package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectDAO extends AbstractDAO<Project> {
    @Override
    Project save(Project object) throws SQLException{

    };

    @Override
    boolean saveAll(List<Project> list) throws SQLException;

    @Override
    Project load(int id) throws SQLException;

    @Override
    List<Project> findAll() throws SQLException;

    @Override
    boolean deleteById(int id) throws SQLException;

    @Override
    boolean deleteAll() throws SQLException;
}
