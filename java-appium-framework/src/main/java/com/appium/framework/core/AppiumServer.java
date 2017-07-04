package com.appium.framework.core;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static com.appium.framework.core.AppiumCukes.APPIUM_PORT;
import static com.support.framework.support.Property.APPIUM_HOST;
import static com.support.framework.support.Property.APPIUM_LOG;
import static io.appium.java_client.service.local.AppiumDriverLocalService.buildService;

public final class AppiumServer {

    private final static AppiumDriverLocalService service;

    static {
        service = buildService(new AppiumServiceBuilder().withIPAddress(APPIUM_HOST.toString())
                .usingPort(APPIUM_PORT)
                .withArgument(GeneralServerFlag.LOG_LEVEL, APPIUM_LOG.toString())
                .withArgument(GeneralServerFlag.PRE_LAUNCH)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE));
    }

    static void startAppiumServer() {
        service.start();
    }

    static void stopAppiumServer() {
        if (service.isRunning()) {
            service.stop();
        }
    }

}
