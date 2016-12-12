package ua.goit.javaee.group2.dao;

import java.sql.SQLException;
import java.util.List;

interface AbstractDAO<T> {
    T save(T object);

    void saveAll(List<T> list);

    T load(int id) throws SQLException;

    List<T> findAll();

    void deleteById(int id);

    boolean deleteAll();


}
