package com.appium.api.server;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class AppiumServer {

    AppiumDriverLocalService service;

    public AppiumServer(String ip, int port) {
        this.service = AppiumDriverLocalService.buildService((new AppiumServiceBuilder()).withIPAddress(ip).usingPort(port)
                .withArgument(GeneralServerFlag.LOG_LEVEL, System.getProperty("appium.log")));
    }

    public static URL getUrl() throws MalformedURLException {
        return new URL("http://" + System.getProperty("appium.host") + ":" + System.getProperty("appium.port") + "/wd/hub");
    }

    public static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            capabilities.setCapability(MobileCapabilityType.APP,
                    new File(URLDecoder.decode(ClassLoader.getSystemResource(System.getProperty("file")).getFile(),
                            StandardCharsets.UTF_8.toString())).getAbsolutePath());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String currentTestPlatform = System.getProperty("platform.name");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, currentTestPlatform);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, System.getProperty("device.name"));
        capabilities.setCapability(MobileCapabilityType.UDID, System.getProperty("device.name"));
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.valueOf(System.getProperty("no.reset")));
        switch (currentTestPlatform.toLowerCase()) {
            case "ios":
                break;
                //some ios capability
            case "android":
                capabilities.setCapability(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS, true);
                break;
            default:
                throw new IllegalArgumentException("Current test platform is not supported: " + currentTestPlatform);
        }
        return capabilities;
    }

    public void startAppiumServer() {
        this.service.start();
    }

    public void stopAppiumServer() {
        if (this.service.isRunning()) {
            this.service.stop();
        }
    }

}
