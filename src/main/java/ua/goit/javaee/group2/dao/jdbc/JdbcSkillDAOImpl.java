package ua.goit.javaee.group2.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.javaee.group2.dao.SkillDAO;
import ua.goit.javaee.group2.model.Skill;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillDAOImpl implements SkillDAO {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcSkillDAOImpl.class);

    private DataSource dataSource;

    @Override
    public Skill save(Skill skill) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement;
            if (skill.isNew()) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO pms.skills (name) VALUES ?");
            } else {
                preparedStatement = connection.prepareStatement(
                        "UPDATE pms.skills SET name = ? WHERE id = ?");
                preparedStatement.setInt(1, skill.getId());
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (skill.isNew()) {
                skill.setId(resultSet.getInt("id"));
            }
            LOG.info("Skill " + skill + " was successfully added to database.");
            return skill;
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Skill> skills) throws SQLException {
        for (Skill skill : skills) {
            save(skill);
        }
    }

    @Override
    public Skill load(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM pms.skills WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Skill skill;
            if (resultSet.next()) {
                skill = createSkill(resultSet);
                LOG.info("Skill " + skill + " was successfully added to database.");
                return skill;
            } else {
                LOG.info("Skill was not found.");
                return null;
            }
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Skill> findAll() throws SQLException {
        List<Skill> skills = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pms.developers");
            while (resultSet.next()) {
                skills.add(createSkill(resultSet));
            }
            return skills;
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pms.skills WHERE id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            LOG.info("Skill was successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM pms.skills");
            LOG.info("All skills were successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private Skill createSkill(ResultSet resultSet) throws SQLException {
        Skill skill = new Skill();
        skill.setId(resultSet.getInt("id"));
        skill.setName(resultSet.getString("first_name"));
        return skill;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
