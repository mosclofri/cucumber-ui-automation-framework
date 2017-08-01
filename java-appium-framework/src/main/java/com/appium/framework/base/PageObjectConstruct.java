package com.appium.framework.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class PageObjectConstruct {

    @Autowired
    public BaseAppium appiumBase;

    public AndroidDriver getAsAndroidDriver() {
        return (AndroidDriver) getDriver();
    }

    public IOSDriver getAsIOSDriver() {
        return (IOSDriver) getDriver();
    }

    public AppiumDriver getDriver() {
        return appiumBase.getDriver();
    }

    public BaseAppium getHelper() {
        return appiumBase;
    }

    @PostConstruct
    private void init() {
        appiumBase.initPageFactoryElements(this);
    }

}
