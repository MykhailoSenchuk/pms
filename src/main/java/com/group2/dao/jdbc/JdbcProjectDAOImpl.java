package com.group2.dao.jdbc;

import com.group2.dao.ProjectDAO;
import com.group2.model.Project;

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
    public boolean deleteAll() throws SQLException {
        return false;
    }

    @Override
    public boolean saveAll(List<Project> list) throws SQLException {
        return false;
    }

    @Override
    public List<Project> findAll() throws SQLException {
        return null;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return false;
    }

    @Override
    public Project load(int id) throws SQLException {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
