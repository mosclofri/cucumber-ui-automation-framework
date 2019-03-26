package com.selenium.framework.base;

import com.support.framework.base.AbstractBase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.support.framework.support.Property.BASE_URL;
import static com.support.framework.support.Property.IMPLICIT_WAIT;
import static org.junit.Assert.assertEquals;

abstract class AbstractSeleniumBase extends AbstractBase<WebElement> {

    private static final Logger LOG = Logger.getLogger(AbstractSeleniumBase.class);
    private WebDriver driver;

    AbstractSeleniumBase(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void assertElementAndUrl(WebElement element, String url, int duration) {
        assertElementPresent(element, duration);
        assertEquals(BASE_URL + url, driver.getCurrentUrl());
    }

    public void dragAndDrop() {
        WebElement element = driver.findElement(By.name("source"));
        WebElement target = driver.findElement(By.name("target"));
        (new Actions(driver)).dragAndDrop(element, target).perform();
    }

    public boolean findInPageSource(String text) {
        return driver.getPageSource().contains(text);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void getURL(String subURL) {
        driver.get(BASE_URL + subURL);
    }

    public Alert switchToAlert(int duration) {
        waitAlert(duration);
        return getDriver().switchTo().alert();
    }

    public void waitAlert(int duration) {
        new WebDriverWait(driver, duration).until(ExpectedConditions.alertIsPresent());
    }

    public void waitForJQueryDocumentReady() {
        new WebDriverWait(driver, IMPLICIT_WAIT.toInt()).until((ExpectedCondition<Boolean>) d -> {
            JavascriptExecutor js = (JavascriptExecutor) d;
            return (Boolean) js.executeScript("return document.readyState").toString().equals("complete");
        });
    }

    public void waitForJQueryToDone() {
        new WebDriverWait(driver, IMPLICIT_WAIT.toInt()).until((ExpectedCondition<Boolean>) d -> {
            JavascriptExecutor js = (JavascriptExecutor) d;
            return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
        });
    }

    public void clickElementByJS(WebElement element) {
        try {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail(e.getMessage());
        }
    }
}
