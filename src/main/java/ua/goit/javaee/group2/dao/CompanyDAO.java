package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Company;

import java.sql.SQLException;

public interface CompanyDAO extends AbstractDAO<Company> {
    Company load(String name);
}
