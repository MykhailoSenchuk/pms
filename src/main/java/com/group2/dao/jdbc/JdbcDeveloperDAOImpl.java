package com.group2.dao.jdbc;

import com.group2.dao.DeveloperDAO;
import com.group2.model.Developer;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class JdbcDeveloperDAOImpl implements DeveloperDAO {
    private static final Logger LOG = getLogger(JdbcDeveloperDAOImpl.class);

    private DataSource dataSource;

    public JdbcDeveloperDAOImpl() {
        LOG.info("Successfully connected to database ");
    }

    @Override
    public Developer save(Developer object) {
        return null;
    }

    @Override
    public boolean saveAll(List<Developer> list) {
        return false;
    }

    @Override
    public Developer load(int id) {
        return null;
    }

    @Override
    public List<Developer> findAll() {
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
