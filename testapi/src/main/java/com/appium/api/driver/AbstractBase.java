package com.appium.api.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractBase {

    public AppiumDriver<? extends MobileElement> driver;

    public AbstractBase(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
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
                case "name":
                    element = driver.findElement(MobileBy.name(locatorValue));
                    break;
                case "xpath":
                    element = driver.findElement(MobileBy.xpath(locatorValue));
                    break;
                case "acc":
                    element = driver.findElement(MobileBy.AccessibilityId(locatorValue));
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
                case "name":
                    elementList = driver.findElements(MobileBy.name(locatorValue));
                    break;
                case "xpath":
                    elementList = driver.findElements(MobileBy.xpath(locatorValue));
                    break;
                case "acc":
                    elementList = driver.findElements(MobileBy.AccessibilityId(locatorValue));
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
        Assert.assertTrue(isElementPresent(element));
    }

    public void shouldNotDisplay(String element) {
        Assert.assertTrue(!isElementPresent(element));
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
        getElementList(element).get(index).click();
    }

    public void ifPresentThenClickOnIt(String arg0, String arg1) {
        setWaitTime(Wait.FIVE_SECONDS);
        if (isElementPresent(arg0)) {
            click(arg1);
        }
        setWaitTime(Wait.TWENTY_SECONDS);
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

    public void setWaitTime(Wait time) {
        driver.manage().timeouts().implicitlyWait(time.getValue(), TimeUnit.SECONDS);
    }

    public byte[] takeScreenShotAsByte() {
        threadWait(0.25);
        return driver.getScreenshotAs(OutputType.BYTES);
    }

    protected String captureLog(String log) {
        String strLog = null;
        List<LogEntry> logEntries = driver.manage().logs().get(log).getAll();
        for (LogEntry logLine : logEntries) {
            strLog += logLine + System.lineSeparator();
        }
        return strLog;
    }

    public abstract String captureLog();

    public enum Wait {
        THREE_SECONDS(3),
        FIVE_SECONDS(5),
        TEN_SECONDS(10),
        FIFTEEN_SECONDS(15),
        TWENTY_SECONDS(20),
        THIRTY_SECONDS(30),
        FORTYFIVE_SECONDS(45),
        SIXTY_SECONDS(60);

        private final int value;

        Wait(final int t) {
            value = t;
        }

        public int getValue() {
            return value;
        }
    }

}
