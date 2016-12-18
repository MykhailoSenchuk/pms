package ua.goit.javaee.group2.model;

public class Skill extends NamedEntity {

    public static String ID = "id";

    public static String NAME = "skill_name";

    public Skill() {
    }

    public Skill(String name) {
        super(name);
    }

    public Skill(Integer id, String name) {
        super(id, name);
    }
}
