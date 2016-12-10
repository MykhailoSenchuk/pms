package ua.goit.javaee.group2.controller;

import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.SkillDAO;
import ua.goit.javaee.group2.model.Skill;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class SkillController extends AbstractController<Skill> {

    private PlatformTransactionManager txManager;

    private SkillDAO skillDAO;

    @Override
    @Transactional
    public Skill add(Skill skill){return skillDAO.save(skill);}

    @Override
    @Transactional
    public Skill get(int id){return skillDAO.load(id);}

    @Transactional
    public Skill getByName(String name){return skillDAO.getByName(name);}

    @Override
    @Transactional
    public List<Skill> getAll(){return skillDAO.findAll();}

    @Override
    @Transactional
    public void update(Skill skill){skillDAO.save(skill);}

    @Override
    @Transactional
    public void delete(int id){skillDAO.deleteById(id);}

    @Override
    @Transactional
    public void deleteAll(){skillDAO.deleteAll();}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setSkillDAO(SkillDAO skillDAO) {
        this.skillDAO = skillDAO;
    }
}
