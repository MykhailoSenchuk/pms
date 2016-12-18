package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Project;

import java.sql.SQLException;

public interface ProjectDAO extends AbstractDAO<Project> {

    Project update(Project customer);

    void addDevToProject(Developer developer, Project project);

}
