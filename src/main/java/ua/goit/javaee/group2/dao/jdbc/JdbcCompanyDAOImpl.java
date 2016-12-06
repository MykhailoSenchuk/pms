package ua.goit.javaee.group2.dao.jdbc;

import ua.goit.javaee.group2.dao.CompanyDAO;
import ua.goit.javaee.group2.model.Company;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class JdbcCompanyDAOImpl implements CompanyDAO {

    private DataSource dataSource;

    @Override
    public Company save(Company object) throws SQLException {
        return null;
    }

    @Override
    public boolean saveAll(List<Company> list) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteAll() throws SQLException {
        return false;
    }

    @Override
    public Company load(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Company> findAll() throws SQLException {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
