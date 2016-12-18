package ua.goit.javaee.group2.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.dao.CustomerDAO;
import ua.goit.javaee.group2.dao.ProjectDAO;
import ua.goit.javaee.group2.model.Company;
import ua.goit.javaee.group2.model.Customer;
import ua.goit.javaee.group2.model.Developer;
import ua.goit.javaee.group2.model.Project;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JdbcProjectDAOImpl implements ProjectDAO {

    private static final String GET_ALL = String.format("SELECT * FROM pms.projects " +
                    "JOIN pms.companies ON (pms.projects.%s = pms.companies.%s) " +
                    "JOIN pms.customers ON (pms.customers.%s = pms.projects.%s)",
            Project.COMPANY_ID, Company.ID, Customer.ID, Project.CUSTOMER_ID);

    private static final String GET_BY_ID = String.format("SELECT * FROM pms.projects WHERE %s = ?",
            Project.ID);

    private static final String GET_BY_NAME = String.format("SELECT * FROM pms.projects WHERE %s = ?",
            Project.PROJECT_NAME);

    private static final String UPDATE_ROW = String.format(
            "UPDATE pms.projects SET %s = ?, %s = ?, %s =? WHERE %s =?",
            Project.PROJECT_NAME, Project.CUSTOMER_ID, Project.COMPANY_ID, Project.ID);

    private static final String INSERT_ROW = String.format(
            "INSERT INTO pms.projects (%s, %s, %s, %s) VALUES (?,?,?,?)",
            Project.PROJECT_NAME, Project.CUSTOMER_ID, Project.COMPANY_ID, Project.COST);

    private static final String REMOVE_DEVELOPER_FROM_PROJECT = "DELETE FROM pms.projects_developers WHERE project_id=?";

    private static final String ADD_DEVELOPERS_TO_PROJECT = "INSERT INTO pms.projects_developers(developer_id, project_id) VALUES (?,?)";

    private static final String DELETE_ALL = "DELETE FROM pms.projects CASCADE";

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectDAO.class);

    private CompanyDAO companyDAO;

    private CustomerDAO customerDAO;

    private DataSource dataSource;

    @Override
    public Project save(Project project) {
        if (!project.isNew()) {
            update(project);
            removeDeveloperFrom(project);
        } else {
            create(project);
        }
        addDevelopersTo(project);
        return project;
    }

    private void removeDeveloperFrom(Project project) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(REMOVE_DEVELOPER_FROM_PROJECT)) {
            ps.setInt(1, project.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred: " + e);
        }
    }

    private void addDevelopersTo(Project project) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_DEVELOPERS_TO_PROJECT)) {
            for (Developer developer : project.getDevelopers()) {
                preparedStatement.setInt(2, project.getId());
                preparedStatement.setInt(1, developer.getId());
                preparedStatement.execute();
                LOGGER.info("Adding projects successful");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred: " + e);
        }
    }

    private Project create(Project project) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROW, PreparedStatement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, project.getName());
                preparedStatement.setInt(2, project.getCustomer().getId());
                preparedStatement.setInt(3, project.getCompany().getId());
                preparedStatement.setFloat(4, project.getCost());

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
            return null;
        }
        return project;
    }

    public Project update(Project project) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
                if (project.getName() == null) {
                    System.out.println("There can't be empty name of project");
                } else {
                    preparedStatement.setString(1, project.getName());
                }
                if (project.getCustomer().getId() == null) {
                    System.out.println("customerId is: " + project.getCustomer().getId());
                } else {
                    preparedStatement.setLong(2, project.getCustomer().getId());
                }
                if (project.getCompany().getId() == null) {
                    System.out.println("companyId is: " + project.getCompany().getId());
                } else {
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
            return null;
        }
    }

    @Override
    public Project loadByName(String name) throws SQLException {
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
            return null;
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(DELETE_ALL);
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
        }
    }

    @Override
    public void saveAll(List<Project> list) {
        try (Connection connection = getConnection()) {
            //insert each customer into BD
            for (Project project : list) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROW)) {
                    preparedStatement.setString(1, project.getName());
                    //break method if the customer is  not saved, provided that no commit will be made
                    if (preparedStatement.executeUpdate() == 0) {
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
        }
    }

    @Override
    public Project load(int id) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    Set<Developer> developers = d
                    return new Project(

                            resultSet.getInt(Project.ID),
                            resultSet.getString(Project.PROJECT_NAME),
                            companyDAO.load(resultSet.getInt(Project.COMPANY_ID)),
                            customerDAO.load(resultSet.getInt(Project.CUSTOMER_ID)),
                    developers,
                            resultSet.getFloat(Project.COST));


                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
            return null;
        }


        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement()) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred: ", e);
        }
    }

    //@Transactional(propagation = Propagation.MANDATORY)
    @Override
    public List<Project> findAll() {
        List<Project> resultProject = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Successfully connected to DB...");

            try (ResultSet resultSet = statement.executeQuery(GET_ALL)) {

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
    public void deleteById(int id) {
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
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
        project.setCompany(companyDAO.load(resultSet.getInt(Project.COMPANY_ID)));
        project.setCustomer(customerDAO.load(resultSet.getInt(Project.CUSTOMER_ID)));
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
            try {
                System.out.println("Creating Table...");
                statement.execute(sqlQuery);
                System.out.println("New Table successfully created");

            } catch (SQLException e) {
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
            try {
                System.out.println("Updating Table...");
                statement.execute(sqlQuery);
                System.out.println("Table successfully updated");

            } catch (SQLException e) {
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
            try {
                System.out.println("Deleting Table...");
                statement.execute(tableName);
                System.out.println("Table successfully deleted");

            } catch (SQLException e) {
                System.out.println("Can't delete Table... " + e);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to DB " + " " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addDevToProject(Developer developer, Project project) {
        if (developer.isNew() && project.isNew()) {
            System.out.println("Developer or project isn't registered in DB");
        } else {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection
                         .prepareStatement("INSERT INTO pms.projects_developers (project_id, developer_id) VALUES (?,?)")) {
                preparedStatement.setInt(1, project.getId());
                preparedStatement.setInt(2, developer.getId());
                if (project.getId() != null && developer.getId() != null) {
                    preparedStatement.execute();
                } else {
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
