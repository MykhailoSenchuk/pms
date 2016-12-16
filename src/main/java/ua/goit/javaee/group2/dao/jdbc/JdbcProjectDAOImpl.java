package ua.goit.javaee.group2.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.dao.CustomerDAO;
import ua.goit.javaee.group2.dao.ProjectDAO;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Project;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProjectDAOImpl implements ProjectDAO {

    static final String SELECT_ALL = "SELECT * FROM pms.projects JOIN pms.companies ON (pms.projects.company_id = pms.companies.id)\n" +
            "  JOIN pms.customers ON (pms.customers.id = pms.projects.customer_id)";
    static final String FIND_BY_ID = "SELECT * FROM pms.projects WHERE id = ?";
    static final String CREATE_TABLE = "CREATE table new_table (id int not null,string TEXT NOT NULL)";
    static final String GET_BY_NAME = "SELECT * FROM pms.projects WHERE project_name =?";
    private static final String UPDATE_ROW = "UPDATE pms.projects SET project_name = ?, customer_id = ?, company_id =? WHERE id =?";
    private static final String INSERT_ROW = "INSERT INTO pms.projects (project_name, customer_id, company_id, cost) VALUES (?,?,?,?)";


    static final Logger LOGGER = LoggerFactory.getLogger(ProjectDAO.class);
    private CompanyDAO companyDAO;
    private CustomerDAO customerDAO;

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //private Connection connection;

    private static int inputID(BufferedReader br) {
        int selectId = 0;
        try {
            System.out.print("Input id:");
            selectId = Integer.parseInt(br.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input. Need a number!!!");
        } catch (IOException e) {
            System.out.println("IOException occurred");
        }
        return selectId;
    }

    private static int inputCost(BufferedReader br) {
        int selectCost = 0;
        try {
            System.out.print("Input cost of project:");
            selectCost = Integer.parseInt(br.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input. Need a number!!!");
        } catch (IOException e) {
            System.out.println("IOException occurred");
        }
        return selectCost;
    }

    private DataSource dataSource;

    @Override
    public Project save(Project project) {
        if (!project.isNew()) {
            update(project);
            removeDeveloperOf(project);
        } else {
            create(project);
        }
        addDeveloperTo(project);
        return project;
    }

    private void removeDeveloperOf(Project project) {
        try(Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM pms.projects_developers WHERE project_id=?")) {
            ps.setInt(1, project.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private void addDeveloperTo(Project project){
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO pms.projects_developers(developer_id, project_id) VALUES (?,?)")) {
            for (Developer developer : project.getDevelopers()) {
                preparedStatement.setInt(2, project.getId());
                preparedStatement.setInt(1, developer.getId());
                preparedStatement.execute();
                LOGGER.info("Adding projects successful");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private Project create(Project project) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROW, PreparedStatement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, project.getName());
                preparedStatement.setInt(2, project.getCustomer().getId());
                preparedStatement.setInt(3, project.getCompany().getId());
                preparedStatement.setInt(4, inputCost(br));

                if (preparedStatement.executeUpdate() == 0) {
                    throw new SQLException("Creating project failed, no rows affected.");
                }
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        project.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating project failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save project: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return project;
    }

    public Project update(Project project) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
                if (project.getName() == null){
                    System.out.println("There can't be empty name of project");
                }else {
                    preparedStatement.setString(1, project.getName());
                }
                if (project.getCustomer().getId() == null){
                    System.out.println("customerId is: " + project.getCustomer().getId());
                }else {
                    preparedStatement.setLong(2, project.getCustomer().getId());
                }
                if (project.getCompany().getId() == null){
                    System.out.println("companyId is: " + project.getCompany().getId());
                }else {
                    preparedStatement.setLong(3, project.getCompany().getId());
                }
                    preparedStatement.setInt(4, project.getId());

                if (preparedStatement.executeUpdate() == 0) {
                    throw new SQLException("Updating project failed, no rows affected");
                }
                return project;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project loadString(String name) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    return new Project(resultSet.getInt(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw e;
        }
    }

    @Override
    public void deleteAll(){
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM pms.projects CASCADE");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Project> list){
        try (Connection connection = getConnection()) {
            //insert each customer into BD
            for (Project project : list) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROW)) {
                    preparedStatement.setString(1, project.getProject_name());
                    //break method if the customer is  not saved, provided that no commit will be made
                    if (preparedStatement.executeUpdate() == 0) {
                        return;
                    }
                }
            }
            return;
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Project load(int id) {
        return null;
    }

    //@Transactional(propagation = Propagation.MANDATORY)
    @Override
    public List<Project> findAll() {
        List<Project> resultProject = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Successfully connected to DB...");

            try(ResultSet resultSet = statement.executeQuery(SELECT_ALL)){

                while (resultSet.next()) {
                    Project project = new Project();

                    project.setCompany(companyDAO.load(resultSet.getInt(Project.COMPANY_ID)));
                    project.setCustomer(customerDAO.load(resultSet.getInt(Project.CUSTOMER_ID)));
                    project.setId(resultSet.getInt(Project.ID));
                    project.setProject_name(resultSet.getString(Project.PROJECT_NAME));
                    project.setCompany_id(resultSet.getLong(Project.COMPANY_ID));
                    project.setCustomer_id(resultSet.getLong(Project.CUSTOMER_ID));
                    project.setCompanyStr(resultSet.getString(Project.COMPANY_NAME));
                    project.setCustomerStr(resultSet.getString(Project.CUSTOMER_NAME));
                    project.setCost(resultSet.getFloat(Project.COST));

                    try {
                        resultProject.add(project);
                    } catch (NullPointerException e) {
                        System.out.println("NullPointerException happens at: findAll()");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to DB" + " " + e);
            throw new RuntimeException(e);
        }
        return resultProject;
    }

    @Override
    public void deleteById(int id){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pms.projects WHERE id=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            LOGGER.info("Project was successfully deleted.");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred: " + e);
        }
    }

    @Override
    public Project findById(int id) throws SQLException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                Project project;
                if (resultSet.next()) {
                    project = createProject(resultSet);
                    LOGGER.info("Project " + project + " successfully added to database.");
                    return project;
                } else {
                    LOGGER.info("Project was not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred: " + e);
            throw new RuntimeException(e);
        }
    }

    private Project createProject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setCompany(companyDAO.load( resultSet.getInt(Project.COMPANY_ID) ));
        project.setCustomer(customerDAO.load( resultSet.getInt(Project.CUSTOMER_ID) ));
        project.setId(resultSet.getInt(Project.ID));
        project.setProject_name(resultSet.getString(Project.PROJECT_NAME));
        project.setCost(resultSet.getFloat(Project.COST));
        return project;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //@Transactional(propagation = Propagation.MANDATORY)

    public void createTable(String sqlQuery) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Successfully connected to DB...");
            try{
                System.out.println("Creating Table...");
                statement.execute(sqlQuery);
                System.out.println("New Table successfully created");

            }catch (SQLException e){
                System.out.println("Can't create Table... " + e);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to DB" + " " + e);
            throw new RuntimeException(e);
        }
    }

    //@Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void updateTable(String sqlQuery) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Successfully connected to DB...");
            try{
                System.out.println("Updating Table...");
                statement.execute(sqlQuery);
                System.out.println("Table successfully updated");

            }catch (SQLException e){
                System.out.println("Can't update Table... " + e);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to DB" + " " + e);
            throw new RuntimeException(e);
        }
    }

    //@Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void deleteTable(String tableName) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Successfully connected to DB...");
            try{
                System.out.println("Deleting Table...");
                statement.execute(tableName);
                System.out.println("Table successfully deleted");

            }catch (SQLException e){
                System.out.println("Can't delete Table... " + e);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to DB " + " " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addDevToProject(Developer developer, Project project) {
        if (developer.isNew() && project.isNew()){
            System.out.println("Developer or project isn't registered in DB");
        }else {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection
                 .prepareStatement("INSERT INTO pms.projects_developers (project_id, developer_id) VALUES (?,?)")){
                preparedStatement.setInt(1,project.getId());
                preparedStatement.setInt(2,developer.getId());
                if (project.getId() != null && developer.getId() != null){
                    preparedStatement.execute();
                }else{
                    System.out.println("One of the inputs is null: " + "projectId - "
                            + project.getId() + ", " + "developerId - " + developer.getId());
                }
            } catch (SQLException e) {
                LOGGER.error("Can't establish connection " + e);
            }
        }
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
