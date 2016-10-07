package com.appium.api.driver;

import com.appium.api.server.AppiumServer;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class AndroidDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    protected AndroidDriver<? extends MobileElement> getDriver() throws MalformedURLException {
        AndroidDriver<? extends MobileElement> driver = new AndroidDriver<>(AppiumServer.getUrl(), AppiumServer.getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("implicit.wait")), TimeUnit.SECONDS);
        return driver;
    }

}