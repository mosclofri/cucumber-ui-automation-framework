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
        baseAppium.hookAfter(baseAppium.captureLog(), baseAppium.takeScreenShotAsByte());
    }

    @Before
    public void before(Scenario scenario) {
        baseAppium.hookBefore(scenario);
    }

}
