package com.support.framework.support;

import org.openqa.selenium.remote.CapabilityType;

public interface MobileCapabilityType extends CapabilityType {
    String AUTOMATION_NAME = "automationName";
    String PLATFORM_NAME = "platformName";
    String PLATFORM_VERSION = "platformVersion";
    String DEVICE_NAME = "deviceName";
    String NEW_COMMAND_TIMEOUT = "newCommandTimeout";
    String APP = "app";
    String BROWSER_NAME = "browserName";
    String UDID = "udid";
    String APPIUM_VERSION = "appiumVersion";
    String LANGUAGE = "language";
    String LOCALE = "locale";
    String ORIENTATION = "orientation";
    String AUTO_WEBVIEW = "autoWebview";
    String NO_RESET = "noReset";
    String FULL_RESET = "fullReset";
}
