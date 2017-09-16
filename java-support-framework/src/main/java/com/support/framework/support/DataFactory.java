package com.support.framework.support;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

public class DataFactory {

    private static final String CURRENT_PERSON = "CURRENT_PERSON";
    private static Fairy fairy = Fairy.create();

    public static Person getCurrentPerson() {
        return ThreadLocalMap.getItem(CURRENT_PERSON, Person.class);
    }

    public static void setCurrentPerson(Person person) {
        ThreadLocalMap.getMap().put(CURRENT_PERSON, person);
    }

    public static Person getPerson() {
        Person person = fairy.person();
        setCurrentPerson(person);
        return person;
    }

    public static String getRandomEmail() {
        return fairy.person().getEmail();
    }

    public static String getRandomFullName() {
        return fairy.person().getFullName();
    }

    public static String getRandomLastName() {
        return fairy.person().getLastName();
    }

    public static String getRandomName() {
        return fairy.person().getFirstName();
    }

    public static String getRandomParagraph(int sentenceCount) {
        return fairy.textProducer().paragraph(sentenceCount);
    }

    public static String getRandomSentence(int worldCount) {
        return fairy.textProducer().sentence(worldCount);
    }
}

