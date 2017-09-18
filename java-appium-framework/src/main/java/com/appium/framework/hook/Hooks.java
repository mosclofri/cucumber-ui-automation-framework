package com.appium.framework.hook;

import com.appium.framework.base.AppiumBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static com.support.framework.hook.Hooks.hookAfter;
import static com.support.framework.hook.Hooks.hookBefore;

public class Hooks {

    @Autowired
    private AppiumBase appiumBase;

    @After
    public void after() {
        hookAfter(appiumBase.getDriver());
    }

    @Before
    public void before(Scenario scenario) {
        hookBefore(scenario);
    }

}
