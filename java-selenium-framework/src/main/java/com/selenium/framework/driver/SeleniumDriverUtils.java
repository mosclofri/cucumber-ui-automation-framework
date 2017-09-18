package com.selenium.framework.driver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.OperaDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.support.framework.support.Property.BROWSER_HEIGHT;
import static com.support.framework.support.Property.BROWSER_NAME;
import static com.support.framework.support.Property.BROWSER_WIDTH;
import static com.support.framework.support.Property.GRID_USE;

@Component
class SeleniumDriverUtils {

    private static final Logger LOG = Logger.getLogger(SeleniumDriverUtils.class);

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Profile("Selenium")
    private WebDriver getWebDriver() {
        WebDriver driver;
        TestCapabilities testCapabilities = new TestCapabilities();
        LOG.info("Initializing WebDriver");
        if (GRID_USE.toString().equalsIgnoreCase("true")) {
            driver = new RemoteWebDriver(testCapabilities.getRemoteUrl(), testCapabilities.getDesiredCapabilities());
        } else {
            switch (BROWSER_NAME.toString().toLowerCase()) {
                case "chrome":
                    ChromeDriverManager.getInstance().setup();
                    driver = new ChromeDriver(testCapabilities.getDesiredCapabilities());
                    break;
                case "firefox":
                    FirefoxDriverManager.getInstance().setup();
                    driver = new FirefoxDriver(testCapabilities.getDesiredCapabilities());
                    break;
                case "safari":
                    driver = new SafariDriver(testCapabilities.getDesiredCapabilities());
                    break;
                case "microsoftedge":
                    EdgeDriverManager.getInstance().setup();
                    driver = new EdgeDriver(testCapabilities.getDesiredCapabilities());
                    break;
                case "opera":
                    OperaDriverManager.getInstance().setup();
                    driver = new OperaDriver(testCapabilities.getDesiredCapabilities());
                    break;
                case "ie":
                    InternetExplorerDriverManager.getInstance().setup();
                    driver = new InternetExplorerDriver(testCapabilities.getDesiredCapabilities());
                    break;
                default:
                    LOG.warn(BROWSER_NAME + " is not found in browser list");
                    LOG.info("Initializing Chrome Driver");
                    ChromeDriverManager.getInstance().setup();
                    driver = new ChromeDriver(testCapabilities.getDesiredCapabilities());
                    break;
            }
        }
        driver.manage().deleteAllCookies();
        if (BROWSER_WIDTH != null && BROWSER_HEIGHT != null) {
            LOG.info("Resizing browser to: " + BROWSER_WIDTH + "x" + BROWSER_HEIGHT);
            driver.manage().window().setSize(new Dimension(BROWSER_WIDTH.toInt(), BROWSER_HEIGHT.toInt()));
        }
        return driver;
    }
}