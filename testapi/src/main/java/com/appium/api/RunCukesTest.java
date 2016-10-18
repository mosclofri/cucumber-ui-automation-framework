package com.appium.api;

import com.appium.api.server.AppiumServer;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.apache.log4j.BasicConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "json:target/cucumber.json",
        features = {"classpath:features"},
        glue = {"com.android.test", "com.appium.api.hooks"},
        tags = {"~@ignore", "@wip"})
public class RunCukesTest {

    private static final AppiumServer appiumServer =
            new AppiumServer(System.getProperty("appium.host"), Integer.parseInt(System.getProperty("appium.port")));

    @BeforeClass
    public static void startAppium() {
        BasicConfigurator.configure();
        appiumServer.startAppiumServer();
    }

    @AfterClass
    public static void stopAppium() {
        appiumServer.stopAppiumServer();
    }

}
