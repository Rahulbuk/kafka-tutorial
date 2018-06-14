package com.wardziniak.kafka.streams.model;

import java.time.LocalTime;
import java.util.Random;

/**
 * Created by wardziniak on 10.06.2018.
 */
public class PersonFactory {

    private static Random r = new Random(LocalTime.now().toNanoOfDay());

    public static Person getPerson(Integer id) {
        return new Person(id, NAMES[Math.abs(r.nextInt())%NAMES.length], getAddress());
    }

    private static Address getAddress() {
        return new Address(
                STREETS[Math.abs(r.nextInt()) % STREETS.length],
                POSTAL_CODES[Math.abs(r.nextInt()) % POSTAL_CODES.length],
                CITIES[Math.abs(r.nextInt()) % CITIES.length]
        );
    }


    private static String[] NAMES = {"Michał", "Piotr", "Paweł", "Marcin", "Rafał"};
    private static String[] STREETS = {"Al. Jerozolimskie", "Marszałkowska", "Wołoska", "Al. Niepodległości"};
    private static String[] POSTAL_CODES = {"00-111", "00-222", "00-333", "11-222", "11-435"};
    private static String[] CITIES = {"WARSZAWA", "ŻUROMIN", "RADZYN PODLASKI", "CIECHANÓW"};
}
