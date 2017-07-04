package com.selenium.framework.base;

import com.support.framework.base.DriverInterface;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class BaseSelenium extends AbstractBaseSelenium implements DriverInterface {

    private static final Logger LOG = Logger.getLogger(BaseSelenium.class);

    public BaseSelenium(WebDriver driver) {
        super(driver);
    }

    @Override
    public void initPageFactoryElements(Object object) {
        PageFactory.initElements(getDriver(), object);
    }

    @Override
    public void swipeDown() {
        //ToDo
    }

    @Override
    public void swipeLeft() {
        //ToDo
    }

    @Override
    public void swipeRight() {
        //ToDo
    }

    @Override
    public void swipeUp() {
        //ToDo
    }


}
