package ua.goit.javaee.group2.controller;

import ua.goit.javaee.group2.dao.DeveloperDAO;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Skill;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public class DeveloperController extends AbstractController<Developer> {

    private PlatformTransactionManager txManager;

    private DeveloperDAO developerDAO;

    public void addSkillsToDeveloper(Set<Skill> skills, Developer developer){}

    //if table already have company with same name, than just return entity form table, don't create new one
    @Override
    public Developer add(Developer developer){return null;}

    @Transactional
    @Override
    public Developer get(int id){
        return null;
    }

    @Transactional
    @Override
    public List<Developer> getAll(){
        return null;
    }

    @Override
    public void update(Developer developer){}

    @Override
    public void delete(int id){}

    @Override
    public void deleteAll(){}

    public void setDeveloperDAO(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
}
