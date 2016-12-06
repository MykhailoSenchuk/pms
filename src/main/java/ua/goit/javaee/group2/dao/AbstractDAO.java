package ua.goit.javaee.group2.dao;

import java.sql.SQLException;
import java.util.List;

interface AbstractDAO<T> {
    T save(T object) throws SQLException;

    boolean saveAll(List<T> list) throws SQLException;

    T load(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    boolean deleteById(int id) throws SQLException;

    boolean deleteAll() throws SQLException;


}
