package ua.goit.javaee.group2.model;

import java.util.Set;

public class Company extends NamedEntity {

    public static String ID = "id";

    public static String NAME = "company_name";

    private Set<Developer> developers;

    public Company(String name) {
        super(name);
    }

    public Company(Integer id, String name) {
        super(id, name);
    }

    public void addDeveloper(Developer developer){
        this.developers.add(developer);
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }
}
