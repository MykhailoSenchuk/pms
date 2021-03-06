package ua.goit.javaee.group2.controller;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.ProjectDAO;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Project;

import java.sql.SQLException;
import java.util.List;

public class ProjectController  extends AbstractController<Project> {

    private ProjectDAO projectDAO;
    @Transactional
    public void addDeveloperToProject(Developer developer, Project project){
        projectDAO.addDevToProject(developer,project);
    }

    @Override
    @Transactional
    public Project add(Project project){
        if (isNullThanPrintAndLogErrorMessageFor(project)) return null;
        return projectDAO.save(project);}

    @Transactional(propagation = Propagation.REQUIRED)
    public Project get(int id) throws SQLException {
        return projectDAO.load(id);
    }

    @Transactional
    public List<Project> getAll() throws SQLException {
        return projectDAO.findAll();
    }

    @Override
    @Transactional
    public void update(Project project){
        if (isNullThanPrintAndLogErrorMessageFor(project)) return;
        projectDAO.save(project);
    }

    @Override
    @Transactional
    public void delete(int id){projectDAO.deleteById(id);}

    @Override
    @Transactional
    public void deleteAll(){projectDAO.deleteAll();}

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }
}
