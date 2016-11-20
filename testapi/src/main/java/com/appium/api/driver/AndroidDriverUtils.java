package com.appium.api.driver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

import static com.appium.api.driver.TestCapabilities.getDesiredCapabilities;
import static com.appium.api.support.Property.IMPLICIT_WAIT_TIME;

public class AndroidDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    private AndroidDriver<? extends MobileElement> getDriver() {
        AndroidDriver<? extends MobileElement> driver = new AndroidDriver<>(getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(IMPLICIT_WAIT_TIME), TimeUnit.SECONDS);
        return driver;
    }

}