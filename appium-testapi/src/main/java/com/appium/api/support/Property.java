package com.appium.api.support;

import org.junit.Assert;

public enum Property {

    APPIUM_HOST(System.getProperty("appium.host")),
    APPIUM_PORT(System.getProperty("appium.port")),
    APPIUM_PLATFORM(System.getProperty("platform.name")),
    DEVICE_NAME(System.getProperty("device.name")),
    UUID(System.getProperty("device.name")),
    APPIUM_LOG_LEVEL(System.getProperty("appium.log")),
    NO_RESET(System.getProperty("no.reset")),
    APP_FILE(System.getProperty("file")),
    IMPLICIT_WAIT(System.getProperty("implicit.wait")),
    COMPARE_IMAGE(System.getProperty("compare.image")),
    TESTCASE_URL(System.getProperty("testrail.url"));

    private String value;

    Property(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value.isEmpty()) {
            Assert.fail("Check your capabilities in your pom.xml !!!");
        }
        return value;
    }

}



