package com.android.test.stepdefs;

import com.android.test.base.ApiDemosDriver;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiDemosGenericStepDefs {

    @Autowired
    public ApiDemosDriver apiDemosGenericStepDefs;

    @Given("^I am on 'App' screen$")
    public void givenIAmOnAppScreen() {
        apiDemosGenericStepDefs.click("App");
    }

}
