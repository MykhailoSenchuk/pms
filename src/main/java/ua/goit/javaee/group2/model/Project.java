package ua.goit.javaee.group2.model;

import java.util.Set;

public class Project extends NamedEntity {

    Company company;

    Customer customer;

    Set<Developer> developers;

    public Project(String name, Company company, Customer customer, Set<Developer> developers) {
        super(name);
        this.company = company;
        this.customer = customer;
        this.developers = developers;
    }

    public Project(Integer id, String name, Company company, Customer customer, Set<Developer> developers) {
        this(name, company, customer, developers);
        this.id = id;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
