package ua.goit.javaee.group2.controller;

import java.sql.SQLException;
import java.util.List;

public abstract  class AbstractController<T> {

    public abstract T add(T t) throws SQLException;

    public abstract T get(int id) throws SQLException;

    public abstract List<T> getAll() throws SQLException;

    public abstract void update(T t) throws SQLException;

    public abstract void delete(int id) throws SQLException;

    public abstract void deleteAll() throws SQLException;
}
