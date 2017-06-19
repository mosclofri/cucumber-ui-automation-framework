package com.appium.framework.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class BaseAppium extends AbstractBaseAppium {

    private static final Logger LOG = Logger.getLogger(BaseAppium.class);

    public BaseAppium(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    public void androidAllowGranularPermissions() {
        setDriverWaitTime(1);
        while (true) {
            try {
                MobileElement element = getDriver().findElement(By.xpath("//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']"));
                element.click();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        setDefaultDriverWaitTime();
    }

    public void androidCheckAll(List<MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                anElementList.click();
            }
        }
    }

    public Boolean androidIsAllChecked(List<MobileElement> elements) {
        for (MobileElement anElementList : elements) {
            if (!Boolean.parseBoolean(anElementList.getAttribute("checked"))) {
                return false;
            }
        }
        return true;
    }

    public Boolean androidIsChecked(MobileElement element) {
        return Boolean.parseBoolean(element.getAttribute("checked"));
    }

    public Boolean androidIsChecked(List<MobileElement> element, int index) {
        return Boolean.parseBoolean(element.get(index).getAttribute("checked"));
    }

}
