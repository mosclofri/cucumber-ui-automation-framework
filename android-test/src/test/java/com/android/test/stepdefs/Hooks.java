package com.android.test.stepdefs;

import com.android.test.base.ApiDemosDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

public class Hooks {

    @Autowired
    private ApiDemosDriver apiDemosDriver;

    @After
    public void after(Scenario scenario) {
        if (!scenario.isFailed()) {
            scenario.embed(apiDemosDriver.takeScreenShotAsByte(), "image/png");
            scenario.embed(apiDemosDriver.captureLog().getBytes(StandardCharsets.UTF_8), "text/html");
        }
    }

}
