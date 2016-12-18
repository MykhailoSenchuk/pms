package ua.goit.javaee.group2.controller;

import org.slf4j.Logger;
import ua.goit.javaee.group2.model.NamedEntity;

import java.sql.SQLException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractController<T> {

    private static final Logger LOG = getLogger(AbstractController.class);

    public abstract T add(T t) throws SQLException;

    public abstract T get(int id) throws SQLException;

    public abstract List<T> getAll() throws SQLException;

    public abstract void update(T t) throws SQLException;

    public abstract void delete(int id) throws SQLException;

    public abstract void deleteAll() throws SQLException;

    public <T extends NamedEntity> boolean isNullThanPrintAndLogErrorMessageFor(T t) {
        if (t == null || t.getName() == null || "".equals(t.getName())) {
            String message = "Object " + t.getClass().getSimpleName() + " was not provided for updating method";
            LOG.error(message);
            System.out.println(message);
            return true;
        }
        return false;
    }
}
