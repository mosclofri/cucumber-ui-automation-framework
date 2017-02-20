package com.appium.api;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.appium.api.server.AppiumServer.startAppiumServer;
import static com.appium.api.server.AppiumServer.stopAppiumServer;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "json:target/cucumber.json",
        features = {"classpath:features"},
        glue = {"com.android.test", "com.appium.api.stepdefs"},
        tags = {"~@ignore"})
public class RunCukesTest {

    private static final Logger LOG = Logger.getLogger(RunCukesTest.class);

    @BeforeClass
    public static void startAppium() {
        LOG.info("### Starting Appium Server ###");
        startAppiumServer();
    }

    @AfterClass
    public static void stopAppium() {
        LOG.info("### Closing Appium Server ###");
        stopAppiumServer();
    }

}
