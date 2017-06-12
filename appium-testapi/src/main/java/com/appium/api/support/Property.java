package com.appium.api.support;

import org.junit.Assert;

public enum Property {

    PLATFORM_NAME(System.getProperty("spring.profiles.active")),
    APPIUM_HOST(System.getProperty("appium.host")),
    APPIUM_PORT(System.getProperty("appium.port")),
    DEVICE_NAME(System.getProperty("device.name")),
    APP_FILE(System.getProperty("app.file")),
    IMPLICIT_WAIT(System.getProperty("implicit.wait")),
    COMPARE_IMAGE(System.getProperty("compare.image")),
    APPIUM_LOG(System.getProperty("appium.log")),
    NO_RESET(System.getProperty("no.reset")),
    TESTRAIL_URL(System.getProperty("testrail.url"));

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



