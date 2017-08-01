package com.appium.framework.driver;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.appium.framework.core.AppiumCukes.APPIUM_PORT;
import static com.support.framework.support.Property.*;
import static io.appium.java_client.remote.AutomationName.APPIUM;
import static io.appium.java_client.remote.AutomationName.IOS_XCUI_TEST;

final class TestCapabilities {

    private static final Logger LOG = Logger.getLogger(TestCapabilities.class);

    static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            capabilities.setCapability(MobileCapabilityType.APP,
                    new File(URLDecoder.decode(ClassLoader.getSystemResource((APP_FILE.toString())).getFile(),
                            StandardCharsets.UTF_8.toString())).getAbsolutePath());
        } catch (NullPointerException e) {
            Assert.fail("Cannot find given application file" + e);
        } catch (UnsupportedEncodingException e) {
            LOG.warn(e);
        }
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.UDID, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.valueOf(NO_RESET.toString()));
        switch (PLATFORM_NAME.toString().toUpperCase()) {
            case "IOS":
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, IOS_XCUI_TEST);
                break;
            case "ANDROID":
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, APPIUM);
                capabilities.setCapability(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS, IGNORE_UNIMPORTANT_VIEWS.toBoolean());
                capabilities.setCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT, NATIVE_WEB_SCREENSHOT.toBoolean());
                break;
            default:
                Assert.fail("Current test platform is not supported: " + PLATFORM_NAME);
        }
        return capabilities;
    }

    static URL getUrl() {
        try {
            return new URL("http://" + APPIUM_HOST.toString() + ":" + APPIUM_PORT + "/wd/hub");
        } catch (MalformedURLException e) {
            Assert.fail("Cannot initiate REST http interface listener URL");
            return null;
        }
    }

}
