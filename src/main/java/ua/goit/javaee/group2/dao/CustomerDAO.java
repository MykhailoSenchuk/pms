package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Customer;

public interface CustomerDAO extends AbstractDAO<Customer> {
    boolean update(Customer company);
    Customer load(String name);
}
