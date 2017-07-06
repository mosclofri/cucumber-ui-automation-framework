package com.support.framework.base;

import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static com.support.framework.support.Property.IMPLICIT_WAIT;
import static com.support.framework.support.Util.threadWait;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertTrue;

public abstract class AbstractBase<T extends WebElement> {

    private static final Logger LOG = Logger.getLogger(AbstractBase.class);
    public static Scenario scenario;
    private WebDriver driver;

    public AbstractBase(WebDriver driver) {
        this.driver = driver;
        //LOG.info("Current Driver: " + driver);
    }

    public boolean isContainsText(List<T> element, int index, String text) {
        return element.get(index).getText().contains(text);
    }

    public boolean isContainsText(T element, String text) {
        return element.getText().contains(text);
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

    public boolean isElementPresent(T element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void shouldDisplay(List<T> element, int index, int duration) {
        assertTrue(isElementPresent(element, index, duration));
    }

    public boolean isElementPresent(List<T> element, int index, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOf(element.get(index)));
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public void shouldDisplay(T element) {
        assertTrue(isElementPresent(element));
    }

    public boolean isElementPresent(T element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void shouldNotDisplay(T element, int duration) {
        assertTrue(isElementNotPresent(element, duration));
    }

    public boolean isElementNotPresent(T element, int duration) {
        try {
            Long a = currentTimeMillis();
            while ((currentTimeMillis() - a) / 1000 > duration) {
                if (!element.isDisplayed()) {
                    return true;
                }
                threadWait(.25);
            }
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    public void shouldNotDisplay(T element) {
        assertTrue(isElementNotPresent(element));
    }

    public boolean isElementNotPresent(T element) {
        try {
            element.isDisplayed();
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public void shouldNotDisplay(List<T> element, int index, int duration) {
        assertTrue(isElementNotPresent(element, index, duration));
    }

    public boolean isElementNotPresent(List<T> element, int index, int duration) {
        try {
            Long a = currentTimeMillis();
            while ((currentTimeMillis() - a) / 1000 > duration) {
                if (!element.get(index).isDisplayed())
                    return true;
                threadWait(.25);
            }
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return true;
        }
        return false;
    }

}
