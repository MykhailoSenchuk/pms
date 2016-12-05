package com.group2.dao;

import com.group2.model.Company;

import java.sql.SQLException;

public interface CompanyDAO extends AbstractDAO<Company> {
    public Company load(String name) throws SQLException;
    public boolean update(Company company) throws SQLException;
}
