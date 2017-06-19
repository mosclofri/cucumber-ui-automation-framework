package com.selenium.framework.driver;

import com.support.framework.support.MobileCapabilityType;
import com.support.framework.support.Property;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static com.support.framework.support.Property.BROWSER_NAME;
import static com.support.framework.support.Property.PLATFORM_NAME;

class TestCapabilities {
    private static final Logger LOG = Logger.getLogger(TestCapabilities.class);

    DesiredCapabilities getDesiredCapabilities() {
        if (PLATFORM_NAME.toString().equalsIgnoreCase("android") || PLATFORM_NAME.toString().equalsIgnoreCase("ios")) {
            return getMobileDesiredCapabilities();
        } else {
            return getDesktopDesiredCapabilities();
        }
    }

    URL getRemoteUrl() {
        try {
            String url = "http://" + Property.GRID_URL + "/wd/hub";
            LOG.info("Grid URL : " + url);
            return new URL(url);
        } catch (MalformedURLException e) {
            LOG.error("Cannot initiate REST http interface listener URL");
            return null;
        }
    }

    private DesiredCapabilities getDesktopDesiredCapabilities() {
        LOG.info("Getting Desktop Capabilities");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(BROWSER_NAME.toString());
        return desiredCapabilities;
    }

    private DesiredCapabilities getMobileDesiredCapabilities() {
        LOG.info("Getting Mobile Capabilities");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BROWSER_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Property.DEVICE_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, Property.PLATFORM_VERSION);

        if (PLATFORM_NAME.toString().equalsIgnoreCase("android") && BROWSER_NAME.toString().equalsIgnoreCase("chrome")) {
            desiredCapabilities.setCapability("bundleId", "com.android.chrome");
        }
        return desiredCapabilities;
    }
}
