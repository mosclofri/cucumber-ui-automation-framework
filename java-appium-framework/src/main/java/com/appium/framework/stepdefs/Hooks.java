package com.appium.framework.stepdefs;

import com.appium.framework.base.BaseAppium;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

    @Autowired
    public BaseAppium baseAppium;

    @After
    public void after() {
        com.support.framework.hook.Hooks.hookAfter(baseAppium.getDriver());
    }

    @Before
    public void before(Scenario scenario) {
        com.support.framework.hook.Hooks.hookBefore(scenario);
    }

}
