package com.group2;

import com.group2.controller.*;
import com.group2.model.Company;
import com.group2.model.Customer;
import com.group2.model.Developer;
import com.group2.model.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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

        String choice = "";

        while (!choice.equalsIgnoreCase("0")) {
            System.out.println(
                    "\n---------Menu---------\n\n" +
                            "\nPlease make your choice:\n" +
                            "1. CRUD operations with companies.\n" +
                            "2. CRUD operations with customers.\n" +
                            "3. CRUD operations with developers.\n" +
                            "4. CRUD operations with projects.\n" +
                            "5. CRUD operations with skills.\n" +
                            "6. Add skill to developer.\n" +
                            "7. Add developer to project.\n" +
                            "0. Quit\n");
            choice = br.readLine();

            switch (choice) {
                case "1": // table companies
                {
                    subMenuCrudOperationsFor(choice);


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
                case "6": //add skill to developer
                {
                    break;
                }
                case "7": //add developer to project
                {
                    break;
                }
                case "exit": //exit
                {
                    System.out.println("Good Bye!");
                    System.exit(0);
                }
                default:
                    System.out.print("\nPlease, choose 1-7 or \'exit\' : ");
            }
        }
    }

    private void subMenuCrudOperationsFor(String choice) throws Exception {
        String tableName = table(choice);
        System.out.println("CRUD operations for table " + tableName);
        while (!choice.equalsIgnoreCase("0")) {
            System.out.println(
                    "\n---------Menu---------\n\n" +
                            "\nPlease choose your CRUD operation with \n" + tableName +
                            "1. Create (add 1 new entity in table.\n" +
                            "2. Read by id.\n" +
                            "3. Read all.\n" +
                            "4. Update by id(update 1 row in table).\n" +
                            "5. Delete by id.\n" +
                            "6. Delete all.\n" +
                            "0. Back to main menu.\n");
            String subMenuChoice = br.readLine();

            switch (subMenuChoice) {
                case "1": // adding new entity
                {
                    System.out.print("Creating new row in table \n" + tableName +
                            "Please enter name of new entity: ");
                    String name = br.readLine();
                    switch (subMenuChoice) {
                        case "1": // adding new company
                        {
                            companyController.add(new Company(name));
                            break;
                        }
                        case "2": // adding new customer
                        {
                            customerController.add(new Customer(name));
                            break;
                        }
                        case "3": // adding new developer
                        {
                            System.out.print("Please enter last name of new developer: ");
                            String lastName = br.readLine();
                            System.out.print("Please enter company id of new developer: ");
                            Integer companyId = Integer.valueOf(br.readLine());
                            System.out.println("Please add skills to new developer. Type name of skills. Press \'Enter\' after each skill name. Press twice \'Enter\' to end intut.");

                            Set<Skill> skills = new HashSet<>();
                            String skillName = br.readLine();
                            while (!"".equals(skillName)){
                                skills.add(skillController.add(new Skill(skillName)));
                            }

                            developerController.add(new Developer(name, lastName, companyController.get(companyId), skills));
                            break;
                        }
                        case "4": //  adding new project
                        {
//                            companyController.add(new Company(name));
                            break;
                        }
                        case "5": //  adding new skill
                        {
                            skillController.add(new Skill(name));
                            break;
                        }
                    }

                    System.out.println("New entity was successfully added in table" + tableName + ".");
                    continue;
                }
                case "2": //
                {
                    System.out.print("Reading by id from table " + tableName + "\n" +
                            "Please enter id: ");
                    String id = br.readLine();
                    Company company = companyController.get(Integer.valueOf(id));
                    System.out.println("New entity was successfully added into table " + tableName + ":\n" + company);
                    continue;
                }
                case "3": //
                {
                    System.out.print("All entities in table " + tableName + ":\n");
                    companyController.getAll().forEach(System.out::println);
                    continue;
                }
                case "4": //
                {
                    System.out.print("Updating by id entity in table " + tableName + "..\n" +
                            "Please enter id: ");
                    String id = br.readLine();
                    Company company = companyController.get(Integer.valueOf(id));
                    System.out.print("Entity from table " + tableName + " with id=" + id + ":\n"
                            + company +
                            "\nPlase enter new name of entity from table " + tableName + " or press enter to not change name of entity: ");
                    String name = br.readLine();
                    if (!("".equals(name) || company.getName().equals(name))) {
                        companyController.update(new Company(company.getId(), name));
                        System.out.println("Entity in table " + tableName + " was successfully updated:\n" + company);
                        continue;
                    }
                    System.out.println("Entity from table " + tableName + " was not updated because because no new parameters added:\n" + company);
                }
                case "5": //
                {
                    System.out.print("Deleting by id from table " + tableName + "\n" +
                            "Please enter id: ");
                    Integer id = Integer.valueOf(br.readLine());
                    System.out.println("Do you really want to delete entity:\n"
                            + companyController.get(id) +
                            "\nPress \'y\\Y\' to confirm or \'n\\N\' to cancel deleting.");
                    choice = br.readLine();
                    if (!("5".equals(choice) || "".equals(choice) || choice.equalsIgnoreCase("y"))) {
                        companyController.delete(id);
                        System.out.println("Entity from table " + tableName + " with id=" + id + "was successfully deleted:\n");

                    }
                    System.out.println("Deleting was canceled.");
                    continue;
                }
                case "6": //
                {

                    System.out.print("Deleting all entities  from table " + tableName + "\n");
                    System.out.println("Do you really want to delete all entities from table " + tableName + "?\n" +
                            "Press \'y\\Y\' to confirm or \'n\\N\' to cancel deleting.");
                    choice = br.readLine();
                    if (!("6".equals(choice) || "".equals(choice) || choice.equalsIgnoreCase("y"))) {
                        companyController.deleteAll();
                        System.out.println("All entities from table " + tableName + " were successfully deleted:\n");
                    }
                    System.out.println("Deleting was canceled.");
                    continue;
                }
                case "0": //
                {
                    continue;
                }
                case "exit": //
                {
                    System.exit(0);
                }
                default:
                    System.out.print("\nPlease, choose 1-5 or 0 : ");
            }

        }
    }

    private String table(String choice) {
        switch (choice) {
            case "1":
                return "companies";
            case "2":
                return "customers";
            case "3":
                return "developers";
            case "4":
                return "projects";
            case "5":
                return "skills";
        }
        return null;
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
