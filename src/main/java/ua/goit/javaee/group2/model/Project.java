package ua.goit.javaee.group2.model;

import java.util.Set;

public class Project extends NamedEntity {

    private Company company;
    private Customer customer;
    private float cost;
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

    public Project() {
    }


    @Override
    public String toString() {
        return "Project{" +
                "ID=" + getId() +
                ", company=" + company.getName() +
                ", customer=" + customer.getName() +
                ", project_name='" + getName() + '\'' +
                ", company_id=" + company.getId() +
                ", customer_id=" + customer.getId() +
                ", cost=" + cost +
                '}';
    }



    public void setProject_name(String project_name) {
        this.setName(project_name);
    }

    public long getCompany_id() {
        return this.company.getId();
    }

    public long getCustomer_id() {
        return this.company.getId();
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
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
