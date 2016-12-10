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
    private static final Logger LOG = getLogger(JdbcDeveloperDAOImpl.class);

    private DataSource dataSource;

    private CompanyDAO companyDAO;

    private SkillDAO skillDAO;

    public JdbcDeveloperDAOImpl() {
        LOG.info("Successfully connected to database ");
    }

    @Override
    public Developer save(Developer developer) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement;
            String sql;
            if (developer.isNew()) {
                sql = "INSERT INTO pms.developers (first_name, last_name, company_id) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                sql = "UPDATE pms.developers SET first_name = ?, last_name = ?, company_id = ? WHERE id = ?";
                removeSkillsFromDeveloper(developer, connection);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(4, developer.getCompany().getId());
            }
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setInt(3, developer.getCompany().getId());

            preparedStatement.executeUpdate();

            if (developer.isNew()) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
//                if (resultSet.next()) {
                resultSet.next();
                developer.setId(resultSet.getInt(1));
//                } else {
//                    LOG.error("Some trouble happened while getting returned id.");
//                }
            }

            addSkills(developer, connection);
            LOG.info("Developer " + developer + " successfully added to database.");
            return developer;
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Developer> list) {
        for (Developer developer : list) {
            save(developer);
        }
    }

    @Override
    public Developer load(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM pms.developers WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Developer developer;
            if (resultSet.next()) {
                developer = createDeveloper(resultSet);
                retrieveDeveloperSkills(connection, developer);
                LOG.info("Developer " + developer + " successfully added to database.");
                return developer;
            } else {
                LOG.info("Developer was not found.");
                return null;
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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pms.developers");
            while (resultSet.next()) {
                Developer developer = createDeveloper(resultSet);
                retrieveDeveloperSkills(connection, developer);
                developers.add(developer);
            }
            return developers;
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pms.developers WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            LOG.info("Developer was successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM pms.developers");
            LOG.info("All developers were successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private Developer createDeveloper(ResultSet resultSet) throws SQLException {
        Developer developer = new Developer();
        developer.setId(resultSet.getInt("id"));
        developer.setName(resultSet.getString("first_name"));
        developer.setLastName(resultSet.getString("last_name"));
        developer.setCompany(companyDAO.load(resultSet.getInt("company_id")));
        return developer;
    }

    private void removeSkillsFromDeveloper(Developer developer, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pms.developers_skills WHERE developer_id=?");
        preparedStatement.setInt(1, developer.getId());
        preparedStatement.executeQuery();
    }

    private void addSkills(Developer developer, Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
        for (Skill skill : developer.getSkills()) {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO pms.developers_skills(developer_id, skill_id) VALUES (?,?)");
            preparedStatement.setInt(1, developer.getId());
            preparedStatement.setInt(2, skill.getId());
            preparedStatement.execute();
            LOG.info("Adding skills successfull");
        }
    }

    private void retrieveDeveloperSkills(Connection connection, Developer developer) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        preparedStatement = connection.prepareStatement(
                "SELECT skill_id FROM pms.developers_skills WHERE developer_id=?");
        preparedStatement.setInt(1, developer.getId());
        resultSet = preparedStatement.executeQuery();
        Set<Skill> skills = new HashSet<>();
        while (resultSet.next()) {
            skills.add(skillDAO.load(resultSet.getInt("skill_id")));
        }
        developer.setSkills(skills);
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSkillDAO(SkillDAO skillDAO) {
        this.skillDAO = skillDAO;
    }
}
