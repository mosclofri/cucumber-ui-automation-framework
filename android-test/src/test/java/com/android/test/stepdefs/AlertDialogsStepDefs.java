package com.android.test.stepdefs;

import com.android.test.base.ApiDemosDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.MobileElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.fail;

public class AlertDialogsStepDefs {

    @Autowired
    public ApiDemosDriver alertDialogsDriver;

    @Autowired
    public GenericStepDefs genericStepDefs;

    @Given("^I am on 'Alert Dialogs' screen$")
    public void givenIAmOnAlertDialogsScreen() {
        genericStepDefs.givenIAmOnAppScreen();
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

    @When("^I check all week days for alarm$")
    public void whenICheckAllWeekDaysForAlarm() {
        List<? extends MobileElement> elementList = alertDialogsDriver.getElementList("id#text1");
        for (int i = 0; i < 5; i++) {
            if (!alertDialogsDriver.isChecked(elementList.get(i))) {
                elementList.get(i).click();
            }
        }
        alertDialogsDriver.click("id#button1");
    }

    @Then("^all days should be checked for alarm$")
    public void thenAllDaysShouldBeCheckedForAlarm() {
        givenIAmOnRepeatAlarmScreen();
        alertDialogsDriver.isAllChecked("id#text1");
    }

    @Then("^week days should be checked for alarm$")
    public void thenWeekDaysShouldBeCheckedForAlarm() {
        givenIAmOnRepeatAlarmScreen();
        List<? extends MobileElement> elementList = alertDialogsDriver.getElementList("id#text1");
        for (int i = 0; i < 5; i++) {
            if (!alertDialogsDriver.isChecked(elementList.get(i))) {
                fail("'" + elementList.get(i).getText() + "' is not checked");
            }
        }
    }

}
