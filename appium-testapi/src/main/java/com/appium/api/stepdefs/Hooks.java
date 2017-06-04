package com.appium.api.stepdefs;

import com.appium.api.base.AppiumBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.appium.api.support.Property.TESTCASE_URL;

public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class);

    @Autowired
    public AppiumBase appiumBase;

    @After
    public void after() {
        LOG.info("### Ending scenario: " + appiumBase.getScenario().getName() + " ###");
        List<String> caseList = getScenariosStartWithCaseIds(appiumBase.scenario.getSourceTagNames());
        for (String currentTag : caseList) {
            appiumBase.scenario.write("<a href=\"" + TESTCASE_URL + currentTag + "\"> Test Scenario: C" + currentTag + "</a>");
        }
        if (appiumBase.scenario.isFailed()) {
            appiumBase.scenario.embed(appiumBase.takeScreenShotAsByte(), "image/png");
            //appiumBase.scenario.embed(appiumBase.captureLog().getBytes(StandardCharsets.UTF_8), "text/html");
        }
    }

    @Before
    public void before(Scenario scenario) {
        appiumBase.setScenario(scenario);
        LOG.info("### Starting scenario: " + appiumBase.getScenario().getName() + " ###");
    }

    public List<String> getScenariosStartWithCaseIds(Collection collection) {
        List<String> list = new ArrayList<>();
        for (Object currentTag : collection) {
            if (currentTag.toString().startsWith("@C")) {
                list.add(currentTag.toString().substring(2));
            }
        }
        return list;
    }

}
