package com.appium.api.driver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.appium.api.driver.TestCapabilities.getDesiredCapabilities;
import static com.appium.api.driver.TestCapabilities.getUrl;
import static com.appium.api.support.Property.IMPLICIT_WAIT_TIME;

@Component
@Profile("Android")
public class AndroidDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    private AndroidDriver<? extends MobileElement> getDriver() {
        AndroidDriver<? extends MobileElement> driver = new AndroidDriver<>(getUrl(), getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(IMPLICIT_WAIT_TIME), TimeUnit.SECONDS);
        return driver;
    }

}