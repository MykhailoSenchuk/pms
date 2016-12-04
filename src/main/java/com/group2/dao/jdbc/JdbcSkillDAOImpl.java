package com.group2.dao.jdbc;

import com.group2.dao.SkillDAO;
import com.group2.model.Skill;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class JdbcSkillDAOImpl implements SkillDAO {

    private DataSource dataSource;

    @Override
    public Skill save(Skill object) throws SQLException {
        return null;
    }

    @Override
    public boolean saveAll(List<Skill> list) throws SQLException {
        return false;
    }

    @Override
    public Skill load(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Skill> findAll() throws SQLException {
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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
