package com.selenium.framework.stepdefs;

import com.selenium.framework.base.BaseSelenium;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class);

    @Autowired
    public BaseSelenium baseSelenium;

    @After
    public void after() {
        baseSelenium.hookAfter(null, baseSelenium.takeScreenShotAsByte());
    }

    @Before
    public void before(Scenario scenario) {
        baseSelenium.hookBefore(scenario);
    }

}
