package com.group2.dao;

import com.group2.model.Company;

import java.sql.SQLException;

public interface CompanyDAO extends AbstractDAO<Company> {
    Company load(String name);
    boolean update(Company company);
}
