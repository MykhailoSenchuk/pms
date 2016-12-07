package ua.goit.javaee.group2.controller;

import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.CustomerDAO;
import ua.goit.javaee.group2.model.Customer;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class CustomerController extends AbstractController<Customer> {

    private PlatformTransactionManager txManager;

    private CustomerDAO customerDAO;

    //if table already have company with same name, than just return entity form table, don't create new one
    @Transactional
    @Override
    public Customer add(Customer customer){
        if(customer == null)
            return null;

        //search customer by name
        Customer byName = customerDAO.load(customer.getName());

        //if wasn't found by name add row to db
        if ( byName == null ){
            customerDAO.save(customer);
            return customer;
        }
        else
            return byName;
    }

    @Transactional
    @Override
    public Customer get(int id){
        return customerDAO.load(id);
    }

    @Transactional
    @Override
    public List<Customer> getAll(){
        return customerDAO.findAll();
    }

    @Transactional
    @Override
    public void update(Customer customer){
        customerDAO.update(customer);
    }

    @Transactional
    @Override
    public void delete(int id){
        customerDAO.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll(){
        customerDAO.deleteAll();
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}
