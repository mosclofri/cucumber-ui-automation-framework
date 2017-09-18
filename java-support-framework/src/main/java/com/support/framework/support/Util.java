package com.support.framework.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.ServerSocket;

public final class Util {

    private static final Logger LOG = Logger.getLogger(Util.class);

    public static int getPort(int currentPort) {
        if (currentPort == 0) {
            try {
                ServerSocket socket = new ServerSocket(0);
                socket.setReuseAddress(true);
                socket.close();
                return socket.getLocalPort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return currentPort;
    }

    public static void logCucumberStep() {
        StackTraceElement stackTraceElements = Thread.currentThread().getStackTrace()[2];
        String methodName = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(stackTraceElements.getMethodName()), ' ');
        LOG.info(methodName);
    }

    public static void skipScenario(String reasonToSkip) {
        LOG.info("Will skip current scenario. Reason to skip: " + reasonToSkip);
        throw new AssumptionViolatedException(reasonToSkip);
    }

    public static boolean stringIsEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static byte[] takeScreenShotAsByte(WebDriver driver) {
        LOG.info("Capturing a screenshot");
        threadWait(0.25);
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static void threadWait(double duration) {
        try {
            if (duration > 3) {
                LOG.info("Waiting '" + duration + "' duration");
            }
            Thread.sleep((long) (duration * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
