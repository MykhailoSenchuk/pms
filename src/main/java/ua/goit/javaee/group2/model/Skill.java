package ua.goit.javaee.group2.model;

public class Skill extends NamedEntity {
    public Skill(String name) {
        super(name);
    }

    public Skill(Integer id, String name) {
        super(id, name);
    }
}
