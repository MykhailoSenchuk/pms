package ua.goit.javaee.group2.model;


public class NamedEntity {

    public static final String ID = "id";

    protected Integer id;

    protected String name;

    protected NamedEntity() {
    }

    protected NamedEntity(String name) {
        this.name = name;
    }

    protected NamedEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "id = '" + getId() + '\'' +
                ", name = '" + name + '\'';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public boolean isNew() {
        return (getId() == null);
    }
}
