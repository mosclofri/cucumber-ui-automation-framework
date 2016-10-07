package com.appium.api.server;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AppiumServer {

    AppiumDriverLocalService service;

    public AppiumServer(String ip, int port) {
        String osName = System.getProperty("os.name").toLowerCase();
        String logLevel = System.getProperty("appium.log");
        String appiumInstallationDir;
        if (osName.contains("windows")) {
            appiumInstallationDir = "C:/Program Files (x86)";
            this.service = AppiumDriverLocalService.buildService((new AppiumServiceBuilder()).usingDriverExecutable(
                    new File(appiumInstallationDir + File.separator + "Appium" + File.separator + "node.exe")).withAppiumJS(
                    new File(appiumInstallationDir + File.separator + "Appium" + File.separator + "node_modules" +
                            File.separator + "appium" + File.separator + "bin" + File.separator + "appium.js")).withIPAddress(ip).usingPort(port)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, logLevel));
        } else if (osName.contains("mac")) {
            appiumInstallationDir = "/Applications";
            this.service = AppiumDriverLocalService.buildService((new AppiumServiceBuilder()).usingDriverExecutable(
                    new File(appiumInstallationDir + "/Appium.app/Contents/Resources/node/bin/node")).withAppiumJS(
                    new File(appiumInstallationDir + "/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js")).withIPAddress(ip).usingPort(port)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, logLevel));
        } else if (osName.contains("linux")) {
            appiumInstallationDir = System.getenv("HOME") + "/.linuxbrew/";
            this.service = AppiumDriverLocalService.buildService((new AppiumServiceBuilder()).usingDriverExecutable(
                    new File(appiumInstallationDir + "/bin/node")).withAppiumJS(
                    new File(appiumInstallationDir + "/lib/node_modules/appium/bin/appium.js")).withIPAddress(ip).usingPort(port)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, logLevel));
        }
    }

    public void startAppiumServer() {
        this.service.start();
    }

    public void stopAppiumServer() {
        this.service.stop();
    }

    public static URL getUrl() throws MalformedURLException {
        return new URL("http://" + System.getProperty("appium.host") + ":" + System.getProperty("appium.port") + "/wd/hub");
    }

    public static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, System.getProperty("platform.name"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, System.getProperty("device.name"));
        capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.valueOf(System.getProperty("no.reset")));
        capabilities.setCapability(MobileCapabilityType.APP, new File(ClassLoader.getSystemResource(System.getProperty("appfile")).getFile()).getAbsolutePath());
        return capabilities;
    }
}
