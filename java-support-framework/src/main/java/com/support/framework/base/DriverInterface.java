package com.support.framework.base;

public interface DriverInterface<T> {

    void initPageFactoryElements(Object object);

    void longPress(T element, int duration);

    void swipeDown();

    void swipeLeft();

    void swipeRight();

    void swipeUp();

}
