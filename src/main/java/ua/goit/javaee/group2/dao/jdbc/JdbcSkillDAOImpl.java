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
    public Skill save(Skill skill) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement;
            String sql;
            if (skill.isNew()) {
                sql = "INSERT INTO pms.skills (skill_name) VALUES (?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                sql = "UPDATE pms.skills SET skill_name = ? WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(2, skill.getId());
            }
            preparedStatement.setString(1, skill.getName());
            preparedStatement.executeUpdate();

            if (skill.isNew()) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                skill.setId(resultSet.getInt(1));
            }
            LOG.info("Success. New skill name = " + skill + '.');
            return skill;
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Skill> skills) {
        skills.forEach(this::save);
    }

    @Override
    public Skill load(int id) {
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
    public List<Skill> findAll() {
        List<Skill> skills = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pms.skills");
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
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pms.skills WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            LOG.info("Skill was successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
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
        skill.setName(resultSet.getString("skill_name"));
        return skill;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
