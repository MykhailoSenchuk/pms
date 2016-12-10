package ua.goit.javaee.group2.dao.jdbc;

import ua.goit.javaee.group2.dao.DeveloperDAO;
import ua.goit.javaee.group2.model.Developer;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class JdbcDeveloperDAOImpl implements DeveloperDAO {

    private static final String UPDATE_ROW = "UPDATE pms.developers SET first_name = ?, last_name = ?, company_id =? WHERE id =?";
    private static final String INSERT_ROW = "INSERT INTO pms.developers (first_name, last_name, company_id) VALUES (?,?,?)";

    private static final Logger LOG = getLogger(JdbcDeveloperDAOImpl.class);

    private DataSource dataSource;

    public JdbcDeveloperDAOImpl() {
        LOG.info("Successfully connected to database ");
    }

    @Override
    public Developer save(Developer developer) throws SQLException{
        if(!developer.isNew()){
            return update(developer);
        }
        else{
            return create(developer);
        }
    }


    private Developer update(Developer developer) throws SQLException{
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_ROW)) {

                ps.setString(1, developer.getName());
                ps.setString(2, developer.getLastName());
                ps.setInt(3,developer.getCompany().getId());
                ps.setInt(4,developer.getId());

                if(ps.executeUpdate() == 0){
                    throw new SQLException("Updating developer failed, no rows affected");
                }
                return developer;
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception occurred: ", e);
            throw e;
        }
    }


    private Developer create(Developer developer) throws SQLException{
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, developer.getName());
                ps.setString(2, developer.getLastName());
                ps.setInt(3,developer.getCompany().getId());

                if (ps.executeUpdate() == 0) {
                    throw new SQLException("Creating developer failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        developer.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating developer failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't save developer: " + e.getMessage(), e);
            throw e;
        }
        return developer;
    }

    @Override
    public boolean saveAll(List<Developer> list) throws SQLException {
        return false;
    }

    @Override
    public Developer load(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Developer> findAll() throws SQLException {
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


    public void setDataSource(DataSource dataSource)  {
        this.dataSource = dataSource;
    }
    private Connection getConnection()throws SQLException{
        return dataSource.getConnection();
    }
}
