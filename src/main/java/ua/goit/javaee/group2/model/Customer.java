package ua.goit.javaee.group2.model;

public class Customer extends NamedEntity {

    public static final String NAME = "customer_name";

    public Customer(String name) {
        super(name);
    }

    public Customer(Integer id, String name) {
        super(id, name);
    }
}
