package ua.goit.javaee.group2.dao.jdbc;

import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.model.Company;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCompanyDAOImpl implements CompanyDAO {

    private DataSource dataSource;

    @Override
    public Company save(Company object){
        return null;
    }

    @Override
    public void saveAll(List<Company> list){
    }

    @Override
    public void deleteById(int id){
    }

    @Override
    public void deleteAll(){
    }

    @Override
    public Company load(int id){
        return null;
    }

    @Override
    public List<Company> findAll(){
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
