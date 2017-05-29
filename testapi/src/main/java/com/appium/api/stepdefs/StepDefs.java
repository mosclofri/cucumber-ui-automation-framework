package com.appium.api.stepdefs;

import com.appium.api.base.AppiumBase;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StepDefs {

    @Autowired
    private AppiumBase appiumBase;

    @When("^I kill app and restart$")
    public void whenIKillAppAndRestart() {
        appiumBase.getDriver().closeApp();
        appiumBase.getDriver().launchApp();
    }

    @When("^I swipe down$")
    public void whenISwipeDown() {
        appiumBase.swipeDown();
    }

    @When("^I swipe left$")
    public void whenISwipeLeft() {
        appiumBase.swipeLeft();
    }

    @When("^I swipe right$")
    public void whenISwipeRight() {
        appiumBase.swipeRight();
    }

    @When("^I wait '(.*)' seconds$")
    public void whenIWaitSeconds(int arg0) {
        appiumBase.threadWait(arg0);
    }

}
