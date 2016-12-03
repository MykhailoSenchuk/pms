package com.group2.dao.jdbc;

import com.group2.dao.ProjectDAO;
import com.group2.model.Project;

import javax.sql.DataSource;
import java.util.List;

public class JdbcProjectDAOImpl implements ProjectDAO {

    private DataSource dataSource;

    @Override
    public Project save(Project object) {
        return null;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public boolean saveAll(List<Project> list) {
        return false;
    }

    @Override
    public List<Project> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public Project load(int id) {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
