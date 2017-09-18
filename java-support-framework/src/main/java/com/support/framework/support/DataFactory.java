package com.support.framework.support;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.DateProducer;
import io.codearte.jfairy.producer.company.Company;
import io.codearte.jfairy.producer.payment.CreditCard;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.text.TextProducer;

public final class DataFactory {

    private static final String CURRENT_PERSON = "CURRENT_PERSON";
    private static final String CURRENT_COMPANY = "CURRENT_COMPANY";
    private static final String CURRENT_TEXT_PRODUCER = "CURRENT_TEXT_PRODUCER";
    private static final String CURRENT_DATE_PRODUCER = "CURRENT_DATE_PRODUCER";
    private static final String CURRENT_CREDIT_CARD = "CURRENT_CREDIT_CARD";
    private static Fairy fairy = Fairy.create();

    public static Company getCompany() {
        Company company = fairy.company();
        setCurrentCompany(company);
        return company;
    }

    public static CreditCard getCreditCard() {
        CreditCard creditCard = fairy.creditCard();
        setCurrentCreditCard(creditCard);
        return creditCard;
    }

    public static Company getCurrentCompany() {
        return ThreadLocalMap.getItem(CURRENT_COMPANY, Company.class);
    }

    public static void setCurrentCompany(Company company) {
        ThreadLocalMap.getMap().put(CURRENT_COMPANY, company);
    }

    public static CreditCard getCurrentCreditCard() {
        return ThreadLocalMap.getItem(CURRENT_CREDIT_CARD, CreditCard.class);
    }

    public static void setCurrentCreditCard(CreditCard creditCard) {
        ThreadLocalMap.getMap().put(CURRENT_CREDIT_CARD, creditCard);
    }

    public static DateProducer getCurrentDateProducer() {
        return ThreadLocalMap.getItem(CURRENT_DATE_PRODUCER, DateProducer.class);
    }

    public static void setCurrentDateProducer(DateProducer dateProducer) {
        ThreadLocalMap.getMap().put(CURRENT_DATE_PRODUCER, dateProducer);
    }

    public static Person getCurrentPerson() {
        return ThreadLocalMap.getItem(CURRENT_PERSON, Person.class);
    }

    public static void setCurrentPerson(Person person) {
        ThreadLocalMap.getMap().put(CURRENT_PERSON, person);
    }

    public static TextProducer getCurrentTextProducer() {
        return ThreadLocalMap.getItem(CURRENT_TEXT_PRODUCER, TextProducer.class);
    }

    public static void setCurrentTextProducer(TextProducer textProducer) {
        ThreadLocalMap.getMap().put(CURRENT_TEXT_PRODUCER, textProducer);
    }

    public static DateProducer getDateProducer() {
        DateProducer dateProducer = fairy.dateProducer();
        setCurrentDateProducer(dateProducer);
        return dateProducer;
    }

    public static Person getPerson() {
        Person person = fairy.person();
        setCurrentPerson(person);
        return person;
    }

    public static TextProducer getTextProducer() {
        TextProducer textProducer = fairy.textProducer();
        setCurrentTextProducer(textProducer);
        return textProducer;
    }
}

