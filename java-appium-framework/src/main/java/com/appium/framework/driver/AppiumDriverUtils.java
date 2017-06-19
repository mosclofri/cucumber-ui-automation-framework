package com.appium.framework.driver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.appium.framework.driver.TestCapabilities.getDesiredCapabilities;
import static com.appium.framework.driver.TestCapabilities.getUrl;
import static com.support.framework.support.Property.IMPLICIT_WAIT;

@Component
public class AppiumDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Profile("Android")
    private AndroidDriver<? extends MobileElement> getAndroidDriver() {
        AndroidDriver<? extends MobileElement> driver = new AndroidDriver<>(getUrl(), getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(IMPLICIT_WAIT.toString()), TimeUnit.SECONDS);
        return driver;
    }

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    @Profile("IOS")
    private IOSDriver<? extends MobileElement> getIOSDriver() {
        IOSDriver<? extends MobileElement> driver = new IOSDriver<>(getUrl(), getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(IMPLICIT_WAIT.toString()), TimeUnit.SECONDS);
        return driver;
    }

}
