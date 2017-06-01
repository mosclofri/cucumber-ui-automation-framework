package com.appium.api.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        setDriverWaitTime(1);
        while (true) {
            try {
                MobileElement element = getDriver().findElement(By.xpath("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']"));
                click(element);
            } catch (NoSuchElementException e) {
                break;
            }
        }
        setDefaultDriverWaitTime();
    }

    public void androidCheckAll(List<MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                LOG.info("Will try to click:" + elementLogger(anElementList));
                anElementList.click();
            }
        }
    }

    public Boolean androidIsAllChecked(List<MobileElement> elements) {
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

    public Boolean androidIsChecked(List<MobileElement> element, int index) {
        return Boolean.parseBoolean(element.get(index).getAttribute("checked"));
    }

    public void click(MobileElement element) {
        LOG.info("Will try to click:" + elementLogger(element));
        element.click();
    }

    public void click(List<MobileElement> element, int index) {
        LOG.info("Will try to click:" + elementLogger(element, index));
        element.get(index).click();
    }

    private String elementLogger(MobileElement element) {
        try {
            if (!element.getText().isEmpty() && element.getText() != null) {
                return " '" + element.getText() + "' ";
            } else {
                return " '" + element.getId() + "' ";
            }
        } catch (NoSuchElementException e) {
            return " 'NoSuchElementException' ";
        }
    }

    private String elementLogger(List<? extends MobileElement> element, int index) {
        try {
            if (!element.get(index).getText().isEmpty() && element.get(index).getText() != null) {
                return " '" + element.get(index).getText() + "' with index: '" + index + "' ";
            } else {
                return " '" + element.get(index).getClass() + "' with index: '" + index + "' ";
            }
        } catch (NoSuchElementException e) {
            return " 'NoSuchElementException' ";
        }
    }

    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    public void ifPresentThenClickOnIt(MobileElement element, int durationForLook) {
        if (isElementPresent(element, durationForLook)) {
            click(element);
        }
    }

    public void ifPresentThenClickOnIt(List<MobileElement> element, int seconds, int index) {
        if (isElementPresent(element, index, seconds)) {
            click(element, index);
        }
    }

    public boolean isContainsText(MobileElement element, String text) {
        try {
            if (element.getText().contains(text)) {
                return true;
            } else {
                LOG.info("Given element" + elementLogger(element) + " does not contains: '" + text + "'");
            }
        } catch (NoSuchElementException e) {
            LOG.warn("Cannot find given element:" + elementLogger(element));
        }
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

    public boolean isElementPresent(MobileElement element, int seconds) {
        try {
            waitVisibilityOfElement(element, seconds);
            return true;
        } catch (NoSuchElementException e) {
            LOG.warn("Cannot find given element: " + elementLogger(element));
            return false;
        }
    }

    public boolean isElementPresent(List<MobileElement> element, int index, int seconds) {
        try {
            waitVisibilityOfElement(element, index, seconds);
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

    public void sendKeys(MobileElement element, String text) {
        LOG.info("Will try to sendKeys '" + text + "' to:" + elementLogger(element));
        element.sendKeys(text);
    }

    public void sendKeys(List<MobileElement> element, int index, String text) {
        LOG.info("Will try to sendKeys '" + text + "' to:" + elementLogger(element, index));
        element.get(index).sendKeys(text);
    }

    public void shouldDisplay(MobileElement element) {
        LOG.info("Given element:" + elementLogger(element) + "should be displayed");
        assertTrue(isElementPresent(element, Integer.parseInt(IMPLICIT_WAIT_TIME)));
    }

    public void shouldNotDisplay(MobileElement element) {
        LOG.info("Given element:" + elementLogger(element) + "should not be displayed");
        assertTrue(!isElementPresent(element, Integer.parseInt(IMPLICIT_WAIT_TIME)));
    }

    public void swipeDown() {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.80);
        int scrollEnd = (int) (dimensions.getHeight() * 0.20);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
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

    public void tap(MobileElement element) {
        LOG.info("Will try to tap:" + elementLogger(element));
        element.tap(1, 1);
    }

    public void tap(List<MobileElement> element, int index) {
        LOG.info("Will try to tap" + elementLogger(element, index));
        element.get(index).tap(1, 1);
    }

    public void type(MobileElement element, String text) {
        LOG.info("Will try to sendKeys '" + text + "' to:" + elementLogger(element));
        element.setValue(text);
    }

    public void type(List<MobileElement> element, int index, String text) {
        LOG.info("Will try to sendKeys '" + text + "' to:" + elementLogger(element, index));
        element.get(index).setValue(text);
    }

    public void waitVisibilityOfElement(MobileElement element, int seconds) {
        new WebDriverWait(driver, seconds)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitVisibilityOfElement(List<MobileElement> element, int index, int seconds) {
        new WebDriverWait(driver, seconds)
                .until(ExpectedConditions.visibilityOf(element.get(index)));
    }

}
