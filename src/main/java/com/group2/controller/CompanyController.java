package com.group2.controller;

import com.group2.dao.CompanyDAO;
import com.group2.model.Company;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class CompanyController extends AbstractController<Company>{

    private PlatformTransactionManager txManager;

    private CompanyDAO companyDAO;

    //if table already have company with same name, than just return entity form table, don't create new one
    @Override
    public Company add(Company company){return null;}

    @Override
    public Company get(int id){return null;}

    @Override
    public List<Company> getAll(){return null;}

    @Override
    public void update(Company company){}

    @Override
    public void delete(int id){}

    @Override
    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }
}
