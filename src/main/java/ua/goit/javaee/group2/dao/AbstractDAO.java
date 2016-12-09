package ua.goit.javaee.group2.dao;

import java.util.List;

interface AbstractDAO<T> {
    T save(T object);

    void saveAll(List<T> list);

    T load(int id);

    List<T> findAll();

    void deleteById(int id);

    void deleteAll();


}
