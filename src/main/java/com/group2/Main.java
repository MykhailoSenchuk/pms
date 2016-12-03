package com.group2;

import com.group2.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private CompanyController companyController;

    private CustomerController customerController;

    private DeveloperController developerController;

    private ProjectController projectController;

    private SkillController skillController;

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = context.getBean(Main.class);
        main.start();
    }

    private void start() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice = "";

        while (!choice.equalsIgnoreCase("0")) {
            System.out.println(
                    "\n---------Menu---------\n\n" +
                            "\nPlease choose table you want to work with:\n" +
                            "1. Companies\n" +
                            "2. Customers\n" +
                            "3. Developers\n" +
                            "4. Projects\n" +
                            "5. Skills\n" +
                            "0. Quit\n");
            choice = br.readLine();

            switch (choice) {
                case "1": // table companies
                {
                    System.out.println("CRUD operations for table companies");
                    while (!choice.equalsIgnoreCase("0")) {
                        System.out.println(
                                "\n---------Menu---------\n\n" +
                                        "\nPlease choose your action with table Copmanies:\n" +
                                        "1. Create (add 1 new row in table\n" +
                                        "2. Read by id\n" +
                                        "3. Read all\n" +
                                        "4. Update (update 1 row in table)\n" +
                                        "5. Delete by id\n" +
                                        "6. Delete all\n" +
                                        "0. Quit\n");
                        choice = br.readLine();

                        switch (choice) {
                            case "1": //
                            {
                                System.out.println("Creating new row. ");


                                break;
                            }
                            case "2": //
                            {
                                break;
                            }
                            case "3": //
                            {
                                break;
                            }
                            case "4": //
                            {
                                break;
                            }
                            case "0": //
                            {
                                br.close();
                                break;
                            }
                            default:
                                System.out.print("\nPlease, choose 1-5 or 0 : ");
                        }

                    }



                    break;
                }
                case "2": // table customers
                {
                    break;
                }
                case "3": // table developers
                {
                    break;
                }
                case "4": //table projects
                {
                    break;
                }
                case "5": //table skills
                {
                    break;
                }
                case "0": //exit
                {
                    System.out.println("Good Bye!");
                    br.close();
                    break;
                }
                default:
                    System.out.print("\nPlease, choose 1-5 or 0 : ");
            }
        }
    }

    public void setCompanyController(CompanyController companyController) {
        this.companyController = companyController;
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void setDeveloperController(DeveloperController developerController) {
        this.developerController = developerController;
    }

    public void setProjectController(ProjectController projectController) {
        this.projectController = projectController;
    }

    public void setSkillController(SkillController skillController) {
        this.skillController = skillController;
    }
}
