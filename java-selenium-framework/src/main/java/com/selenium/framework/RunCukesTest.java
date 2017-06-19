package com.selenium.framework;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static com.support.framework.support.Property.BROWSER_NAME;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "json:target/cucumber.json",
        features = {"classpath:features"},
        glue = {"com.selenium.test.stepdefs", "com.selenium.framework.stepdefs"},
        tags = {"~@ignore"})
public class RunCukesTest {

    private static final Logger LOG = Logger.getLogger(RunCukesTest.class);

    public static WebDriver driver;

    @BeforeClass
    public static void setupDriver() {
        LOG.info("### Starting Selenium " + BROWSER_NAME.toString().toUpperCase() + " Driver ###");
    }

    @AfterClass
    public static void stopDriver() {
        LOG.info("### Stopping Selenium Driver ###");
        if (driver != null) {
            driver.quit();
        }
    }

}