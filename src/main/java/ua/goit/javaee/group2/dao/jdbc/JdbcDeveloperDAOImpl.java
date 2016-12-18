package ua.goit.javaee.group2.dao.jdbc;

import org.slf4j.Logger;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.dao.DeveloperDAO;
import ua.goit.javaee.group2.dao.SkillDAO;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Skill;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class JdbcDeveloperDAOImpl implements DeveloperDAO {

    private static final String UPDATE_ROW
            = "UPDATE pms.developers SET first_name = ?, last_name = ?, company_id =? WHERE id =?";
    private static final String INSERT_ROW
            = "INSERT INTO pms.developers (first_name, last_name, company_id) VALUES (?,?,?)";

    private static final Logger LOG = getLogger(JdbcDeveloperDAOImpl.class);

    private DataSource dataSource;

    private CompanyDAO companyDAO;

    private SkillDAO skillDAO;

    public JdbcDeveloperDAOImpl() {
        LOG.info("Successfully connected to database ");
    }

    @Override
    public Developer save(Developer developer) {
        if (!developer.isNew()) {
            update(developer);
            removeSkillsOf(developer);
        } else {
            create(developer);
        }
        addSkillsTo(developer);
        return developer;
    }

    private Developer update(Developer developer) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(UPDATE_ROW)) {

                ps.setString(1, developer.getName());
                ps.setString(2, developer.getLastName());
                if(developer.getCompany() != null)
                    ps.setInt(3, developer.getCompany().getId());
                else
                    ps.setNull(3,Types.INTEGER);
                ps.setInt(4, developer.getId());

                if (ps.executeUpdate() == 0) {
                    throw new SQLException("Updating developer failed, no rows affected");
                }
                return developer;
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    private Developer create(Developer developer) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_ROW, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, developer.getName());
                ps.setString(2, developer.getLastName());
                ps.setInt(3, developer.getCompany().getId());

                if (ps.executeUpdate() == 0) {
                    throw new SQLException("Creating developer failed, no rows affected.");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        developer.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating developer failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't save developer: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return developer;
    }

    @Override
    public void saveAll(List<Developer> list) {
        for (Developer developer : list) {
            save(developer);
        }
    }

    @Override
    public Developer load(int id) {
        try ( Connection connection = dataSource.getConnection() ) {
            try( PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM pms.developers WHERE id=?") ) {
                preparedStatement.setInt(1, id);
                try( ResultSet resultSet = preparedStatement.executeQuery() ) {
                    Developer developer;
                    if (resultSet.next()) {
                        developer = createDeveloper(resultSet);
                        retrieveSkillsOf(developer);
                        LOG.info("Developer " + developer + " successfully added to database.");
                        return developer;
                    } else {
                        LOG.info("Developer was not found.");
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> developers = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try( Statement statement = connection.createStatement()){
                try(ResultSet resultSet = statement.executeQuery("SELECT * FROM pms.developers") ){
                    while (resultSet.next()) {
                        Developer developer = createDeveloper(resultSet);
                        retrieveSkillsOf(developer);
                        developers.add(developer);
                    }
                    return developers;
                }
            }

        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM pms.developers WHERE id=?")){
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                LOG.info("Developer was successfully deleted.");
            }
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dataSource.getConnection()) {
            try(Statement statement = connection.createStatement()){
                statement.execute("DELETE FROM pms.developers");
                LOG.info("All developers were successfully deleted.");
            }
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private Developer createDeveloper(ResultSet resultSet) throws SQLException {
        Developer developer = new Developer();
        developer.setId(resultSet.getInt(Developer.ID));
        developer.setName(resultSet.getString(Developer.FIRST_NAME));
        developer.setLastName(resultSet.getString(Developer.LAST_NAME));
        developer.setCompany(companyDAO.load(resultSet.getInt(Developer.COMPANY_ID)));
        return developer;
    }

    private void removeSkillsOf(Developer developer) {
        try {
            PreparedStatement preparedStatement = getConnection()
                    .prepareStatement("DELETE FROM pms.developers_skills WHERE developer_id=?");
            preparedStatement.setInt(1, developer.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private void addSkillsTo(Developer developer){
        try (Connection connection = getConnection() ){
            try( PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO pms.developers_skills(developer_id, skill_id) VALUES (?,?)") ){
                for (Skill skill : developer.getSkills()) {
                    preparedStatement.setInt(1, developer.getId());
                    preparedStatement.setInt(2, skill.getId());
                    preparedStatement.execute();
                    LOG.info("Adding skills successful");
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private void retrieveSkillsOf(Developer developer){

        try (Connection connection = getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT skill_id FROM pms.developers_skills WHERE developer_id=?")){
                preparedStatement.setInt(1, developer.getId());
                try( ResultSet resultSet = preparedStatement.executeQuery()){
                    Set<Skill> skills = new HashSet<>();
                    while (resultSet.next()) {
                        skills.add(skillDAO.load(resultSet.getInt(Developer.SKILL_ID)));
                    }
                    developer.setSkills(skills);
                }
            }
        }
        catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void setSkillDAO(SkillDAO skillDAO) {
        this.skillDAO = skillDAO;
    }
}
