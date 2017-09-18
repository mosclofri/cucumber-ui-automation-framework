package com.selenium.framework.hook;

import com.selenium.framework.base.SeleniumBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static com.support.framework.hook.Hooks.hookAfter;
import static com.support.framework.hook.Hooks.hookBefore;

public class Hooks {

    @Autowired
    private SeleniumBase seleniumBase;

    @After
    public void after() {
        hookAfter(seleniumBase.getDriver());
    }

    @Before
    public void before(Scenario scenario) {
        hookBefore(scenario);
    }

}
