package com.group2.dao.jdbc;

import com.group2.dao.SkillDAO;
import com.group2.model.Skill;

import javax.sql.DataSource;
import java.util.List;

public class JdbcSkillDAOImpl implements SkillDAO {

    private DataSource dataSource;

    @Override
    public Skill save(Skill object) {
        return null;
    }

    @Override
    public boolean saveAll(List<Skill> list) {
        return false;
    }

    @Override
    public Skill load(int id) {
        return null;
    }

    @Override
    public List<Skill> findAll() {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
