package com.appium.api.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

import static com.appium.api.support.Property.IMPLICIT_WAIT_TIME;
import static org.junit.Assert.assertTrue;

public class AppiumBase extends AbstractBase {

    private static final Logger LOG = Logger.getLogger(AppiumBase.class);

    public AppiumBase(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public void androidAllowGranularPermissions() {
        LOG.info("Will allow all requested permission");
        MobileElement element = getDriver().findElement(By.xpath("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']"));
        setDriverWaitTime(1);
        while (isElementPresent(element)) {
            click(element);
        }
        setDefaultDriverWaitTime();
    }

    public void androidCheckAll(List<? extends MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked")))
                LOG.info("Will try to click:" + elementLogger(anElementList));
            anElementList.click();
        }
    }

    public Boolean androidIsAllChecked(List<? extends MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                return false;
            }
        }
        return true;
    }

    public Boolean androidIsChecked(MobileElement element) {
        return Boolean.parseBoolean(element.getAttribute("checked"));
    }

    public Boolean androidIsChecked(List<? extends MobileElement> element, int index) {
        return Boolean.parseBoolean(element.get(index).getAttribute("checked"));
    }

    public void click(MobileElement element) {
        LOG.info("Will try to click:" + elementLogger(element));
        element.click();
    }

    public void click(List<? extends MobileElement> element, int index) {
        LOG.info("Will try to click:" + elementLogger(element, index));
        element.get(index).click();
    }

    private String elementLogger(MobileElement element) {
        if (!element.getText().isEmpty() && element.getText() != null) {
            return " '" + element.getText() + "' ";
        } else {
            return " '" + element.getId() + "' ";
        }
    }

    private String elementLogger(List<? extends MobileElement> element, int index) {
        if (!element.get(index).getText().isEmpty() && element.get(index).getText() != null) {
            return " '" + element.get(index).getText() + "' with index: '" + index + "' ";
        } else {
            return " '" + element.get(index).getClass() + "' with index: '" + index + "' ";
        }
    }

    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    public void ifPresentThenClickOnIt(MobileElement element, int durationForLook) {
        setDriverWaitTime(durationForLook);
        if (isElementPresent(element)) {
            click(element);
        }
        setDefaultDriverWaitTime();
    }

    public void ifPresentThenClickOnIt(List<? extends MobileElement> element, int durationForLook, int index) {
        setDriverWaitTime(durationForLook);
        if (isElementPresent(element, index)) {
            click(element, index);
        }
        setDefaultDriverWaitTime();
    }

    public boolean isContainsText(MobileElement element, String text) {
        try {
            if (element.getText().contains(text)) {
                return true;
            }
        } catch (NoSuchElementException e) {
            LOG.warn("Cannot find given element:" + elementLogger(element));
        }
        LOG.info("Given element" + elementLogger(element) + " does not contains: '" + text + "'");
        return false;
    }

    public boolean isElementPresent(MobileElement element) {
        try {
            element.getCenter();
            return true;
        } catch (NoSuchElementException e) {
            LOG.warn("Cannot find given element: " + elementLogger(element));
            return false;
        }
    }

    public boolean isElementPresent(List<? extends MobileElement> element, int index) {
        try {
            element.get(index).getCenter();
            return true;
        } catch (NoSuchElementException e) {
            LOG.warn("Cannot find given element:" + element);
            return false;
        }
    }

    public void navigate(String element) {
        switch (element) {
            case "Back":
                LOG.info("Navigating back");
                getDriver().navigate().back();
                break;
            case "Forward":
                LOG.info("Navigating forward");
                getDriver().navigate().forward();
                break;
            default:
                LOG.info("Navigating to element: " + element);
                getDriver().navigate().to(element);
                break;
        }
    }

    public void restartApp() {
        LOG.info("Appium will close and relaunch application in test");
        driver.closeApp();
        driver.launchApp();
    }

    public void shouldDisplay(MobileElement element) {
        LOG.info("Given element:" + elementLogger(element) + "should be displayed");
        assertTrue(isElementPresent(element));
    }

    public void shouldNotDisplay(MobileElement element) {
        LOG.info("Given element:" + elementLogger(element) + "should not be displayed");
        assertTrue(!isElementPresent(element));
    }

    public void swipeDown() {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.80);
        int scrollEnd = (int) (dimensions.getHeight() * 0.20);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

    public void swipeDownToElement(MobileElement element) {
        setDriverWaitTime(1);
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        while ((endTime - startTime) / 1000 < Integer.parseInt(IMPLICIT_WAIT_TIME) && !isElementPresent(element)) {
            swipeDown();
            endTime = System.currentTimeMillis();
        }
        setDefaultDriverWaitTime();
    }

    public void swipeLeft() {
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.80);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void swipeRight() {
        Dimension size = getDriver().manage().window().getSize();
        int endx = (int) (size.width * 0.80);
        int startx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void swipeUp() {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.20);
        int scrollEnd = (int) (dimensions.getHeight() * 0.80);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

    public void swipeUpToElement(MobileElement element) {
        setDriverWaitTime(1);
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        while ((endTime - startTime) / 1000 < Integer.parseInt(IMPLICIT_WAIT_TIME) && !isElementPresent(element)) {
            swipeUp();
            endTime = System.currentTimeMillis();
        }
        setDefaultDriverWaitTime();
    }

    public void tap(MobileElement element) {
        LOG.info("Will try to tap:" + elementLogger(element));
        element.tap(1, 1);
    }

    public void tap(List<? extends MobileElement> element, int index) {
        LOG.info("Will try to tap" + elementLogger(element, index));
        element.get(index).tap(1, 1);
    }

    public void type(MobileElement element, String text) {
        LOG.info("Will try to sendKeys '" + text + "' to:" + elementLogger(element));
        element.setValue(text);
    }

    public void type(List<? extends MobileElement> element, int index, String text) {
        LOG.info("Will try to sendKeys '" + text + "' to:" + elementLogger(element, index));
        element.get(index).setValue(text);
    }

}
