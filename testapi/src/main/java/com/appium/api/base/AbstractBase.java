package com.appium.api.base;

import com.appium.api.support.ImageCompare;
import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.appium.api.support.Property.COMPARE_IMAGE;
import static com.appium.api.support.Property.IMPLICIT_WAIT_TIME;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public abstract class AbstractBase {

    public final AppiumDriver<? extends MobileElement> driver;

    public Scenario scenario;

    public AbstractBase(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public AppiumDriver<? extends MobileElement> getDriver() {
        return driver;
    }

    public MobileElement getElement(String locatorName) {
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
                    System.out.println("getElement method had an error");
            }
        } else {
            element = driver.findElement(MobileBy.AccessibilityId(locatorName));
        }
        return element;
    }

    public List<? extends MobileElement> getElementList(String locatorName) {
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
                    System.out.println("getElement method had an error");
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
            return false;
        }
    }

    public void shouldDisplay(String element) {
        assertTrue(isElementPresent(element));
    }

    public void shouldNotDisplay(String element) {
        assertTrue(!isElementPresent(element));
    }

    public void click(String element) {
        getElement(element).click();
    }

    public void click(String element, int index) {
        getElementList(element).get(index).click();
    }

    public void type(String element, String text) {
        getElement(element).sendKeys(text);
    }

    public void type(String element, int index, String text) {
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
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setWaitTime(int duration) {
        driver.manage().timeouts().implicitlyWait(duration, TimeUnit.SECONDS);
    }

    public byte[] takeScreenShotAsByte() {
        threadWait(0.25);
        return driver.getScreenshotAs(OutputType.BYTES);
    }

    public String captureLog(String log) {
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
        ImageCompare imageCompare = new ImageCompare();
        System.out.println("Comparing current screen with " + expectedImage);

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

        if (differenceCounter / width > errorThreshold) {
            scenario.embed(imageCompare.saveByteImage(imgInput1), "image/png");
            fail(differenceCounter / width + " differences found which is greater than given threshold " + errorThreshold);
        }
    }

}
