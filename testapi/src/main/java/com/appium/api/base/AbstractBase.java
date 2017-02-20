package com.appium.api.base;

import com.appium.api.support.ImageCompare;
import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.appium.api.support.Property.COMPARE_IMAGE;
import static com.appium.api.support.Property.IMPLICIT_WAIT_TIME;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public abstract class AbstractBase {

    private static final Logger LOG = Logger.getLogger(AbstractBase.class);

    public final AppiumDriver<? extends MobileElement> driver;

    public Scenario scenario;

    public AbstractBase(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public AppiumDriver<? extends MobileElement> getDriver() {
        return driver;
    }

    public MobileElement getElement(String locatorName) {
        LOG.info("Trying to get element: '" + locatorName + "'");
        MobileElement element = null;
        if (locatorName.contains("#")) {
            String locatorStrategy = locatorName.split("#")[0];
            String locatorValue = locatorName.split("#")[1];
            switch (locatorStrategy) {
                case "id":
                    element = driver.findElement(MobileBy.id(locatorValue));
                    break;
                case "ac":
                    element = driver.findElement(MobileBy.AccessibilityId(locatorValue));
                    break;
                case "xp":
                    element = driver.findElement(MobileBy.xpath(locatorValue));
                    break;
                default:
                    LOG.error("Given locatorStrategy does not match any case state");
            }
        } else {
            element = driver.findElement(MobileBy.AccessibilityId(locatorName));
        }
        return element;
    }

    public List<? extends MobileElement> getElementList(String locatorName) {
        LOG.info("Trying to get elementList: '" + locatorName + "'");
        List<? extends MobileElement> elementList = null;
        if (locatorName.contains("#")) {
            String locatorStrategy = locatorName.split("#")[0];
            String locatorValue = locatorName.split("#")[1];
            switch (locatorStrategy) {
                case "id":
                    elementList = driver.findElements(MobileBy.id(locatorValue));
                    break;
                case "ac":
                    elementList = driver.findElements(MobileBy.AccessibilityId(locatorValue));
                    break;
                case "xp":
                    elementList = driver.findElements(MobileBy.xpath(locatorValue));
                    break;
                default:
                    LOG.error("Given locatorStrategy does not match any case state");
            }
        } else {
            elementList = driver.findElements(MobileBy.AccessibilityId(locatorName));
        }
        return elementList;
    }

    public boolean isElementPresent(String element) {
        try {
            getElement(element);
            return true;
        } catch (NoSuchElementException e) {
            LOG.warn("Cannot find given element: '" + element + "'");
            return false;
        }
    }

    public void shouldDisplay(String element) {
        LOG.info("Given element: '" + element + "' should be displayed");
        assertTrue(isElementPresent(element));
    }

    public void shouldNotDisplay(String element) {
        LOG.info("Given element: '" + element + "' should not be displayed");
        assertTrue(!isElementPresent(element));
    }

    public void click(String element) {
        LOG.info("Will try to click to element: '" + element + "'");
        getElement(element).click();
    }

    public void click(String element, int index) {
        LOG.info("Will try to click to element: '" + element + "' at index: '" + index + "'");
        getElementList(element).get(index).click();
    }

    public void type(String element, String text) {
        LOG.info("Will try to sendKeys to element: '" + element + "'");
        getElement(element).sendKeys(text);
    }

    public void type(String element, int index, String text) {
        LOG.info("Will try to sendKeys to element: '" + element + "' at index: '" + index + "'");
        getElementList(element).get(index).sendKeys(text);
    }

    public void ifPresentThenClickOnIt(String arg0, String arg1) {
        setWaitTime(1);
        if (isElementPresent(arg0)) {
            click(arg1);
        }
        setWaitTime(Integer.parseInt(IMPLICIT_WAIT_TIME));
    }

    public void swipeLeft() {
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.8);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void swipeRight() {
        Dimension size = getDriver().manage().window().getSize();
        int endx = (int) (size.width * 0.8);
        int startx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    public void navigate(String element) {
        switch (element) {
            case "Back":
                getDriver().navigate().back();
                break;
            case "Forward":
                getDriver().navigate().forward();
                break;
            default:
                getDriver().navigate().to(element);
                break;

        }
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

    public void setWaitTime(int duration) {
        LOG.info("Setting implicit wait time for driver: '" + duration + "'");
        driver.manage().timeouts().implicitlyWait(duration, TimeUnit.SECONDS);
    }

    public byte[] takeScreenShotAsByte() {
        LOG.info("Capturing a screenshot");
        threadWait(0.25);
        return driver.getScreenshotAs(OutputType.BYTES);
    }

    public String captureLog(String log) {
        LOG.info("Capturing device logs");
        StringBuilder deviceLog = new StringBuilder();
        List<LogEntry> logEntries = driver.manage().logs().get(log).getAll();
        for (LogEntry logLine : logEntries) {
            deviceLog.append(logLine).append(System.lineSeparator());
        }
        return deviceLog.toString();
    }

    public abstract String captureLog();

    public void compareImage(String expectedImage, int errorThreshold) {
        if (!Boolean.valueOf(COMPARE_IMAGE)) {
            return;
        }
        LOG.info("Comparing current screen with: '" + expectedImage + "' with using given threshold value: " + errorThreshold);
        ImageCompare imageCompare = new ImageCompare();

        String expectedImagePath = getClass().getClassLoader().getResource("expected_images/" + expectedImage).getPath();

        BufferedImage imgInput1 = imageCompare.loadImage(expectedImagePath);
        threadWait(0.25);
        BufferedImage imgInput2 = imageCompare.loadImage(driver.getScreenshotAs(OutputType.FILE).getAbsolutePath());

        assert imgInput1 != null && imgInput2 != null;
        assert imgInput1.getWidth() == imgInput2.getWidth();

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

}
