package ua.goit.javaee.group2.controller;

import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.model.Company;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;
import java.util.List;

public class CompanyController extends AbstractController<Company>{

    private PlatformTransactionManager txManager;

    private CompanyDAO companyDAO;

    //if table already have company with same name, than just return entity form table, don't create new one
    @Transactional
    @Override
    public Company add(Company company){

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

    @Transactional
    @Override
    public Company get(int id)throws SQLException{
        return companyDAO.load(id);
    }

    @Transactional
    @Override
    public List<Company> getAll()throws SQLException{
        return companyDAO.findAll();
    }

    @Transactional
    @Override
    public void update(Company company){
        if(company == null){
            return;
        }
        companyDAO.update(company);
    }

    @Transactional
    @Override
    public void delete(int id){
        companyDAO.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll(){
        companyDAO.deleteAll();
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }
}
