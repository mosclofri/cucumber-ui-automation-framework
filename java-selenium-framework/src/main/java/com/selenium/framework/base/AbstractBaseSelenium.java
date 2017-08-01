package com.selenium.framework.base;

import com.support.framework.base.AbstractBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.support.framework.support.Property.BASE_URL;
import static org.junit.Assert.assertEquals;

abstract class AbstractBaseSelenium extends AbstractBase<WebElement> {

    private static final Logger LOG = Logger.getLogger(AbstractBaseSelenium.class);
    private WebDriver driver;

    AbstractBaseSelenium(WebDriver driver) {
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

    public void waitForPageLoaded(int waitDuration) {
        ExpectedCondition<Boolean> expectation = driver -> {
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        };
        try {
            Thread.sleep(500);
            new WebDriverWait(driver, waitDuration).until(expectation);
        } catch (Throwable error) {
            LOG.warn("Timeout while waiting for Page Load Request to complete");
        }
    }

}
