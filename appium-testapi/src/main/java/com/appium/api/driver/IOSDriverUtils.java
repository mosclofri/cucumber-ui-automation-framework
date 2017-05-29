package com.appium.api.driver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.appium.api.driver.TestCapabilities.getDesiredCapabilities;
import static com.appium.api.driver.TestCapabilities.getUrl;
import static com.appium.api.support.Property.IMPLICIT_WAIT_TIME;

@Component
@Profile("IOS")
public class IOSDriverUtils {

    @Bean(destroyMethod = "quit")
    @Scope("cucumber-glue")
    private IOSDriver<? extends MobileElement> getDriver() {
        IOSDriver<? extends MobileElement> driver = new IOSDriver<>(getUrl(), getDesiredCapabilities());
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(IMPLICIT_WAIT_TIME), TimeUnit.SECONDS);
        return driver;
    }

}