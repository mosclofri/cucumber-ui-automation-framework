package com.android.test.stepdefs;

import com.android.test.base.ApiDemosDriver;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericStepDefs {

    @Autowired
    public ApiDemosDriver genericStepDefs;

    @Given("^I am on 'App' screen$")
    public void givenIAmOnAppScreen() {
        genericStepDefs.compareImage("main_screen.png", 1);
        genericStepDefs.click("App");
    }

}
