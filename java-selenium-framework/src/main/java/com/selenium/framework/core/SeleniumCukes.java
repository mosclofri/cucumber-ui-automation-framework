package com.selenium.framework.core;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.support.framework.support.Property.BROWSER_NAME;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "json:target/cucumber.json",
        features = {"classpath:features"},
        glue = {"com.selenium.test.stepdefs", "com.selenium.framework.stepdefs"},
        tags = {"~@ignore"})
public class SeleniumCukes {

    private static final Logger LOG = Logger.getLogger(SeleniumCukes.class);

    @BeforeClass
    public static void startSelenium() {
        LOG.info("### Starting Selenium " + BROWSER_NAME.toString().toUpperCase() + " Selenium ###");
    }

    @AfterClass
    public static void stopSelenium() {
        LOG.info("### Stopping Selenium ###");
    }

}