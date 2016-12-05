package com.group2.controller;

import com.group2.dao.CompanyDAO;
import com.group2.model.Company;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;
import java.util.List;

public class CompanyController {

    private PlatformTransactionManager txManager;

    private CompanyDAO companyDAO;

    //if table already have company with same name, than just return entity form table, don't create new one
    public Company add(Company company) throws SQLException {

        if(company == null)
            return null;

        //search company by name
        Company byName = companyDAO.load(company.getName());

        //if wasn't found by name add row to db
        if ( byName == null ){
            companyDAO.save(company);
            return company;
        }
        else
            return byName;
    }

    public Company get(int id)throws SQLException{
        return companyDAO.load(id);
    }

    public List<Company> getAll()throws SQLException{
        return companyDAO.findAll();
    }

    public void update(Company company)throws SQLException{
        if(company == null){
            return;
        }
        companyDAO.update(company);
    }

    public void delete(int id)throws SQLException{
        companyDAO.deleteById(id);
    }

    public void deleteAll()throws SQLException{
        companyDAO.deleteAll();
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }
}
