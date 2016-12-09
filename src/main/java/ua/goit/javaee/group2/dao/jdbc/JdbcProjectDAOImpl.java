package ua.goit.javaee.group2.dao.jdbc;

import ua.goit.javaee.group2.dao.ProjectDAO;
import ua.goit.javaee.group2.model.Project;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class JdbcProjectDAOImpl implements ProjectDAO {

    private DataSource dataSource;

    @Override
    public Project save(Project object) throws SQLException {
        return null;
    }

    @Override
    public void deleteAll() throws SQLException {
    }

    @Override
    public void saveAll(List<Project> list) throws SQLException {
    }

    @Override
    public List<Project> findAll() throws SQLException {
        return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {

    }

    @Override
    public Project load(int id) throws SQLException {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
