package com.appium.api.driver;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.appium.api.support.Property.*;

final class TestCapabilities {

    private static final Logger log = Logger.getLogger(TestCapabilities.class);

    public static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            capabilities.setCapability(MobileCapabilityType.APP,
                    new File(URLDecoder.decode(ClassLoader.getSystemResource((APP_FILE)).getFile(),
                            StandardCharsets.UTF_8.toString())).getAbsolutePath());
        } catch (NullPointerException e) {
            log.error(e);
            System.exit(1);
        } catch (UnsupportedEncodingException e) {
            log.warn(e);
        }
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, APPIUM_PLATFORM);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.UDID, UUID);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.valueOf(NO_RESET));
        switch (APPIUM_PLATFORM.toUpperCase()) {
            case "IOS":
                //some ios capability
                break;
            case "ANDROID":
                capabilities.setCapability(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS, true);
                break;
            default:
                log.fatal("Current test platform is not supported: " + APPIUM_PLATFORM);
                System.exit(1);
        }
        return capabilities;
    }

}
