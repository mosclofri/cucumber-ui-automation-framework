package com.selenium.framework.base;

import com.support.framework.base.AbstractBase;
import com.support.framework.support.Property;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class AbstractBaseSelenium extends AbstractBase<WebElement> {

    private static final Logger LOG = Logger.getLogger(AbstractBaseSelenium.class);
    private WebDriver driver;

    AbstractBaseSelenium(WebDriver driver) {
        super(driver);
        this.driver = driver;
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
        driver.get(Property.BASE_URL + subURL);
    }

    public void initPageFactoryElements(Object object) {
        PageFactory.initElements(driver, object);
    }

    public Alert waitAlert(int duration) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, duration);
            wait.until(ExpectedConditions.alertIsPresent());
            return getDriver().switchTo().alert();
        } catch (Throwable error) {
            return null;
        }
    }

    public void waitForPageLoaded(int waitDuration) {
        ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, waitDuration);
            wait.until(expectation);
        } catch (Throwable error) {
            LOG.warn("Timeout while waiting for Page Load Request to complete");
        }
    }

}
