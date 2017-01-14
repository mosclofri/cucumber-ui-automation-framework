package com.appium.api;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
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

    @BeforeClass
    public static void startAppium() {
        startAppiumServer();
    }

    @AfterClass
    public static void stopAppium() {
        stopAppiumServer();
    }

}
