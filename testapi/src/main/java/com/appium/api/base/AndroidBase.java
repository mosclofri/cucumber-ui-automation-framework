package com.appium.api.base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.fail;

public class AndroidBase extends AbstractBase {

    public AndroidBase(AndroidDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AndroidDriver<? extends MobileElement> getDriver() {
        return (AndroidDriver<? extends MobileElement>) driver;
    }

    @Override
    public String captureLog() {
        return captureLog("logcat");
    }

    public Boolean isChecked(MobileElement element) {
        return Boolean.parseBoolean(element.getAttribute("checked"));
    }

    public void isAllChecked(String elements) {
        for (MobileElement anElementList : getElementList(elements)) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                fail("'" + anElementList.getText() + "' is not checked");
            }
        }
    }

    public void checkAll(String elements) {
        for (MobileElement anElementList : getElementList(elements)) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked")))
                anElementList.click();
        }
    }

}
