package com.support.framework.hook;

import com.support.framework.support.ThreadLocalMap;
import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.support.framework.support.Property.PLATFORM_NAME;
import static com.support.framework.support.Property.TESTRAIL_URL;
import static com.support.framework.support.Util.getCurrentScenario;
import static com.support.framework.support.Util.setCurrentScenario;
import static com.support.framework.support.Util.takeScreenShotAsByte;

public class Hooks {

    private static final Logger LOG = Logger.getLogger(Hooks.class);

    public static void hookAfter(WebDriver driver) {
        Scenario scenario = getCurrentScenario();
        LOG.info("### " + scenario.getStatus() + " ###");
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
        ThreadLocalMap.cleanup();
    }

    public static void hookBefore(Scenario scenario) {
        LOG.info("### Starting scenario: " + scenario.getName() + " ###");
        setCurrentScenario(scenario);
    }

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

    private static List<String> getScenariosStartWithCaseIds(Collection collection) {
        List<String> list = new ArrayList<>();
        for (Object currentTag : collection) {
            if (currentTag.toString().startsWith("@C")) {
                list.add(currentTag.toString().substring(2));
            }
        }
        return list;
    }
}
