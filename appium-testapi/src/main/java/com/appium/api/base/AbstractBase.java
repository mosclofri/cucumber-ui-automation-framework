package com.appium.api.base;

import com.appium.api.support.ImageCompare;
import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.PageFactory;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.appium.api.support.Property.*;
import static org.junit.Assert.fail;

public abstract class AbstractBase {

    private static final Logger LOG = Logger.getLogger(AbstractBase.class);

    public AppiumDriver<? extends MobileElement> driver;
    public Scenario scenario;

    public AbstractBase(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
    }

    public String captureLog() {
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
        File screenShot;
        String deviceName = null;

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
            BufferedImage imgInput2 = imageCompare.loadImage(driver.getScreenshotAs(OutputType.FILE).getAbsolutePath());

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
            File deviceScreen = driver.getScreenshotAs(OutputType.FILE);
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

    public AppiumDriver<? extends MobileElement> getDriver() {
        return driver;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void initElementsWithFieldDecorator(Object object) {
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Integer.parseInt(IMPLICIT_WAIT.toString()), TimeUnit.SECONDS), object);
    }

    public void initElementsWithFieldDecorator(Object object, int timeout) {
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, timeout, TimeUnit.SECONDS), object);
    }

    public void setDefaultDriverWaitTime() {
        setDriverWaitTime(Integer.parseInt(IMPLICIT_WAIT.toString()));
    }

    public void setDriverWaitTime(int duration) {
        LOG.info("Setting implicit wait time for driver: '" + duration + "'");
        driver.manage().timeouts().implicitlyWait(duration, TimeUnit.SECONDS);
    }

    public void skipScenario(String reasonToSkip) {
        LOG.info("Will skip current scenario. Reason to skip: " + reasonToSkip);
        throw new AssumptionViolatedException(reasonToSkip);
    }

    public void switchContextToFirstWEBVIEW() {
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            if (context.contains("WEBVIEW")) {
                LOG.info("Switching context to: " + context);
                driver.context(context);
            } else {
                LOG.info("No WEBVIEW context available, keeping current context");
            }
        }
    }

    public void switchContextToNATIVE() {
        LOG.info("Switching context to NATIVE_APP");
        driver.context("NATIVE_APP");
    }

    public byte[] takeScreenShotAsByte() {
        LOG.info("Capturing a screenshot");
        threadWait(0.25);
        return driver.getScreenshotAs(OutputType.BYTES);
    }

    public void threadWait(double seconds) {
        try {
            if (seconds > 3) {
                LOG.info("Waiting '" + seconds + "' seconds");
            }
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
