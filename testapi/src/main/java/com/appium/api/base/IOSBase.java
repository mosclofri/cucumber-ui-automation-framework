package com.appium.api.base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class IOSBase extends AbstractBase {

    public IOSBase(IOSDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IOSDriver<? extends MobileElement> getDriver() {
        return (IOSDriver<? extends MobileElement>) driver;
    }

    @Override
    public String captureLog() {
        return captureLog("syslog");
    }

}
