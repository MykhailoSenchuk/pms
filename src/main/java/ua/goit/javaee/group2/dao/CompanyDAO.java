package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Company;

import java.sql.SQLException;

public interface CompanyDAO extends AbstractDAO<Company> {
    Company update(Company company) throws SQLException;
    Company load(String name)throws SQLException;
}
