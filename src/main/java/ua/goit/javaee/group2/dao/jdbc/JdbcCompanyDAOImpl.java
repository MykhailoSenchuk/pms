package ua.goit.javaee.group2.dao.jdbc;


import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.model.Company;


public class JdbcCompanyDAOImpl implements CompanyDAO {

    private static final String INSERT_ROW = "INSERT INTO pms.companies VALUES (DEFAULT,?)";
    private static final String DELETE_ROW = "DELETE FROM pms.companies WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM jpms.companies";
    private static final String UPDATE_ROW = "UPDATE pms.companies SET company_name = ? WHERE id =?";
    private static final String GET_BY_ID = "SELECT * FROM pms.companies WHERE id =?";
    private static final String GET_BY_NAME = "SELECT * FROM pms.companies WHERE company_name =?";
    private static final String GET_ALL = "SELECT * FROM pms.companies";

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private DataSource dataSource;

    @Override
    public Company save(Company object){
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW)) {
                ps.setString(1, object.getName());
                ps.execute();
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
        return object;
    }

    /**
     * Saves list of companies to DB. Commit only if all objects will be saved.
     *
     * @param list of Company objects
     * @return status of operation
     * @throws RuntimeException on SQLException and the Logger message
     */
    @Override
    public boolean saveAll(List<Company> list) {
        try (Connection connection = getConnection()) {
            //insert each object into BD
            for (Company object : list) {
                try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW)) {
                    ps.setString(1, object.getName());
                    //break method if the object is  not saved, provided that no commit will be made
                    if (ps.executeUpdate() == 0) {
                        return false;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes companies row by id
     *
     * @param id
     * @return true if a row was delete, false if no row was deleted
     * @throws RuntimeException on SQLException and the Logger message
     */

    @Override
    public boolean deleteById(int id) {
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

        return removed;
    }

    /**
     * Delete all rows from companies table
     *
     * @return true if no exception was thrown
     * @throws RuntimeException on SQLException and the Logger message
     */
    @Override
    public boolean deleteAll() {
        try (Connection connection = getConnection()) {
            try (Statement st = connection.createStatement()) {
                st.executeQuery(DELETE_ALL);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Get company object by id
     *
     * @param id
     * @return company object, null if entry wasn't found
     * @throws RuntimeException on SQLException and the Logger message
     */
    @Override
    public Company load(int id) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
                ps.setInt(1, id);

                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    return new Company(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Get company object by name
     *
     * @param name
     * @return company object, null if entry wasn't found
     * @throws SQLException
     */
    @Override
    public Company load(String name) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_BY_NAME)) {
                ps.setString(1, name);

                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }

                    return new Company(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> findAll() {

        try (Connection connection = getConnection()) {
            try (Statement st = connection.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(GET_ALL)) {

                    List<Company> companies = new ArrayList<>();

                    while (resultSet.next()) {
                        companies.add(new Company(resultSet.getInt(1), resultSet.getString(2)));
                    }

                    return companies;

                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Company company){
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_ROW)) {
                ps.setString(1, company.getName());
                ps.setInt(2, company.getId());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}