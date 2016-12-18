package ua.goit.javaee.group2.controller;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.dao.DeveloperDAO;
import ua.goit.javaee.group2.model.Company;
import ua.goit.javaee.group2.model.Developer;

import java.sql.SQLException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class CompanyController extends AbstractController<Company> {

    private static final Logger LOG = getLogger(CompanyController.class);

    private CompanyDAO companyDAO;
    private DeveloperDAO developerDAO;

    @Transactional
    public void addDeveloperToCompany(Developer developer, Company company) throws SQLException {
        if (isNullThanPrintAndLogErrorMessageFor(company)) return;

        if (company.isNew()) {
            String message = "Company isn't registered in database yet.";
            LOG.error(message);
            System.out.println(message);
            return;
        }

        if (developer.isNew()) {
            String message = "Developer isn't registered in DB yet";
            System.out.println(message);
            LOG.error(message);
        }

        developer.setCompany(company);
        developerDAO.save(developer);
        company.addDeveloper(developer);
    }

    //if table already have company with same name, than just return entity form table, don't create new one
    @Transactional
    @Override
    public Company add(Company company) throws SQLException {
        if (company == null || company.getName() == null || "".equals(company.getName())) {
            String message = "Company wasn't provided";
            System.out.println(message);
            LOG.error(message);
            return null;
        }

        //search company by name
        Company byName = companyDAO.load(company.getName());

        //if wasn't found by name add row to db
        if (byName == null) {
            companyDAO.save(company);
            return company;
        } else
            return byName;
    }

    @Transactional
    @Override
    public Company get(int id) throws SQLException {
        return companyDAO.load(id);
    }

    @Transactional
    @Override
    public List<Company> getAll() throws SQLException {
        return companyDAO.findAll();
    }

    @Transactional
    @Override
    public void update(Company company) throws SQLException {
        if (isNullThanPrintAndLogErrorMessageFor(company)) return;
        companyDAO.save(company);
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException {
        companyDAO.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() throws SQLException {
        companyDAO.deleteAll();
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void setDeveloperDAO(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }
}
