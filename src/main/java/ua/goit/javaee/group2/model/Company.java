package ua.goit.javaee.group2.model;

public class Company extends NamedEntity {
    public Company(String name) {
        super(name);
    }

    public Company(Integer id, String name) {
        super(id, name);
    }
}
