package com.support.framework.hook;

import com.support.framework.base.AbstractBase;
import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.support.framework.base.AbstractBase.scenario;
import static com.support.framework.support.Property.PLATFORM_NAME;
import static com.support.framework.support.Property.TESTRAIL_URL;
import static com.support.framework.support.Util.takeScreenShotAsByte;

public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class);

    private static String captureLog(WebDriver driver) {
        LOG.info("Capturing device logs");
        String logType;
        if (PLATFORM_NAME.toString().equalsIgnoreCase("android"))
            logType = "logcat";
        else
            logType = "syslog";
        StringBuilder deviceLog = new StringBuilder();
        List<LogEntry> logEntries = driver.manage().logs().get(logType).getAll();
        for (LogEntry logLine : logEntries) {
            deviceLog.append(logLine).append(System.lineSeparator());
        }
        return deviceLog.toString();
    }

    public static void hookAfter(WebDriver driver) {
        LOG.info("### " + scenario.getStatus().toUpperCase() + " ###");
        LOG.info("### Ending scenario: " + scenario.getName() + " ###");
        List<String> caseList = getScenariosStartWithCaseIds(scenario.getSourceTagNames());
        for (String currentTag : caseList) {
            scenario.write("<a href=\"" + TESTRAIL_URL + currentTag + "\"> Test Scenario: C" + currentTag + "</a>");
        }
        if (scenario.isFailed()) {
            scenario.embed(takeScreenShotAsByte(driver), "image/png");
            //ToDo Need better Selenium control
            /*if (!PLATFORM_NAME.toString().equalsIgnoreCase("web")) {
                scenario.embed(captureLog(driver).getBytes(StandardCharsets.UTF_8), "text/html");
            }*/
        }
    }

    private static List<String> getScenariosStartWithCaseIds(Collection collection) {
        List<String> list = new ArrayList<>();
        for (Object currentTag : collection) {
            if (currentTag.toString().startsWith("@C")) {
                list.add(currentTag.toString().substring(2));
            }
        }
        return list;
    }

    public static void hookBefore(Scenario scenario) {
        AbstractBase.scenario = scenario;
        LOG.info("### Starting scenario: " + scenario.getName() + " ###");
    }

}
