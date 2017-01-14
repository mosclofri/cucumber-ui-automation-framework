package com.appium.api.stepdefs;

import com.appium.api.base.AbstractBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.appium.api.support.Property.TESTRAIL_URL;

public class Hooks {

    @Autowired
    public AbstractBase abstractBase;

    @Before
    public void before(Scenario scenario) {
        abstractBase.setScenario(scenario);
    }

    @After
    public void after() {
        List<String> caseList = getScenariosStartWithCaseIds(abstractBase.scenario.getSourceTagNames());
        for (String currentTag : caseList) {
            abstractBase.scenario.write("<a href=\"" + TESTRAIL_URL + currentTag + "\"> TestRail Scenario: C" + currentTag + "</a>");
        }
        if (abstractBase.scenario.isFailed()) {
            abstractBase.scenario.embed(abstractBase.takeScreenShotAsByte(), "image/png");
            abstractBase.scenario.embed(abstractBase.captureLog().getBytes(StandardCharsets.UTF_8), "text/html");
        }
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
