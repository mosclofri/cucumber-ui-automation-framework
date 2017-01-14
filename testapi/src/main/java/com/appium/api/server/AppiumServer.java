package com.appium.api.server;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static com.appium.api.support.Property.APPIUM_HOST;
import static com.appium.api.support.Property.APPIUM_LOG_LEVEL;
import static com.appium.api.support.Property.APPIUM_PORT;
import static io.appium.java_client.service.local.AppiumDriverLocalService.buildService;

public final class AppiumServer {

    private final static AppiumDriverLocalService service;

    static {
        service = buildService(new AppiumServiceBuilder().withIPAddress(APPIUM_HOST).usingPort(Integer.parseInt(APPIUM_PORT))
                .withArgument(GeneralServerFlag.LOG_LEVEL, APPIUM_LOG_LEVEL));
    }

    public static void startAppiumServer() {
        service.start();
    }

    public static void stopAppiumServer() {
        if (service.isRunning()) {
            service.stop();
        }
    }

}
