package com.android.test;

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
        features = {"src/test/resources/features"},
        glue = "com.android.test.stepdefs",
        tags = {"~@ignore", "@wip"})
public class RunCukesTest {

    public static final String appiumLog = System.getProperty("appium.log");
    public static final String appiumHost = System.getProperty("appium.host");
    public static final int appiumPort = Integer.parseInt(System.getProperty("appium.port"));
    private static AppiumServer appiumServer = new AppiumServer(appiumHost, appiumPort, appiumLog);

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
