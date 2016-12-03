package com.group2.controller;

import com.group2.dao.SkillDAO;
import com.group2.model.Project;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class SkillController {

    private PlatformTransactionManager txManager;

    private SkillDAO skillDAO;

    public void add(Project project){}

    public Project get(int id){return null;}

    public List<Project> getAll(){return null;}

    public void update(Project company){}

    public void delete(int id){}

    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setSkillDAO(SkillDAO skillDAO) {
        this.skillDAO = skillDAO;
    }
}
