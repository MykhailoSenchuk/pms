package ua.goit.javaee.group2.controller;

import ua.goit.javaee.group2.dao.ProjectDAO;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Project;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class ProjectController  extends AbstractController<Project> {

    private PlatformTransactionManager txManager;

    private ProjectDAO projectDAO;

    public void addDeveloperToProject(Developer developer, Project project){

//        тут нужно реализовать добавление девелоперов в проекты

//
//




    }
    //if table already have company with same name, than just return entity form table, don't create new one
    @Override
    public Project add(Project project){return null;}

    @Override
    public Project get(int id){return null;}

    @Override
    public List<Project> getAll(){return null;}

    @Override
    public void update(Project project){}

    @Override
    public void delete(int id){}

    @Override
    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }
}
