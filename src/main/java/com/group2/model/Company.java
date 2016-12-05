package com.group2.model;

public class Company extends NamedEntity {
    public Company(String name) {
        super(name);
    }

    public Company(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "" +
                "id ='" + getId() + '\'' +
                ", Company Name ='" + getName() + '\'';
    }
}
