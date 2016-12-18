package ua.goit.javaee.group2.model;

public class Customer extends NamedEntity {

    public static String ID = "id";

    public static String NAME = "customer_name";

    public Customer(String name) {
        super(name);
    }

    public Customer(Integer id, String name) {
        super(id, name);
    }
}
