package com.group2.model;

import java.util.Set;

public class Developer extends NamedEntity{

    private String lastName;

    private Company company;

    private Set<Skill> skills;

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
                ", company=" + company +
                ", skills=" + skills +
                '}';
    }
}
