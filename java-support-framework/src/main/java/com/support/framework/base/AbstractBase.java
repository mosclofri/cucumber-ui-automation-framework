package com.support.framework.base;

import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
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

    public void assertElementsNotPresent(List<T> element, int index, int duration) {
        assertTrue(elementsNotPresent(element, index, duration));
    }

    public void assertElementsPresent(List<T> element, int index, int duration) {
        assertTrue(elementsPresent(element, index, duration));
    }

    public boolean assertMultipleElementsWithAnd(int duration, T... args) {
        ExpectedCondition[] expected = getExpectedConditions(args);
        try {
            new WebDriverWait(driver, duration).until(ExpectedConditions.and(expected));
            return true;
        } catch (NoSuchElementException ignore) {
            return false;
        }
    }

    public boolean assertMultipleElementsWithOr(int duration, T... args) {
        ExpectedCondition[] expected = getExpectedConditions(args);
        try {
            new WebDriverWait(driver, duration).until(ExpectedConditions.or(expected));
            return true;
        } catch (NoSuchElementException ignore) {
            return false;
        }
    }

    public void assertVisibleAndInvisibleElement(T visibleElement, T invisibleElement, int duration) {
        ExpectedCondition<Boolean> expectation = driver -> (visibleElement.isDisplayed() && !invisibleElement.isDisplayed());
        new WebDriverWait(driver, duration).until(expectation);
    }

    public void clickOnIfPresent(T element, int duration) {
        if (elementPresent(element, duration)) {
            element.click();
        }
    }

    public void clickOnIfPresents(List<T> element, int duration, int index) {
        if (elementsPresent(element, index, duration)) {
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
        } catch (NoSuchElementException ignore) {
            return true;
        }
    }

    public boolean elementNotPresent(T element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.invisibilityOf(element));
            return false;
        } catch (NoSuchElementException | IndexOutOfBoundsException ignore) {
            return true;
        }
    }

    public boolean elementPresent(T element, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException ignore) {
            return false;
        }
    }

    public boolean elementPresent(T element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException ignore) {
            return false;
        }
    }

    public boolean elementsNotPresent(List<T> element, int index, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.invisibilityOf(element.get(index)));
            return false;
        } catch (NoSuchElementException | IndexOutOfBoundsException ignore) {
            return true;
        }
    }

    public boolean elementsPresent(List<T> element, int index, int duration) {
        try {
            new WebDriverWait(driver, duration)
                    .until(ExpectedConditions.visibilityOf(element.get(index)));
            return true;
        } catch (NoSuchElementException | IndexOutOfBoundsException ignore) {
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

    private ExpectedCondition[] getExpectedConditions(T[] args) {
        List<T> elements = Arrays.asList(args);
        ExpectedCondition[] expected = new ExpectedCondition[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            expected[i] = ExpectedConditions.visibilityOf(elements.get(i));
        }
        return expected;
    }

}


