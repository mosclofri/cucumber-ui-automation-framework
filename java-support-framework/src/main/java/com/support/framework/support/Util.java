package com.support.framework.support;

import io.codearte.jfairy.Fairy;

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

    public static boolean stringIsEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static class DataGenerator {
        public static String getRandomEmail() {
            return Fairy.create().person().getEmail();
        }

        public static String getRandomFullName() {
            return Fairy.create().person().getFullName();
        }

        public static String getRandomLastName() {
            return Fairy.create().person().getLastName();
        }

        public static String getRandomName() {
            return Fairy.create().person().getFirstName();
        }

        public static String getRandomParagraph(int sentenceCount) {
            return Fairy.create().textProducer().paragraph(sentenceCount);
        }

        public static String getRandomSentence(int worldCount) {
            return Fairy.create().textProducer().sentence(worldCount);
        }
    }

}
