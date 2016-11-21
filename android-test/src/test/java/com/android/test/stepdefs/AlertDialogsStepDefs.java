package com.android.test.stepdefs;

import com.android.test.base.ApiDemosDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class AlertDialogsStepDefs {

    @Autowired
    public ApiDemosDriver alertDialogsDriver;

    @Autowired
    public ApiDemosGenericStepDefs apiDemosGenericStepDefs;

    @Given("^I am on 'Alert Dialogs' screen$")
    public void givenIAmOnAlertDialogsScreen() {
        apiDemosGenericStepDefs.givenIAmOnAppScreen();
        alertDialogsDriver.click("Alert Dialogs");
    }

    @Given("^I am on Repeat Alarm screen$")
    public void givenIAmOnRepeatAlarmScreen() {
        alertDialogsDriver.click("Repeat alarm");
    }

    @When("^I check all days for alarm$")
    public void whenICheckAllDaysForAlarm() {
        alertDialogsDriver.checkAll("id#text1");
        alertDialogsDriver.click("id#button1");
    }

    @Then("^all days should be checked for alarm$")
    public void thenAllDaysShouldBeCheckedForAlarm() {
        givenIAmOnRepeatAlarmScreen();
        alertDialogsDriver.isAllChecked("id#text1");
    }
}
