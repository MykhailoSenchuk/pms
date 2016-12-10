package ua.goit.javaee.group2.controller;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.dao.DeveloperDAO;
import ua.goit.javaee.group2.model.Company;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Developer;

import java.util.List;

public class CompanyController extends AbstractController<Company>{

    private PlatformTransactionManager txManager;

    private CompanyDAO companyDAO;
    private DeveloperDAO developerDAO;

    public void addDeveloperToCompany(Developer developer, Company company) throws SQLException{

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
            return;
        }
        companyDAO.update(company);
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
