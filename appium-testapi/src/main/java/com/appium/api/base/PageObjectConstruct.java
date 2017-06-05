package com.appium.api.base;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class PageObjectConstruct {

    @Autowired
    private AppiumBase appiumBase;

    public AppiumBase getAppium() {
        return appiumBase;
    }

    @PostConstruct
    private void init() {
        appiumBase.initElementsWithFieldDecorator(this);
    }

}
