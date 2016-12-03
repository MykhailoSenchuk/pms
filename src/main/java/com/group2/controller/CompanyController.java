package com.group2.controller;

import com.group2.dao.CompanyDAO;
import com.group2.model.Company;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class CompanyController {

    private PlatformTransactionManager txManager;

    private CompanyDAO companyDAO;

    public void add(Company company){}

    public Company get(int id){return null;}

    public List<Company> getAll(){return null;}

    public void update(Company company){}

    public void delete(int id){}

    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }
}
