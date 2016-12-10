package ua.goit.javaee.group2.dao.jdbc;

import ua.goit.javaee.group2.dao.ProjectDAO;
import ua.goit.javaee.group2.model.Project;

import javax.sql.DataSource;
import java.util.List;

public class JdbcProjectDAOImpl implements ProjectDAO {

    private DataSource dataSource;

    @Override
    public Project save(Project object){
        return null;
    }

    @Override
    public void deleteAll(){
    }

    @Override
    public void saveAll(List<Project> list){
    }

    @Override
    public List<Project> findAll(){
        return null;
    }

    @Override
    public void deleteById(int id){

    }

    @Override
    public Project load(int id){
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
