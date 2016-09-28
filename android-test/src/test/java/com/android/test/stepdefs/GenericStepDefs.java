package com.android.test.stepdefs;

import com.android.test.base.ApiDemosDriver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericStepDefs {

    @Autowired
    protected ApiDemosDriver apiDemosDriver;

    @When("^I click on '(.*)'$")
    public void iClickOnElement(String arg0) {
        apiDemosDriver.click(arg0);
    }

    @When("^I click on all unselected '(.*)'$")
    public void iClickOnAllUnselectedElements(String arg0) {
        apiDemosDriver.checkAll(arg0);
    }

    @Then("^'(.*)' should be displayed$")
    public void elementShouldBeDisplayed(String arg0) {
        apiDemosDriver.shouldDisplay(arg0);
    }

    @Then("^'(.*)' should be selected$")
    public void elementShouldBeSelected(String arg0) {
        apiDemosDriver.isChecked(arg0);
    }

    @Then("^all '(.*)' should be selected$")
    public void allElementsShouldBeSelected(String arg0) {
        apiDemosDriver.isAllChecked(arg0);
    }
}
