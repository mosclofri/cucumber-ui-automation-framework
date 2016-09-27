package com.android.test.utils;

import com.android.test.RunCukesTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppiumDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    protected AndroidDriver<? extends MobileElement> getDriver() throws MalformedURLException {
        String appFile = System.getProperty("appfile");
        String platformName = System.getProperty("platform.name");
        String deviceName = System.getProperty("device.name");
        Boolean noReset = Boolean.valueOf(System.getProperty("no.reset"));
        int implicitWait = Integer.parseInt(System.getProperty("implicit.wait"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);
        capabilities.setCapability(MobileCapabilityType.APP, new File(ClassLoader.getSystemResource(appFile).getFile()).getAbsolutePath());
        URL serverUrl = new URL("http://" + RunCukesTest.appiumHost + ":" + RunCukesTest.appiumPort + "/wd/hub");
        AndroidDriver<? extends MobileElement> driver = new AndroidDriver<>(serverUrl, capabilities);
        driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        return driver;
    }

}