package com.group2.controller;

import com.group2.dao.CustomerDAO;
import com.group2.model.Customer;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class CustomerController extends AbstractController<Customer> {

    private PlatformTransactionManager txManager;

    private CustomerDAO customerDAO;

    //if table already have company with same name, than just return entity form table, don't create new one
    @Override
    public Customer add(Customer customer){return null;}

    @Override
    public Customer get(int id){return null;}

    @Override
    public List<Customer> getAll(){return null;}

    @Override
    public void update(Customer customer){}

    @Override
    public void delete(int id){}

    @Override
    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}
