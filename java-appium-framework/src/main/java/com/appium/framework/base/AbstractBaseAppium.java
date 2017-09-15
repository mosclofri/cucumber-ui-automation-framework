package com.appium.framework.base;

import com.support.framework.base.AbstractBase;
import com.support.framework.support.Property;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.Set;

abstract class AbstractBaseAppium extends AbstractBase<MobileElement> {

    private static final Logger LOG = Logger.getLogger(AbstractBaseAppium.class);
    private AppiumDriver<? extends MobileElement> driver;

    AbstractBaseAppium(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
        this.driver = driver;
    }

    public void androidAllowGranularPermissions() {
        setDriverWaitTime(1);
        while (true) {
            try {
                MobileElement element = getDriver().findElement(By.xpath("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']"));
                element.click();
            } catch (NoSuchElementException ignore) {
                break;
            }
        }
        setDefaultDriverWaitTime();
    }

    public void androidCheckAll(List<MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!androidIsChecked(anElementList)) {
                anElementList.click();
            }
        }
    }

    public Boolean androidIsAllChecked(List<MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!androidIsChecked(anElementList)) {
                return false;
            }
        }
        return true;
    }

    public Boolean androidIsChecked(MobileElement element) {
        return Boolean.parseBoolean(element.getAttribute("checked"));
    }

    public AppiumDriver<? extends MobileElement> getDriver() {
        return driver;
    }

    public boolean isPlatformAndroid() {
        return (Property.PLATFORM_NAME.toString().equalsIgnoreCase("android"));
    }

    public boolean isPlatformIOS() {
        return (Property.PLATFORM_NAME.toString().equalsIgnoreCase("ios"));
    }

    public void restartApp() {
        driver.closeApp();
        driver.launchApp();
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
