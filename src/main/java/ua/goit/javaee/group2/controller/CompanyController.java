package ua.goit.javaee.group2.controller;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.dao.DeveloperDAO;
import ua.goit.javaee.group2.model.Company;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Developer;

import java.sql.SQLException;
import java.util.List;

public class CompanyController extends AbstractController<Company>{

    private PlatformTransactionManager txManager;

    private CompanyDAO companyDAO;
    private DeveloperDAO developerDAO;

    public void addDeveloperToCompany(Developer developer, Company company) throws SQLException {

        if( company == null ){
            // TODO make logger
            System.out.println("Company wasn't provided");
            return;
        }
        if (company.isNew()){

            System.out.println("company isn't registered in DB");
            return;
        }

        if(developer.isNew()){
            System.out.println("developer isn't registered in DB");
        }

        developer.setCompany(company);
        developerDAO.save(developer);
        company.addDeveloper(developer);
    }
    //if table already have company with same name, than just return entity form table, don't create new one
    @Transactional
    @Override
    public Company add(Company company) throws SQLException{

        if(company == null || company.getName() == null || "".equals(company.getName()) ) {
            System.out.println("company wasn't provided");
            return null;
        }
        //search company by name
        //TODO make name unique in DB
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
    public boolean checkById (int id) throws SQLException{
        return get(id) != null;
    }

    @Transactional
    @Override
    public List<Company> getAll()throws SQLException{
        return companyDAO.findAll();
    }

    @Transactional
    @Override
    public void update(Company company) throws SQLException{
        if(company == null){
            System.out.println("no object was provided");
            return;
        }
        companyDAO.save(company);
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException{
        companyDAO.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() throws SQLException{
        companyDAO.deleteAll();
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void setDeveloperDAO(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }
}
