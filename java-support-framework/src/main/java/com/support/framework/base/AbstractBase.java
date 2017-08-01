package com.support.framework.base;

import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.support.framework.support.Property.IMPLICIT_WAIT;
import static org.junit.Assert.assertTrue;

public abstract class AbstractBase<T extends WebElement> {

    private static final Logger LOG = Logger.getLogger(AbstractBase.class);
    public static Scenario scenario;
    private WebDriver driver;

    public AbstractBase(WebDriver driver) {
        this.driver = driver;
        //LOG.info("Current Driver: " + driver);
    }

    public void assertElementNotPresent(T element) {
        assertTrue(elementNotPresent(element));
    }

    public void assertElementNotPresent(T element, int duration) {
        assertTrue(elementNotPresent(element, duration));
    }

    public void assertElementPresent(T element, int duration) {
        assertTrue(elementPresent(element, duration));
    }

    public void assertElementPresent(T element) {
        assertTrue(elementPresent(element));
    }

    public void assertElementsNotPresent(List<T> element, int duration) {
        assertTrue(elementsNotPresent(element, duration));
    }

    public void assertElementsPresent(List<T> element, int duration) {
        assertTrue(elementsPresent(element, duration));
    }

    public void clickOnIfPresent(T element, int duration) {
        if (elementPresent(element, duration)) {
            element.click();
        }
    }

    public void clickOnIfPresents(List<T> element, int duration, int index) {
        if (elementsPresent(element, duration)) {
            element.get(index).click();
        }
    }

    public boolean contains(T element, String text) {
        return element.getText().contains(text);
    }

    public boolean contains(List<T> element, int index, String text) {
        return element.get(index).getText().contains(text);
    }

    public boolean elementNotPresent(T element) {
        try {
            element.isDisplayed();
            return false;
        } catch (org.openqa.selenium.NoSuchElementException ignore) {
            return true;
        }
    }

    public boolean elementNotPresent(T element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.invisibilityOfAllElements((List<WebElement>) element));
            return false;
        } catch (org.openqa.selenium.NoSuchElementException | IndexOutOfBoundsException ignore) {
            return true;
        }
    }

    public boolean elementPresent(T element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ignore) {
            return false;
        }
    }

    public boolean elementPresent(T element) {
        try {
            element.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ignore) {
            return false;
        }
    }

    public boolean elementsNotPresent(List<T> element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.invisibilityOfAllElements((List<WebElement>) element));
            return false;
        } catch (org.openqa.selenium.NoSuchElementException | IndexOutOfBoundsException ignore) {
            return true;
        }
    }

    public boolean elementsPresent(List<T> element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOfAllElements((List<WebElement>) element));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException | IndexOutOfBoundsException ignore) {
            return false;
        }
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

}


