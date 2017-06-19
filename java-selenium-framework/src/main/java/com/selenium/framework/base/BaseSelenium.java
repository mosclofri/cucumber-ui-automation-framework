package com.selenium.framework.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class BaseSelenium extends AbstractBaseSelenium {

    private static final Logger LOG = Logger.getLogger(BaseSelenium.class);

    public BaseSelenium(WebDriver driver) {
        super(driver);
    }

}
