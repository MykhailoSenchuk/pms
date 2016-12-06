package com.group2;

import com.group2.controller.CompanyController;
import com.group2.model.Company;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


/**
 * Created by Mhs_Note on 05.12.2016.
 */
public class TestCompany {
    private CompanyController companyController;
    public static void main(String[] args) throws Exception{

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        TestCompany main = context.getBean(TestCompany.class);
        main.start();
    }
    private void start() throws Exception {

        List<Company> list = companyController.getAll();
        System.out.println(list);
    }

}
