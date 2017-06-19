package com.appium.framework.core;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static com.support.framework.support.Property.APPIUM_LOG;
import static com.support.framework.support.Util.getURLPort;
import static io.appium.java_client.service.local.AppiumDriverLocalService.buildService;

public final class AppiumServer {

    private final static AppiumDriverLocalService service;

    static {
        service = buildService(new AppiumServiceBuilder().withIPAddress(getURLPort("url"))
                .usingPort(Integer.parseInt(getURLPort("port")))
                .withArgument(GeneralServerFlag.LOG_LEVEL, APPIUM_LOG.toString()));
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
