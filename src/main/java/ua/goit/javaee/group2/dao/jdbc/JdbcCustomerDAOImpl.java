package ua.goit.javaee.group2.dao.jdbc;

import ua.goit.javaee.group2.dao.CustomerDAO;
import ua.goit.javaee.group2.model.Customer;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCustomerDAOImpl implements CustomerDAO {

    private DataSource dataSource;

    @Override
    public Customer save(Customer object){
        return null;
    }

    @Override
    public void saveAll(List<Customer> list){
    }

    @Override
    public Customer load(int id){
        return null;
    }

    @Override
    public List<Customer> findAll(){
        return null;
    }

    @Override
    public void deleteById(int id){
    }

    @Override
    public void deleteAll(){
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
