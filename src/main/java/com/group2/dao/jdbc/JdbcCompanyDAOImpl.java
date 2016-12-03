package com.group2.dao.jdbc;

import com.group2.dao.CompanyDAO;
import com.group2.model.Company;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCompanyDAOImpl implements CompanyDAO {

    private DataSource dataSource;

    @Override
    public Company save(Company object) {
        return null;
    }

    @Override
    public boolean saveAll(List<Company> list) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public Company load(int id) {
        return null;
    }

    @Override
    public List<Company> findAll() {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
