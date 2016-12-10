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
    @Override
    public Company add(Company company){return null;}

    @Override
    public Company get(int id){return new Company(1, "Ciklum");}

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

    public void setDeveloperDAO(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }
}
