package com.android.test.base;

import com.appium.api.base.AndroidBase;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class ApiDemosDriver extends AndroidBase {

    @Autowired
    public ApiDemosDriver(AndroidDriver<? extends MobileElement> driver) {
        super(driver);
    }

}
