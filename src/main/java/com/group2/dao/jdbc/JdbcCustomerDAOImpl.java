package com.group2.dao.jdbc;

import com.group2.dao.CustomerDAO;
import com.group2.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcCustomerDAOImpl implements CustomerDAO {

    private static final String INSERT_ROW = "INSERT INTO pms.customers VALUES (DEFAULT,?)";
    private static final String DELETE_ROW = "DELETE FROM pms.customers WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM pms.customers";
    private static final String UPDATE_ROW = "UPDATE pms.customers SET customer_name = ? WHERE id =?";
    private static final String GET_BY_ID = "SELECT * FROM pms.customers WHERE id =?";
    private static final String GET_BY_NAME = "SELECT * FROM pms.customers WHERE customer_name =?";
    private static final String GET_ALL = "SELECT * FROM pms.customers";

    private DataSource dataSource;

    @Override
    public Customer save(Customer object) {
        return null;
    }

    @Override
    public boolean saveAll(List<Customer> list) {
        return false;
    }

    @Override
    public Customer load(int id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
