package com.appium.api.driver;

import com.appium.api.server.AppiumServer;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class IOSDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    protected IOSDriver<? extends MobileElement> getDriver() throws MalformedURLException {
        IOSDriver<? extends MobileElement> driver = new IOSDriver<>(AppiumServer.getUrl(), AppiumServer.getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("implicit.wait")), TimeUnit.SECONDS);
        return driver;
    }

}