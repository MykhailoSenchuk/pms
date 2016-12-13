package ua.goit.javaee.group2.model;

import java.util.Set;

public class Project extends NamedEntity {

    private String companyStr;
    private String customerStr;
    private String project_name;
    private long company_id;
    private long customer_id;

    private Company company;
    private Customer customer;
    private float cost;
    private Set<Developer> developers;

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

    public Project(Integer id, String name) {

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

    /*@Override
    public String toString() {
        return "Project{" +
                "ID=" + getId() +
                ", company=" + companyStr +
                ", customer=" + customerStr +
                ", project_name='" + project_name + '\'' +
                ", company_id=" + company_id +
                ", customer_id=" + customer_id +
                ", cost=" + cost +
                '}';
    }*/


    public String getCompanyStr() {
        return companyStr;
    }

    public void setCompanyStr(String companyStr) {
        this.companyStr = companyStr;
    }

    public String getCustomerStr() {
        return customerStr;
    }

    public void setCustomerStr(String customerStr) {
        this.customerStr = customerStr;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public void setProject_name(String project_name) {
        this.setName(project_name);
    }

    /*public void setProject_name(String project_name) {
        this.project_name = project_name;
    }*/

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
