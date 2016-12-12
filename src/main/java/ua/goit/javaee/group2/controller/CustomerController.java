package ua.goit.javaee.group2.controller;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.CustomerDAO;
import ua.goit.javaee.group2.model.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerController extends AbstractController<Customer> {

    private PlatformTransactionManager txManager;

    private CustomerDAO customerDAO;

    //if table already have company with same name, than just return entity form table, don't create new one
    @Transactional
    @Override
    public Customer add(Customer customer) throws SQLException {
        if(customer == null)
            return null;

    //search customer by name
    Customer byName = customerDAO.load(customer.getName());

    //if wasn't found by name add row to db
    if ( byName == null ){
        customerDAO.save(customer);
        return customer;
    } else
        return byName;
}

    @Transactional
    public boolean checkById (int id) throws SQLException {
        return get(id) != null;
    }

    @Transactional
    @Override
    public Customer get(int id) throws SQLException{
        return customerDAO.load(id);
    }

    @Transactional
    @Override
    public List<Customer> getAll() throws SQLException{
        return customerDAO.findAll();
    }

    @Transactional
    @Override
    public void update(Customer customer) throws SQLException{
        customerDAO.update(customer);
    }

    @Transactional
    @Override
    public void delete(int id) throws SQLException{
        customerDAO.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() throws SQLException{
        customerDAO.deleteAll();
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}
