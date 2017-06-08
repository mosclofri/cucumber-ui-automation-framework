package com.appium.api.driver;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.appium.api.support.Property.*;

final class TestCapabilities {

    private static final Logger LOG = Logger.getLogger(TestCapabilities.class);

    public static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            capabilities.setCapability(MobileCapabilityType.APP,
                    new File(URLDecoder.decode(ClassLoader.getSystemResource((APP_FILE.toString())).getFile(),
                            StandardCharsets.UTF_8.toString())).getAbsolutePath());
        } catch (NullPointerException e) {
            LOG.error("Cannot find given application file" + e);
        } catch (UnsupportedEncodingException e) {
            LOG.warn(e);
        }
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, APPIUM_PLATFORM);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.UDID, UUID);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.valueOf(NO_RESET.toString()));
        switch (APPIUM_PLATFORM.toString().toUpperCase()) {
            case "IOS":
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                break;
            case "ANDROID":
                capabilities.setCapability(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS, true);
                capabilities.setCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT, true);
                break;
            default:
                LOG.error("Current test platform is not supported: " + APPIUM_PLATFORM);
        }
        return capabilities;
    }

    public static URL getUrl() {
        try {
            return new URL("http://" + APPIUM_HOST + ":" + APPIUM_PORT + "/wd/hub");
        } catch (MalformedURLException e) {
            System.err.println("Cannot initiate REST http interface listener URL");
            return null;
        }
    }

}
