package com.group2;

import com.group2.controller.*;
import com.group2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
                    "\n---------Main-menu---------\n" +
                            "1. CRUD operations with companies.\n" +
                            "2. CRUD operations with customers.\n" +
                            "3. CRUD operations with developers.\n" +
                            "4. CRUD operations with projects.\n" +
                            "5. CRUD operations with skills.\n" +
                            "6. Add skills to developer.\n" +
                            "7. Add developer to project.\n" +
                            "0. Quit\n\n" +
                            "Please make your choice: ");
            choice = br.readLine();

            switch (choice) {
                case "1": // table companies
                case "2": // table customers
                case "3": // table developers
                case "4": //table projects
                case "5": { //table skills
                    getIntoSubMenu(choice);
                }
                case "6": //add skills to developer
                {
                    System.out.println("Start of adding skill to existing developer..");
                    Developer developer = developerController.get(getIdFromConsole());
                    System.out.println("Developer for adding skills:\n" + developer);
                    System.out.println("Now you need to input all skills of developer.");
                    Set<Skill> updatedSkills = developer.getSkills();
                    updatedSkills.addAll(getSkillsFromConsole());
                    developerController.update(
                            new Developer(developer.getId(), developer.getName(), developer.getLastName(),
                                    developer.getCompany(), updatedSkills));
                    break;
                }
                case "7": //add developer to project
                {
                    break;
                }
                case "0": //exit
                {
                    System.out.println("Good Bye!");
                    System.exit(0);
                }
                default:
                    System.out.print("\nPlease, choose 1-7 or \'exit\' : ");
            }
        }
    }

    private void getIntoSubMenu(String choice) throws Exception {
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
                    switch (choice) {
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
                            developerController.add(
                                    new Developer(name, lastName, companyController.get(companyId), getSkillsFromConsole()));
                            break;
                        }
                        case "4": //  adding new project
                        {

                            System.out.print("Please enter company id of new project: ");
                            Integer companyId = Integer.valueOf(br.readLine());
                            System.out.print("Please enter customer id of new project: ");
                            Integer customerId = Integer.valueOf(br.readLine());

                            System.out.println("Please developers to new project. Type id's of developer. Press \'Enter\' after each id of developer. Press twice \'Enter\' to end input.");

                            Set<Developer> developers = new HashSet<>();
                            String stringDeveloperId = br.readLine();
                            while (!"".equals(stringDeveloperId)) {
                                developers.add(developerController.get(Integer.valueOf(stringDeveloperId)));
                                stringDeveloperId = br.readLine();
                            }

                            projectController.add(new Project(name, companyController.get(companyId), customerController.get(customerId), developers));
//
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
                case "2": // reading by id
                {
                    System.out.print("Reading by id from table " + tableName + "\n" +
                            "Please enter id: ");
                    Integer id = Integer.valueOf(br.readLine());

                    switch (choice) {
                        case "1": // reading company by id
                        {
                            System.out.println("Successful reading of company:\n" + companyController.get(id));
                            break;
                        }
                        case "2": // reading customer by id
                        {
                            System.out.println("Successful reading of customer:\n" + customerController.get(id));
                            break;
                        }
                        case "3": // reading developer by id
                        {
                            System.out.println("Successful reading of developer:\n" + developerController.get(id));
                            break;
                        }
                        case "4": //  reading project by id
                        {

                            System.out.println("Successful reading of project:\n" + projectController.get(id));
//
                            break;
                        }
                        case "5": //  reading skill by id
                        {
                            System.out.println("Successful reading of skill:\n" + skillController.get(id));
                            break;
                        }
                        default: {
                            System.out.println("There is no entity with id=" + id + " in table " + tableName);
                        }
                    }
                    continue;
                }
                case "3": //reading all
                {
                    switch (choice) {
                        case "1": // reading all companies
                        {
                            companyController.getAll().forEach(System.out::println);
                            break;
                        }
                        case "2": // reading all customers
                        {
                            customerController.getAll().forEach(System.out::println);
                            break;
                        }
                        case "3": // reading all developers
                        {
                            developerController.getAll().forEach(System.out::println);
                            break;
                        }
                        case "4": //  reading all projects
                        {
                            projectController.getAll().forEach(System.out::println);
                            break;
                        }
                        case "5": //  reading all skills
                        {
                            skillController.getAll().forEach(System.out::println);
                            break;
                        }
                    }
                    continue;
                }
                case "4": // updating by id
                {
                    System.out.print("Updating by id in table " + tableName);
                    Integer id = getIdFromConsole();

                    System.out.print("Starting of data input for entity update." +
                            " ! Notion: Press twice \'Enter\' if you don\'t want to change this param (to keep this param as it is now).");
                    switch (choice) {
                        case "1": // updating company
                        {
                            Company company = companyController.get(id);
                            System.out.println("Company for update:\n" + company);
                            System.out.print("Enter new name of company :");
                            String newName = br.readLine();
                            companyController.update(new Company(id, newName));
                            break;
                        }
                        case "2": // updating customer
                        {
                            Customer customer = customerController.get(id);
                            System.out.println("Customer for update:\n" + customer);
                            System.out.print("Enter new name of customer:");
                            String newName = br.readLine();
                            customerController.update(new Customer(id, newName));
                            break;
                        }
                        case "3": // updating developer
                        {
                            Developer developer = developerController.get(id);
                            System.out.println("Developer for update:\n" + developer);
                            System.out.print("Enter new name of developer:");
                            String newName = br.readLine();
                            System.out.print("Please enter new last name of updated developer: ");
                            String newLastName = br.readLine();
                            System.out.print("Please enter new company id of updated developer: ");
                            Integer newCompanyId = Integer.valueOf(br.readLine());
                            System.out.println("Now you need to input all skills of updated developer.");
                            developerController.update(new Developer(id, newName, newLastName, companyController.get(newCompanyId), getSkillsFromConsole()));
                            break;
                        }
                        case "4": //  updating project
                        {

                            Project project = projectController.get(id);
                            System.out.println("Project for update:\n" + project);
                            System.out.print("Enter new name of project:");
                            String newName = br.readLine();

                            System.out.print("Please enter new company id of updated project: ");
                            Integer newCompanyId = Integer.valueOf(br.readLine());
                            System.out.print("Please enter new customer id of updated project: ");
                            Integer newCustomerId = Integer.valueOf(br.readLine());

                            System.out.println("Please developers to new project. Type id's of developer. Press \'Enter\' after each id of developer. Press twice \'Enter\' to end input.");

                            Set<Developer> newDevelopers = new HashSet<>();
                            String stringDeveloperId = br.readLine();
                            while (!"".equals(stringDeveloperId)) {
                                newDevelopers.add(developerController.get(Integer.valueOf(stringDeveloperId)));
                                stringDeveloperId = br.readLine();
                            }

                            projectController.update(new Project(id, newName, companyController.get(newCompanyId), customerController.get(newCustomerId), newDevelopers));
//
                            break;
                        }
                        case "5": //  updating skill
                        {
                            Skill skill = skillController.get(id);
                            System.out.println("Skill for update:\n" + skill);
                            System.out.print("Enter new name of skill:");
                            String newName = br.readLine();
                            skillController.update(new Skill(id, newName));
                            break;
                        }
                    }

                    System.out.println("Entity was successfully updated in table" + tableName + ".");
                    continue;
                }
                case "5": // deleting by id
                {
                    System.out.print("Deleting by id from table " + tableName + "\n" +
                            "Please enter id: ");
                    Integer id = Integer.valueOf(br.readLine());
                    System.out.println("Do you really want to delete entity:\n"
                            + companyController.get(id) +
                            "\nPress \'y\\Y\' to confirm or \'n\\N\' to cancel deleting.");
                    String deleteChoice = br.readLine();
                    if (deleteChoice.equalsIgnoreCase("y")) {
                        switch (choice) {
                            case "1": // deleting company by id
                            {
                                companyController.delete(id);
                                break;
                            }
                            case "2": // deleting customer by id
                            {
                                customerController.delete(id);
                                break;
                            }
                            case "3": // deleting developer by id
                            {
                                developerController.delete(id);
                                break;
                            }
                            case "4": //  deleting project by id
                            {
                                projectController.delete(id);
                                break;
                            }
                            case "5": //  deleting skill by id
                            {
                                skillController.delete(id);
                                break;
                            }
                        }
                        System.out.println("Entity from table " + tableName + " with id=" + id + "was successfully deleted:\n");

                    }
                    System.out.println("Deleting was canceled.");
                    continue;
                }
                case "6": // deleting all
                {

                    System.out.print("Deleting all entities  from table " + tableName + "\n");
                    System.out.println("Do you really want to delete all entities from table " + tableName + "?\n" +
                            "Press \'y\\Y\' to confirm or \'n\\N\' to cancel deleting.");
                    choice = br.readLine();
                    if (!("6".equals(choice) || "".equals(choice) || choice.equalsIgnoreCase("y"))) {
                        switch (choice) {
                            case "1": // deleting all companies
                            {
                                companyController.deleteAll();
                                break;
                            }
                            case "2": // deleting all customers
                            {
                                customerController.deleteAll();
                                break;
                            }
                            case "3": // deleting all developers
                            {
                                developerController.deleteAll();
                                break;
                            }
                            case "4": //  deleting all projects
                            {
                                projectController.deleteAll();
                                break;
                            }
                            case "5": //  deleting all skills
                            {
                                skillController.deleteAll();
                                break;
                            }
                        }
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

    private Integer getIdFromConsole() throws IOException {
        System.out.println("Please enter id: ");
        return Integer.valueOf(br.readLine());
    }

    private Set<Skill> getSkillsFromConsole() throws IOException {
        System.out.println("Type name of skill of developer, press \'Enter\' after each skill name. To stop input press \'Enter\' twice.");
        Set<Skill> skills = new HashSet<>();
        String skillName = br.readLine();
        while (!"".equals(skillName)) {
            skills.add(skillController.add(new Skill(skillName)));
            skillName = br.readLine();
        }
        return skills;
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
