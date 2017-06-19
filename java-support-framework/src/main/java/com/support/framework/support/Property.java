package com.support.framework.support;

import org.junit.Assert;

import java.util.Optional;

public enum Property {

    PLATFORM_NAME(System.getProperty("platform.name")),
    PLATFORM_VERSION(System.getProperty("platform.version")),
    IMPLICIT_WAIT(Optional.ofNullable(System.getProperty("implicit.wait")).orElse("3")),
    COMPARE_IMAGE(Optional.ofNullable(System.getProperty("compare.image")).orElse("false")),

    //Appium Specific
    APP_FILE(System.getProperty("app.file")),
    DEVICE_NAME(System.getProperty("device.name")),
    APPIUM_URL(Optional.ofNullable(System.getProperty("appium.url")).orElse("127.0.0.1:4799")),
    NO_RESET(Optional.ofNullable(System.getProperty("no.reset")).orElse("true")),
    APPIUM_LOG(Optional.ofNullable(System.getProperty("appium.log")).orElse("warn")),

    //Selenium Specific
    BROWSER_NAME(Optional.ofNullable(System.getProperty("browser.name")).orElse("Chrome")),
    BASE_URL(System.getProperty("base.url")),
    GRID_URL(System.getProperty("grid.url")),
    GRID_USE(System.getProperty("grid.use")),
    SELENIUM_LOG(Optional.ofNullable(System.getProperty("selenium.log")).orElse("WARNING")),

    TESTRAIL_URL(System.getProperty("testrail.url"));

    private String value;

    Property(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null || value.length() == 0) {
            Assert.fail("Property " + this.name() + " is missing. Check your your pom.xml");
        }
        return value;
    }

}



