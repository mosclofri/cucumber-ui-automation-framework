package com.support.framework.support;

import static com.support.framework.support.Property.APPIUM_URL;

public class Util {

    public static String getURLPort(String uop) {
        String s = APPIUM_URL.toString();
        switch (uop) {
            case "url":
                return s.split("\\:")[0];
            case "port":
                return s.split("\\:")[1];
            default:
                return APPIUM_URL.toString();
        }
    }

}
