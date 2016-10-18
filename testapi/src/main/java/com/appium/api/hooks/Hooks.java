package com.appium.api.hooks;

import com.appium.api.base.AbstractBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

public class Hooks {

    @Autowired
    private AbstractBase abstractBase;

    @After
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.embed(abstractBase.takeScreenShotAsByte(), "image/png");
            scenario.embed(abstractBase.captureLog().getBytes(StandardCharsets.UTF_8), "text/html");
        }
    }

}
