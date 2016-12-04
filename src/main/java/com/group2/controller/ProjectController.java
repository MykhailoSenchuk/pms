package com.group2.controller;

import com.group2.dao.ProjectDAO;
import com.group2.model.Developer;
import com.group2.model.Project;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class ProjectController {

    private PlatformTransactionManager txManager;

    private ProjectDAO projectDAO;

    public void addDeveloperToProject(Developer developer, Project project){}

    //if table already have company with same name, than just return entity form table, don't create new one
    public Project add(Project project){return null;}

    public Project get(int id){return null;}

    public List<Project> getAll(){return null;}

    public void update(Project project){}

    public void delete(int id){}

    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }
}
