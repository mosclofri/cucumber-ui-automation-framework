package com.selenium.framework.base;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class PageObjectConstruct {

    @Autowired
    public BaseSelenium seleniumBase;

    public WebDriver getDriver() {
        return seleniumBase.getDriver();
    }

    public BaseSelenium getHelper() {
        return seleniumBase;
    }

    @PostConstruct
    public void init() {
        seleniumBase.initPageFactoryElements(this);
    }
}
