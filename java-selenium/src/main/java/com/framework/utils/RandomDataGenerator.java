package com.framework.utils;
import com.github.javafaker.Faker;

import java.util.UUID;
import java.util.Random;

public class RandomDataGenerator {
    private static final Random random = new Random();

    public static String generateRandomEmail(){
        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,8);
        return ("user"+uuid+'@'+"yopmail.com");

    }
    public static String generateRandomPhoneNumber(){
        int areaCode = random.nextInt(800) + 200;   // 200–999
        int prefix = random.nextInt(743) + 200;      // 200–942
        int lineNumber = random.nextInt(10000);      // 0000–9999

        return String.format("%03d%03d%04d", areaCode, prefix, lineNumber);
    }

    public static String generateRandomName(){
        String name = random.toString();
        return name;
    }

    private static final Faker faker = new Faker();

    // Full name
    public static String getFullName() {
        return faker.name().fullName();
    }

    // First name
    public static String getFirstName() {
        return faker.name().firstName();
    }

    // Last name
    public static String getLastName() {
        return faker.name().lastName();
    }

    // Email
    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    // Phone number (US format)
    public static String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    // Address (optional but useful in QA)
    public static String getAddress() {
        return faker.address().streetAddress();
    }

    public static String getCity() {
        return faker.address().city();
    }

    public static String getZipcode(){
        return faker.address().zipCode();
    }
    
}

