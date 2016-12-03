package com.group2.dao.jdbc;

import com.group2.dao.CustomerDAO;
import com.group2.model.Customer;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCustomerDAOImpl implements CustomerDAO {

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
