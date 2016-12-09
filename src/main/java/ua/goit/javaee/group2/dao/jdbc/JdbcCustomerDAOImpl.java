package ua.goit.javaee.group2.dao.jdbc;

import ua.goit.javaee.group2.dao.CustomerDAO;
import ua.goit.javaee.group2.model.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class JdbcCustomerDAOImpl implements CustomerDAO {

    private DataSource dataSource;

    @Override
    public Customer save(Customer object) throws SQLException {
        return null;
    }

    @Override
    public boolean saveAll(List<Customer> list) throws SQLException {
        return false;
    }

    @Override
    public Customer load(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Customer> findAll() throws SQLException {
        return null;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteAll() throws SQLException {
        return false;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean update(Customer company) throws SQLException {
        return false;
    }

    @Override
    public Customer load(String name) throws SQLException {
        return null;
    }
}
