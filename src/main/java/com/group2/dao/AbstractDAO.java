package com.group2.dao;

import com.group2.model.BaseEntity;

import java.util.List;

interface AbstractDAO<T extends BaseEntity> {
    T save(T object);

    boolean saveAll(List<T> list);

    T load(int id);

    List<T> findAll();

    boolean deleteById(int id);

    boolean deleteAll();


}
