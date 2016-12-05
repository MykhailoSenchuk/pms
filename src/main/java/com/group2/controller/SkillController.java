package com.group2.controller;

import com.group2.dao.SkillDAO;
import com.group2.model.Skill;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class SkillController extends AbstractController<Skill> {

    private PlatformTransactionManager txManager;

    private SkillDAO skillDAO;

    //if table already have company with same name, than just return entity form table, don't create new one
    @Override
    public Skill add(Skill skill){return null;}

    @Override
    public Skill get(int id){return null;}

    @Override
    public List<Skill> getAll(){return null;}

    @Override
    public void update(Skill skill){}

    @Override
    public void delete(int id){}

    @Override
    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setSkillDAO(SkillDAO skillDAO) {
        this.skillDAO = skillDAO;
    }
}
