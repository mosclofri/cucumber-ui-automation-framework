package com.appium.api.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class AppiumBase extends AbstractBase {

    private static final Logger LOG = Logger.getLogger(AppiumBase.class);

    public AppiumBase(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public void androidAllowGranularPermissions() {
        setDriverWaitTime(1);
        while (true) {
            try {
                MobileElement element = getDriver().findElement(By.xpath("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']"));
                element.click();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        setDefaultDriverWaitTime();
    }

    public void androidCheckAll(List<MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                anElementList.click();
            }
        }
    }

    public Boolean androidIsAllChecked(List<MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
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

    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    public boolean isContainsText(MobileElement element, String text) {
        return element.getText().contains(text);
    }

    public boolean isContainsText(List<MobileElement> element, int index, String text) {
        return element.get(index).getText().contains(text);
    }

    public boolean isElementNotPresent(MobileElement element, int seconds) {
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(List<MobileElement> element, int index, int seconds) {
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element.get(index)));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(MobileElement element) {
        try {
            element.isDisplayed();
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(List<MobileElement> element, int index) {
        try {
            element.get(index).isDisplayed();
            return false;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return true;
        }
    }

    public boolean isElementPresent(MobileElement element, int seconds) {
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(List<MobileElement> element, int index, int seconds) {
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element.get(index)));
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isElementPresent(MobileElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(List<MobileElement> element, int index) {
        try {
            element.get(index).isDisplayed();
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
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
        driver.closeApp();
        driver.launchApp();
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

}
