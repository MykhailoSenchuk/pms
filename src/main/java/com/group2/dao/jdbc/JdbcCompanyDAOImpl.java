package com.group2.dao.jdbc;

import com.group2.dao.CompanyDAO;
import com.group2.model.Company;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCompanyDAOImpl implements CompanyDAO {
    private static final String INSERT_ROW = "INSERT INTO pms.companies VALUES (6,?)";
    private static final String DELETE_ROW = "DELETE FROM pms.companies WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM jpms.companies";
    private static final String UPDATE_ROW = "UPDATE pms.companies SET company_name = ? WHERE id =?";
    private static final String GET_BY_ID = "SELECT * FROM pms.companies WHERE id =?";
    private static final String GET_BY_NAME = "SELECT * FROM pms.companies WHERE company_name =?";
    private static final String GET_ALL = "SELECT * FROM pms.companies";

    private DataSource dataSource;

    @Override
    public Company save(Company object) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW)) {
                ps.setString( 1, object.getName() );
                ps.execute();
            }
        }
        return object;
    }

    /**
     * Saves list of companies to DB. Commit only if all objects will be saved.
     *
     * @param list of Company objects
     * @return status of operation
     * @throws SQLException
     */
    @Override
    public boolean saveAll(List<Company> list) throws SQLException {
        try (Connection connection = getConnection()) {
            try{
                // disable autocommit
                connection.setAutoCommit(false);

                for (Company object : list) {
                    try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW)) {

                        ps.setString( 1, object.getName() );

                        //break method if the object is  not saved, provided that no commit will be made
                        if(ps.executeUpdate() == 0){
                            return false;
                        }
                    }
                }

                //make commit
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException(e.getMessage(), e);
            }

        }
        return true;
    }

     /**
     * Deletes companies row by id
     * TODO delete cascade
     *
     * @param id
     * @return true if a row was delete, false if no row was deleted
     * @throws SQLException
     */
    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean removed = false;

        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_ROW)) {
                ps.setLong(1, id);
                removed = ps.executeUpdate() > 0;
            }
        }
        return removed;
    }

    /**
     * Delete all rows from companies table
     * TODO delete cascade
     *
     * @return true if no exception was thrown
     * @throws SQLException
     */
    @Override
    public boolean deleteAll() throws SQLException {
        try( Connection connection = getConnection() ){
            try( Statement st = connection.createStatement()){
                st.executeQuery(DELETE_ALL);
            }
        }
        return true;
    }

    /**
     * Get company object by id
     *
     * @param id
     * @return company object, null if entry wasn't found
     * @throws SQLException
     */
    @Override
    public Company load(int id) throws SQLException {
        try( Connection connection = getConnection() ){
            try( PreparedStatement ps = connection.prepareStatement(GET_BY_ID)){
                ps.setInt(1, id);

                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    return new Company( resultSet.getInt(1), resultSet.getString(2) );
                }
            }
        }
    }
    /**
     * Get company object by name
     *
     * @param name
     * @return company object, null if entry wasn't found
     * @throws SQLException
     */
    public Company load(String name) throws SQLException{
        try( Connection connection = getConnection() ){
            try( PreparedStatement ps = connection.prepareStatement(GET_BY_NAME)){
                ps.setString(1, name);

                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }

                    return new Company( resultSet.getInt(1), resultSet.getString(2) );
                }
            }
        }
    }

    @Override
    public List<Company> findAll() throws SQLException {

        try( Connection connection = getConnection() ){
            try( Statement st = connection.createStatement()){
                try (ResultSet resultSet = st.executeQuery(GET_ALL)) {

                    List<Company> companies = new ArrayList<>();

                    while(resultSet.next()){
                        companies.add(new Company(resultSet.getInt(1),resultSet.getString(2) ) );
                    }

                    return companies;

                }
            }
        }
    }

    @Override
    public boolean update(Company company) throws SQLException {
        try( Connection connection = getConnection()){
            try( PreparedStatement ps = connection.prepareStatement(UPDATE_ROW)){
                ps.setString(1, company.getName());
                ps.setInt(2, company.getId());
                return ps.executeUpdate() > 0 ;
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}
