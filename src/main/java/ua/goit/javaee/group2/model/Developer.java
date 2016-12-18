package ua.goit.javaee.group2.model;

import java.util.Set;

public class Developer extends NamedEntity{

    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String COMPANY_ID = "company_id";
    public static final String SKILL_ID = "skill_id";

    private String lastName;

    private Company company;

    private Set<Skill> skills;

    public Developer(String name, String lastName, Company company, Set<Skill> skills) {
        super(name);
        this.lastName = lastName;
        this.company = company;
        this.skills = skills;
    }

    public Developer(Integer id, String name, String lastName, Company company, Set<Skill> skills) {
        this(name, lastName, company, skills);
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Developer() {

    }

    @Override
    public String toString() {
        return "Developer{" +
                "id='" + getId() + '\'' +
                " Name='" + getName() + '\'' +
                " lastName='" + lastName + '\'' +
                ", company=" + company.getName() +
                ", skills=" + skills +
                '}';
    }
}
