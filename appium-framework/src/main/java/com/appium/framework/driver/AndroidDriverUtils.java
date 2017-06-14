package com.appium.framework.driver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.appium.framework.driver.TestCapabilities.getDesiredCapabilities;
import static com.appium.framework.driver.TestCapabilities.getUrl;
import static com.appium.framework.support.Property.IMPLICIT_WAIT;

@Component
@Profile("Android")
public class AndroidDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    private AndroidDriver<? extends MobileElement> getDriver() {
        AndroidDriver<? extends MobileElement> driver = new AndroidDriver<>(getUrl(), getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(IMPLICIT_WAIT.toString()), TimeUnit.SECONDS);
        return driver;
    }

}