package com.group2.controller;

import java.util.List;

public abstract  class AbstractController<T> {

    public abstract T add(T t);

    public abstract T get(int id);

    public abstract List<T> getAll();

    public abstract void update(T t);

    public abstract void delete(int id);

    public abstract void deleteAll();
}
