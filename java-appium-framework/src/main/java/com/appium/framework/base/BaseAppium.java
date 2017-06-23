package com.appium.framework.base;

import com.support.framework.base.DriverInterface;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class BaseAppium extends AbstractBaseAppium implements DriverInterface {

    private static final Logger LOG = Logger.getLogger(BaseAppium.class);

    public BaseAppium(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    public void initPageFactoryElements(Object object) {
        PageFactory.initElements(
                new AppiumFieldDecorator(getDriver()), object);
    }

    @Override
    public void swipeDown() {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.80);
        int scrollEnd = (int) (dimensions.getHeight() * 0.20);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

    @Override
    public void swipeLeft() {
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.80);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    @Override
    public void swipeRight() {
        Dimension size = getDriver().manage().window().getSize();
        int endx = (int) (size.width * 0.80);
        int startx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        getDriver().swipe(startx, starty, endx, starty, 1000);
    }

    @Override
    public void swipeUp() {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * 0.20);
        int scrollEnd = (int) (dimensions.getHeight() * 0.80);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, 1000);
    }

}
