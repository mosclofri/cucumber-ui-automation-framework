package com.appium.framework.base;

import com.support.framework.base.AbstractBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.support.framework.support.Property.*;

abstract class AbstractBaseAppium extends AbstractBase<MobileElement> {

    private static final Logger LOG = Logger.getLogger(AbstractBaseAppium.class);
    private AppiumDriver<? extends MobileElement> driver;

    AbstractBaseAppium(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
        this.driver = driver;
    }

    public String captureLog() {
        LOG.info("Capturing device logs");
        String logType;
        if (PLATFORM_NAME.toString().equalsIgnoreCase("android"))
            logType = "logcat";
        else
            logType = "syslog";
        StringBuilder deviceLog = new StringBuilder();
        List<LogEntry> logEntries = driver.manage().logs().get(logType).getAll();
        for (LogEntry logLine : logEntries) {
            deviceLog.append(logLine).append(System.lineSeparator());
        }
        return deviceLog.toString();
    }

    public String getAndroidSDKVersion() {
        return executeShellReturnStringResult("adb -s " + DEVICE_NAME.toString() + " shell getprop ro.build.version.sdk");
    }

    public AppiumDriver<? extends MobileElement> getDriver() {
        return driver;
    }

    public void initElementsWithFieldDecorator(Object object) {
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Integer.parseInt(IMPLICIT_WAIT.toString()), TimeUnit.SECONDS), object);
    }

    public void initElementsWithFieldDecorator(Object object, int timeout) {
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, timeout, TimeUnit.SECONDS), object);
    }

    public void restartApp() {
        driver.closeApp();
        driver.launchApp();
    }

    public void swipeDown() {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.80);
        int scrollEnd = (int) (dimensions.getHeight() * 0.20);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

    public void swipeLeft() {
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.80);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void swipeRight() {
        Dimension size = getDriver().manage().window().getSize();
        int endx = (int) (size.width * 0.80);
        int startx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    public void swipeUp() {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.20);
        int scrollEnd = (int) (dimensions.getHeight() * 0.80);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

    public void switchContextToFirstWEBVIEW() {
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            if (context.contains("WEBVIEW")) {
                LOG.info("Switching context to: " + context);
                driver.context(context);
            } else {
                LOG.info("No WEBVIEW context available, keeping current context");
            }
        }
    }

    public void switchContextToNATIVE() {
        LOG.info("Switching context to NATIVE_APP");
        driver.context("NATIVE_APP");
    }

}
