package com.group2.controller;

import com.group2.dao.CustomerDAO;
import com.group2.model.Customer;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

public class CustomerController {

    private PlatformTransactionManager txManager;

    private CustomerDAO customerDAO;

    public void add(Customer customer){}

    public Customer get(int id){return null;}

    public List<Customer> getAll(){return null;}

    public void update(Customer company){}

    public void delete(int id){}

    public void deleteAll(){}

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}
