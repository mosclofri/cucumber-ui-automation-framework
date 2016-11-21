package com.appium.api.stepdefs;

import com.appium.api.base.AbstractBase;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StepDefs {

    @Autowired
    private AbstractBase abstractBase;

    @When("^I click on '(.*)'$")
    public void iClickOnElement(String arg0) {
        abstractBase.click(arg0);
    }

    @When("^I type '(.*)' to '(.*)'$")
    public void iClickOnElement(String arg0, String arg1) {
        abstractBase.type(arg1, arg0);
    }

    @Then("^'(.*)' should be displayed$")
    public void elementShouldBeDisplayed(String arg0) {
        abstractBase.shouldDisplay(arg0);
    }

    @Then("^'(.*)' should not be displayed$")
    public void elementShouldNotBeDisplayed(String arg0) {
        abstractBase.shouldNotDisplay(arg0);
    }

}
