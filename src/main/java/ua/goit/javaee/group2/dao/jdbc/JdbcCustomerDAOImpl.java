package ua.goit.javaee.group2.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.javaee.group2.dao.CustomerDAO;
import ua.goit.javaee.group2.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCustomerDAOImpl implements CustomerDAO {

    private static final String INSERT_ROW = "INSERT INTO pms.customers (customer_name) VALUES (?)";
    private static final String DELETE_ROW = "DELETE FROM pms.customers WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM pms.customers";
    private static final String UPDATE_ROW = "UPDATE pms.customers SET customer_name = ? WHERE id =?";
    private static final String GET_BY_ID = "SELECT * FROM pms.customers WHERE id =?";
    private static final String GET_BY_NAME = "SELECT * FROM pms.customers WHERE customer_name =?";
    private static final String GET_ALL = "SELECT * FROM pms.customers";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDAO.class);

    private DataSource dataSource;


    @Override
    public Customer save(Customer customer) {
        if(!customer.isNew()){
            return update(customer);
        }
        else{
            return create(customer);
        }
    }

    @Override
    public Customer update(Customer customer) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_ROW)) {

                ps.setString(1, customer.getName());
                ps.setInt(2, customer.getId());

                if(ps.executeUpdate() == 0){
                    throw new SQLException("Creating customer failed, no rows affected");
                }
                return customer;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }


    private Customer create(Customer customer) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW,Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, customer.getName());
                if (ps.executeUpdate() == 0) {
                    throw new SQLException("Creating customer failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating customer failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save customer: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public void saveAll(List<Customer> list) {
        try (Connection connection = getConnection()) {
            //insert each customer into BD
            for (Customer customer : list) {
                try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW)) {
                    ps.setString(1, customer.getName());
                    //break method if the customer is  not saved, provided that no commit will be made
                    if (ps.executeUpdate() == 0) {
                        return;
                    }
                }
            }
            return;
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer load(int id) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
                ps.setInt(1, id);

                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    return new Customer(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        try (Connection connection = getConnection()) {
            try (Statement st = connection.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(GET_ALL)) {

                    List<Customer> customers = new ArrayList<>();

                    while (resultSet.next()) {
                        customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2)));
                    }

                    return customers;

                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        boolean removed = false;

        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_ROW)) {
                ps.setLong(1, id);
                removed = ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteAll() {
        try (Connection connection = getConnection()) {
            try (Statement st = connection.createStatement()) {
                st.executeQuery(DELETE_ALL);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }



    @Override
    public Customer load(String name) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_BY_NAME)) {
                ps.setString(1, name);

                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }

                    return new Customer(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw e;
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
