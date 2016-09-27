package com.android.test.stepdefs;

import com.android.test.base.ApiDemosDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

    @Autowired
    protected ApiDemosDriver apiDemosDriver;
    protected Scenario scenario;

    @After
    public void after(Scenario scenario) {
        this.scenario = scenario;
        scenario.embed(apiDemosDriver.takeScreenShotAsByte(), "image/png");
        scenario.embed(apiDemosDriver.captureLog().getBytes(), "text/html");
    }

}
