package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Company;

public interface CompanyDAO extends AbstractDAO<Company> {
    boolean update(Company company);
    Company load(String name);
}
