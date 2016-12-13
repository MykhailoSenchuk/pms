package ua.goit.javaee.group2.controller;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ua.goit.javaee.group2.dao.ProjectDAO;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Project;

import java.sql.SQLException;
import java.util.List;

public class ProjectController  extends AbstractController<Project> {

    private PlatformTransactionManager txManager;

    private ProjectDAO projectDAO;

    public void addDeveloperToProject(Developer developer, Project project){
        if (project.isNew()){

            System.out.println("project isn't registered in DB");
            return;
        }

        if(developer.isNew()){
            System.out.println("developer isn't registered in DB");
        }
//TODO finish this
      project.getDevelopers().add(developer);
      projectDAO.save(project);

    }

    @Override
    public Project add(Project project){return projectDAO.save(project);}

    @Transactional
    public void createTable(String project){
        projectDAO.createTable(project);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Project get(int id) throws SQLException {
        return projectDAO.findById(id);
    }

    public List<Project> getAll() throws SQLException {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try{
            List<Project> result = projectDAO.findAll();
            txManager.commit(status);
            return result;
        }catch (NullPointerException e){
            System.out.println("Null pointer");
            txManager.rollback(status);
            throw new RuntimeException();
        }

    }

    @Override
    public void update(Project project){projectDAO.save(project);}

    @Transactional
    public void updateTable(String project){
        projectDAO.updateTable(project);
    }

    @Override
    public void delete(int id){projectDAO.deleteById(id);}

    @Transactional
    public void deleteTable(String project){
        projectDAO.deleteTable(project);
    }

    @Override
    public void deleteAll(){projectDAO.deleteAll();}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }
}
