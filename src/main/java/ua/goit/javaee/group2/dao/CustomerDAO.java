package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends AbstractDAO<Customer> {
    boolean update(Customer company) throws SQLException;
    Customer load(String name)throws SQLException;
}
