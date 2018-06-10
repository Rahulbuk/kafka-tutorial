package com.wardziniak.kafka.model;

/**
 * Created by wardziniak on 10.06.18.
 */
public class Person {

    private String name;

    private int id;

    private Address address;

    public Person(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
