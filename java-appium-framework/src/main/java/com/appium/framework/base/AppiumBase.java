package com.appium.framework.base;

import com.support.framework.base.DriverInterface;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static java.time.Duration.ofSeconds;

@Component
@Scope("cucumber-glue")
public class AppiumBase extends AbstractAppiumBase implements DriverInterface<MobileElement> {

    private static final Logger LOG = Logger.getLogger(AppiumBase.class);
    private final double FIRST_MULTIPLIER = 0.80;
    private final double SECOND_MULTIPLIER = 0.20;

    public AppiumBase(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
        initPageFactoryElements(this);
    }

    @Override
    public void initPageFactoryElements(Object object) {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), object);
    }

    @Override
    public void longPress(MobileElement element, int seconds) {
        TouchAction action = new TouchAction(getDriver());
        action.longPress(element, ofSeconds(seconds)).perform().release();
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
        int startX = (int) (dimensions.width * first);
        int endX = (int) (dimensions.width * second);
        int startY = dimensions.height / 2;

        TouchAction touchAction = new TouchAction(getDriver());
        touchAction.press(startX, startY).moveTo(endX, startY).perform().release();
    }

    private void swipeUpOrDown(double first, double second) {
        Dimension dimensions = getDriver().manage().window().getSize();
        int startY = (int) (dimensions.getHeight() * first);
        int endY = (int) (dimensions.getHeight() * second);
        int startX = dimensions.width / 2;

        TouchAction touchAction = new TouchAction(getDriver());
        touchAction.press(startX, startY).moveTo(startX, endY).perform().release();
    }
}
