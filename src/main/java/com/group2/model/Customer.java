package com.group2.model;

public class Customer extends NamedEntity {
    public Customer(String name) {
        super(name);
    }

    public Customer(Integer id, String name) {
        super(id, name);
    }
}