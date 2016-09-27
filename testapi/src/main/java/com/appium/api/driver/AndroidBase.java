package com.appium.api.driver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AndroidBase extends AbstractBase {

    public AndroidBase(AndroidDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    public AndroidDriver<? extends MobileElement> getDriver() {
        return (AndroidDriver<? extends MobileElement>) driver;
    }

    @Override
    public String captureLog() {
        return captureLog("logcat");
    }

    public Boolean isChecked(String element) {
        return Boolean.parseBoolean(getElement(element).getAttribute("checked"));
    }

    public Boolean isAllChecked(String elements) {
        for (MobileElement anElementList : getElementList(elements)) {
            if (Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                return false;
            }
        }
        return true;
    }

    public void checkAll(String elements) {
        for (MobileElement anElementList : getElementList(elements)) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked")))
                anElementList.click();
        }
    }

}
