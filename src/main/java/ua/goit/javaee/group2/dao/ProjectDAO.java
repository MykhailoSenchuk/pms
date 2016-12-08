package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Project;

import java.sql.SQLException;

public interface ProjectDAO extends AbstractDAO<Project> {

    Project findById(int id) throws SQLException;

    void createTable(String sqlQuery);

    void updateTable(String sqlQuery);

    void deleteTable(String tableName);

}
