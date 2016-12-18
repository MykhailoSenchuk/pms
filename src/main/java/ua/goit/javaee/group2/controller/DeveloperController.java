package ua.goit.javaee.group2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.DeveloperDAO;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Skill;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DeveloperController extends AbstractController<Developer> {
    private PlatformTransactionManager txManager;

    private static final Logger LOG = LoggerFactory.getLogger(DeveloperController.class);

    private DeveloperDAO developerDAO;

    @Transactional
    public void addSkillsToDeveloper(Set<Skill> skills, Developer developer){
        developer.setSkills(skills);
        developerDAO.save(developer);
    }

    @Override
    @Transactional
    public Developer add (Developer developer) {
        return developerDAO.save(developer);
    }

    @Override
    @Transactional
    public Developer get(int id) throws SQLException {
        return developerDAO.load(id);
    }

    @Override
    @Transactional
    public List<Developer> getAll(){
        return developerDAO.findAll();
    }

    @Override
    @Transactional
    public void update(Developer developer){
        if (isNullThanPrintAndLogErrorMessageForObject(developer.getCompany())) return;
        developerDAO.save(developer);
    }

    @Override
    @Transactional
    public void delete(int id){developerDAO.deleteById(id);}

    @Override
    @Transactional
    public void deleteAll(){developerDAO.deleteAll();}

    public void setDeveloperDAO(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
}
