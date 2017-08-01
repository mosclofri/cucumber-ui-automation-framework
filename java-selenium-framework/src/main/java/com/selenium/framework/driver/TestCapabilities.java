package com.selenium.framework.driver;

import com.support.framework.support.Property;
import org.apache.log4j.Logger;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import static com.support.framework.support.Property.BROWSER_NAME;
import static com.support.framework.support.Property.GRID_URL;
import static com.support.framework.support.Property.PLATFORM_NAME;
import static com.support.framework.support.Property.SELENIUM_LOG;

class TestCapabilities {

    private static final Logger LOG = Logger.getLogger(TestCapabilities.class);

    private DesiredCapabilities desiredCapabilities;
    private LoggingPreferences loggingPreferences;

    DesiredCapabilities getDesiredCapabilities() {
        loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.parse(SELENIUM_LOG.toString()));
        loggingPreferences.enable(LogType.DRIVER, Level.parse(SELENIUM_LOG.toString()));
        if (PLATFORM_NAME.toString().equalsIgnoreCase("android") || PLATFORM_NAME.toString().equalsIgnoreCase("ios")) {
            return getMobileDesiredCapabilities();
        } else {
            return getDesktopDesiredCapabilities();
        }
    }

    private DesiredCapabilities getDesktopDesiredCapabilities() {
        LOG.info("Getting Desktop Capabilities");
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(BROWSER_NAME.toString());
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
        return desiredCapabilities;
    }

    private DesiredCapabilities getMobileDesiredCapabilities() {
        LOG.info("Getting Mobile Capabilities");
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", PLATFORM_NAME);
        desiredCapabilities.setCapability("deviceName", Property.DEVICE_NAME);
        desiredCapabilities.setCapability("platformVersion", Property.PLATFORM_VERSION);
        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BROWSER_NAME);

        if (PLATFORM_NAME.toString().equalsIgnoreCase("android") && BROWSER_NAME.toString().equalsIgnoreCase("chrome")) {
            desiredCapabilities.setCapability("bundleId", "com.android.chrome");
        }
        return desiredCapabilities;
    }

    URL getRemoteUrl() {
        try {
            String url = "http://" + GRID_URL + "/wd/hub";
            LOG.info("Grid URL : " + url);
            return new URL(url);
        } catch (MalformedURLException e) {
            System.err.println("Cannot initiate REST http interface listener URL");
            return null;
        }
    }

}
