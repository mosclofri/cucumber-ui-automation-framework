package com.support.framework.base;

import com.support.framework.support.ImageCompare;
import cucumber.api.Scenario;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.support.framework.support.Property.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class AbstractBase<T extends WebElement> {

    private static final Logger LOG = Logger.getLogger(AbstractBase.class);
    private Scenario scenario;
    private WebDriver driver;

    public AbstractBase(WebDriver driver) {
        this.driver = driver;
        LOG.info("Current Driver: " + driver);
    }

    public void compareImage(int errorThreshold) {
        String currentMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Pattern p = Pattern.compile("given|and|when|then");
        Matcher m = p.matcher(currentMethodName);
        if (m.find()) {
            currentMethodName = currentMethodName.substring(m.end());
        }
        compareImage(currentMethodName, errorThreshold);
    }

    public void compareImage(String expectedImage, int errorThreshold) {
        //TODO Add Browser size control for Selenium
        File screenShot;
        String deviceName;

        if (COMPARE_IMAGE.toString().matches("true|record")) {
            if (PLATFORM_NAME.toString().equalsIgnoreCase("android")) {
                deviceName = executeShellReturnStringResult("adb -s " + DEVICE_NAME.toString() + " shell getprop ro.product.model");
            } else {
                deviceName = DEVICE_NAME.toString();
            }
            screenShot = new File("./src/test/resources/expected_images/" + deviceName + "/" + expectedImage + ".png");
        } else {
            return;
        }

        if (COMPARE_IMAGE.toString().matches("true")) {
            if (!screenShot.exists()) {
                LOG.warn("There is no existing image for : " + expectedImage + ".png for device: " + deviceName);
                return;
            }
            LOG.info("Comparing current screen with: '" + expectedImage + "' with using given threshold value: " + errorThreshold);
            ImageCompare imageCompare = new ImageCompare();

            BufferedImage imgInput1 = imageCompare.loadImage(screenShot.getPath());
            threadWait(0.25);
            BufferedImage imgInput2 = imageCompare.loadImage(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).getAbsolutePath());

            int width = imgInput1.getWidth();
            int differenceCounter = 0;
            Color black = new Color(0, 0, 0);
            Color white = new Color(255, 255, 255);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < Math.min(imgInput1.getHeight(), imgInput2.getHeight()); y++) {
                    Color color1 = new Color(imgInput1.getRGB(x, y));
                    Color color2 = new Color(imgInput2.getRGB(x, y));

                    int differenceRed = Math.abs(color1.getRed() - color2.getRed());
                    int differenceGreen = Math.abs(color1.getGreen() - color2.getGreen());
                    int differenceBlue = Math.abs(color1.getBlue() - color2.getBlue());
                    int difference = differenceRed + differenceGreen + differenceBlue;

                    double relativeDifference = (double) difference / (256 * 3);

                    if (relativeDifference > ImageCompare.colorThreshold) {
                        imgInput1.setRGB(x, y, black.getRGB());
                        imageCompare.addToRectangles(x, y);
                        differenceCounter++;
                    } else {
                        imgInput1.setRGB(x, y, white.getRGB());
                    }
                }
            }

            int differenceThreshold = differenceCounter / width;
            if (differenceThreshold > errorThreshold) {
                LOG.info(differenceThreshold + " differences found that is greater than given threshold value: " + errorThreshold);
                scenario.embed(imageCompare.saveByteImage(imgInput1), "image/png");
                fail(differenceThreshold + " differences found that is greater than given threshold value: " + errorThreshold);
            }
        }

        if (COMPARE_IMAGE.toString().matches("record")) {
            threadWait(0.25);
            File deviceScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            if (!screenShot.exists()) {
                try {
                    LOG.info("Saving current screen as: " + expectedImage + ".png for device:" + deviceName);
                    FileUtils.copyFile(deviceScreen, new File(screenShot.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                LOG.info("There is already saved screenShot: " + expectedImage + ".png for device: " + deviceName);
            }
        }
    }

    public String executeShellReturnStringResult(String command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine commandline = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        try {
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            return outputStream.toString("UTF-8").substring(0, outputStream.toString().length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAndroidSDKVersion() {
        return executeShellReturnStringResult("adb -s " + DEVICE_NAME.toString() + " shell getprop ro.build.version.sdk");
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void hookAfter() {
        LOG.info("### " + scenario.getStatus().toUpperCase() + " ###");
        LOG.info("### Ending scenario: " + scenario.getName() + " ###");
        List<String> caseList = getScenariosStartWithCaseIds(scenario.getSourceTagNames());
        for (String currentTag : caseList) {
            scenario.write("<a href=\"" + TESTRAIL_URL + currentTag + "\"> Test Scenario: C" + currentTag + "</a>");
        }
        if (scenario.isFailed()) {
            scenario.embed(takeScreenShotAsByte(), "image/png");
            if (!PLATFORM_NAME.toString().equalsIgnoreCase("web")) {
                scenario.embed(captureLog().getBytes(StandardCharsets.UTF_8), "text/html");
            }
        }
    }

    public void hookBefore(Scenario scenario) {
        setScenario(scenario);
        LOG.info("### Starting scenario: " + scenario.getName() + " ###");
    }

    public boolean isContainsText(List<T> element, int index, String text) {
        return element.get(index).getText().contains(text);
    }

    public boolean isContainsText(T element, String text) {
        return element.getText().contains(text);
    }

    public boolean isElementNotPresent(T element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOf(element));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(T element) {
        try {
            element.isDisplayed();
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementPresent(T element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(T element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void logCucumberStep() {
        StackTraceElement stackTraceElements = Thread.currentThread().getStackTrace()[2];
        String methodName = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(stackTraceElements.getMethodName()), ' ');
        LOG.info(methodName);
    }

    public void navigate(String element) {

        switch (element) {
            case "Back":
                LOG.info("Navigating back");
                driver.navigate().back();
                break;
            case "Forward":
                LOG.info("Navigating forward");
                driver.navigate().forward();
                break;
            default:
                LOG.info("Navigating to element: " + element);
                driver.navigate().to(element);
                break;
        }
    }

    public void setDefaultDriverWaitTime() {
        setDriverWaitTime(Integer.parseInt(IMPLICIT_WAIT.toString()));
    }

    public void setDriverWaitTime(int duration) {
        LOG.info("Setting implicit wait time for driver: '" + duration + "'");
        driver.manage().timeouts().implicitlyWait(duration, TimeUnit.SECONDS);
    }

    public void shouldDisplay(T element, int duration) {
        assertTrue(isElementPresent(element, duration));
    }

    public void shouldDisplay(T element) {
        assertTrue(isElementPresent(element));
    }


    public void shouldNotDisplay(T element, int duration) {
        assertTrue(isElementNotPresent(element, duration));
    }


    public void shouldNotDisplay(T element) {
        assertTrue(isElementNotPresent(element));
    }


    public void skipScenario(String reasonToSkip) {
        LOG.info("Will skip current scenario. Reason to skip: " + reasonToSkip);
        throw new AssumptionViolatedException(reasonToSkip);
    }

    public byte[] takeScreenShotAsByte() {
        LOG.info("Capturing a screenshot");
        threadWait(0.25);
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void threadWait(double duration) {
        try {
            if (duration > 3) {
                LOG.info("Waiting '" + duration + "' duration");
            }
            Thread.sleep((long) (duration * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String captureLog() {
        LOG.info("Capturing device logs");
        String logType;
        if (PLATFORM_NAME.toString().equalsIgnoreCase("android"))
            logType = "logcat";
        else
            logType = "syslog";
        StringBuilder deviceLog = new StringBuilder();
        List<LogEntry> logEntries = driver.manage().logs().get(logType).getAll();
        for (LogEntry logLine : logEntries) {
            deviceLog.append(logLine).append(System.lineSeparator());
        }
        return deviceLog.toString();
    }

    private List<String> getScenariosStartWithCaseIds(Collection collection) {
        List<String> list = new ArrayList<>();
        for (Object currentTag : collection) {
            if (currentTag.toString().startsWith("@C")) {
                list.add(currentTag.toString().substring(2));
            }
        }
        return list;
    }

}

