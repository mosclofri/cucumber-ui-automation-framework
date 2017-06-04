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

public class AppiumBase extends AbstractBase {

    private static final Logger LOG = Logger.getLogger(AppiumBase.class);

    public AppiumBase(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public void androidAllowGranularPermissions() {
        logCucumberStepName();
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
        logCucumberStepName();
        for (MobileElement anElementList : elements) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                anElementList.click();
            }
        }
    }

    public Boolean androidIsAllChecked(List<MobileElement> elements) {
        logCucumberStepName();
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

    public void click(MobileElement element) {
        logCucumberStepName();
        element.click();
    }

    public void click(List<MobileElement> element, int index) {
        logCucumberStepName();
        element.get(index).click();
    }

    public void hideKeyboard() {
        logCucumberStepName();
        driver.hideKeyboard();
    }

    public boolean isContainsText(MobileElement element, String text) {
        logCucumberStepName();
        try {
            if (element.getText().contains(text)) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            logNoSuchElementException(e);
            return false;
        }
    }

    public boolean isContainsText(List<MobileElement> element, int index, String text) {
        logCucumberStepName();
        try {
            if (element.get(index).getText().contains(text)) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isElementNotPresent(MobileElement element, int seconds) {
        logCucumberStepName();
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(List<MobileElement> element, int index, int seconds) {
        logCucumberStepName();
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element.get(index)));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(MobileElement element) {
        logCucumberStepName();
        try {
            element.isDisplayed();
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(List<MobileElement> element, int index) {
        logCucumberStepName();
        try {
            element.get(index).isDisplayed();
            return false;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return true;
        }
    }

    public boolean isElementPresent(MobileElement element, int seconds) {
        logCucumberStepName();
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(List<MobileElement> element, int index, int seconds) {
        logCucumberStepName();
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOf(element.get(index)));
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean isElementPresent(MobileElement element) {
        logCucumberStepName();
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(List<MobileElement> element, int index) {
        logCucumberStepName();
        try {
            element.get(index).isDisplayed();
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public void navigate(String element) {
        logCucumberStepName();
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
        logCucumberStepName();
        driver.closeApp();
        driver.launchApp();
    }

    public void sendKeys(MobileElement element, String text) {
        logCucumberStepName();
        element.sendKeys(text);
    }

    public void sendKeys(List<MobileElement> element, int index, String text) {
        logCucumberStepName();
        element.get(index).sendKeys(text);
    }

    public void swipeDown() {
        logCucumberStepName();
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.80);
        int scrollEnd = (int) (dimensions.getHeight() * 0.20);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

    public void swipeLeft() {
        logCucumberStepName();
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.80);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void swipeRight() {
        logCucumberStepName();
        Dimension size = getDriver().manage().window().getSize();
        int endx = (int) (size.width * 0.80);
        int startx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void swipeUp() {
        logCucumberStepName();
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.20);
        int scrollEnd = (int) (dimensions.getHeight() * 0.80);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

    public void tap(MobileElement element) {
        logCucumberStepName();
        element.tap(1, 1);
    }

    public void tap(List<MobileElement> element, int index) {
        logCucumberStepName();
        element.get(index).tap(1, 1);
    }

    public void type(MobileElement element, String text) {
        logCucumberStepName();
        element.setValue(text);
    }

    public void type(List<MobileElement> element, int index, String text) {
        logCucumberStepName();
        element.get(index).setValue(text);
    }

    private void logNoSuchElementException(NoSuchElementException e) {
        String errorMessage = e.toString().substring(0, e.toString().indexOf("For documentation") - 1);
        LOG.error(errorMessage);
    }

    private void logCucumberStepName() {
        StackTraceElement stackTraceElements = Thread.currentThread().getStackTrace()[3];
        String frameworkMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String classNameWithPackage = stackTraceElements.getClassName();
        String className = classNameWithPackage.substring(classNameWithPackage.lastIndexOf(".") + 1, classNameWithPackage.length());
        String methodName = stackTraceElements.getMethodName();
        int lineNumber = stackTraceElements.getLineNumber();
        LOG.info(className + ":" + lineNumber + ":" + methodName + ":" + frameworkMethodName);
    }

}
