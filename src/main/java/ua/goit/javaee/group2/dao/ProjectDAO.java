package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Project;

public interface ProjectDAO extends AbstractDAO<Project> {

   void addDevToProject(Developer developer, Project project);
}
