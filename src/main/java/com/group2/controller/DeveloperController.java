package com.group2.controller;

import com.group2.dao.DeveloperDAO;
import com.group2.model.Developer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DeveloperController {

    private PlatformTransactionManager txManager;

    private DeveloperDAO developerDAO;

    public void add(Developer developer){}

    @Transactional
    public Developer get(int id){
        return null;
    }

    @Transactional
    public List<Developer> getAll(){
        return null;
    }

    public void update(Developer developer){}

    public void delete(int id){}

    public void deleteAll(){}

    public void setDeveloperDAO(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
}
