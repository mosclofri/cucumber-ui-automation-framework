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
    private final double FIRST_MULTIPLIER = 0.80;
    private final double SECOND_MULTIPLIER = 0.20;
    private final int SWIPE_DURATION = 1000;

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
        swipeUpOrDown(FIRST_MULTIPLIER, SECOND_MULTIPLIER);
    }

    @Override
    public void swipeLeft() {
        swipeLeftOrRight(FIRST_MULTIPLIER, SECOND_MULTIPLIER);
    }

    @Override
    public void swipeRight() {
        swipeLeftOrRight(SECOND_MULTIPLIER, FIRST_MULTIPLIER);
    }

    @Override
    public void swipeUp() {
        swipeUpOrDown(SECOND_MULTIPLIER, FIRST_MULTIPLIER);
    }

    private void swipeLeftOrRight(double first, double second) {
        Dimension dimensions = getDriver().manage().window().getSize();
        int startx = (int) (dimensions.width * first);
        int endx = (int) (dimensions.width * second);
        int starty = dimensions.height / 2;
        getDriver().swipe(startx, starty, endx, starty, SWIPE_DURATION);
    }

    private void swipeUpOrDown(double first, double second) {
        Dimension dimensions = getDriver().manage().window().getSize();
        int scrollStart = (int) (dimensions.getHeight() * first);
        int scrollEnd = (int) (dimensions.getHeight() * second);
        getDriver().swipe(0, scrollStart, 0, scrollEnd, SWIPE_DURATION);
    }

}
