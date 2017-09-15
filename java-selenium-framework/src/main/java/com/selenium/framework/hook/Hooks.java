package com.selenium.framework.hook;

import com.selenium.framework.base.BaseSelenium;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static com.support.framework.hook.Hooks.hookAfter;
import static com.support.framework.hook.Hooks.hookBefore;

public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class);

    @Autowired
    public BaseSelenium baseSelenium;

    @After
    public void after() {
        hookAfter(baseSelenium.getDriver());
    }

    @Before
    public void before(Scenario scenario) {
        hookBefore(scenario);
    }

}
