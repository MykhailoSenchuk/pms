package ua.goit.javaee.group2.dao.jdbc;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.javaee.group2.dao.ProjectDAO;
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

    static final String FIND_BY_ID = "SELECT * FROM pms.projects\n" +
            "JOIN pms.companies ON (pms.projects.company_id = pms.companies.id)\n" +
            "JOIN pms.customers ON (pms.projects.customer_id = pms.customers.id)\n" +
            "WHERE pms.projects.id = ?";

    static final String CREATE_TABLE = "CREATE table new_table (id int not null,string TEXT NOT NULL)";

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static int inputID(BufferedReader br) {
        int selectId = 0;
        try {
            System.out.print("Input id:");
            selectId = Integer.parseInt(br.readLine());
            System.out.println(selectId);
        } catch (NumberFormatException e) {
            System.out.println("Wrong input. Need a number!!!");
        } catch (IOException e) {
            System.out.println("IOException occurred");
        }
        return selectId;
    }

    private DataSource dataSource;

    @Override
    public Project save(Project object) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteAll() throws SQLException {
        return false;
    }

    @Override
    public boolean saveAll(List<Project> list) throws SQLException {
        return false;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public List<Project> findAll() {
        List<Project> resultProject = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Successfully connected to DB...");

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()){
                Project projects = new Project();
                projects.setCompanyStr(resultSet.getString("company_name"));
                projects.setCustomerStr(resultSet.getString("customer_name"));
                projects.setId(resultSet.getInt("id"));
                projects.setProject_name(resultSet.getString("project_name"));
                projects.setCompany_id(resultSet.getLong("company_id"));
                projects.setCustomer_id(resultSet.getLong("customer_id"));
                projects.setCost(resultSet.getFloat("cost"));
                //System.out.println(projects);
                try{
                    resultProject.add(projects);
                }catch (NullPointerException e) {
                    System.out.println("NullPointerException happens at: findAll()");
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to DB " + " " + e);
            throw new RuntimeException(e);
        }
        return resultProject;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return false;
    }

    @Override
    public Project load(int id) throws SQLException {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Project findById(int id) {
        Project projects = new Project();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){
            System.out.println("Successfully connected to DB...");
            //preparedStatement.setInt(1, inputID(br));//For inputing from keyboard
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                projects.setCompanyStr(resultSet.getString("company_name"));
                projects.setCustomerStr(resultSet.getString("customer_name"));
                projects.setId(resultSet.getInt("id"));
                projects.setProject_name(resultSet.getString("project_name"));
                projects.setCompany_id(resultSet.getLong("company_id"));
                projects.setCustomer_id(resultSet.getLong("customer_id"));
                projects.setCost(resultSet.getFloat("cost"));
                //System.out.println(projects);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to DB " + " " + e);
            throw new RuntimeException(e);
        }
        try {
            br.close();
        } catch (IOException e) {
            System.out.println("Can't close BufferedReader");
        }
        return projects;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void createTable(String sqlQuery) {
        try (Connection connection = dataSource.getConnection();
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
            System.out.println("Exception occurred while connecting to DB " + " " + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void updateTable(String sqlQuery) {
        try (Connection connection = dataSource.getConnection();
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
            System.out.println("Exception occurred while connecting to DB " + " " + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void deleteTable(String tableName) {
        try (Connection connection = dataSource.getConnection();
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
}
