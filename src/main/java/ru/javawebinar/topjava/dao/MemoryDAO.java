package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;


public interface MemoryDAO
{

    boolean create(Meal meal);
    boolean update(Integer key, Meal meal);
    boolean delete(Integer key);
    Meal read(Integer key);
    List<Meal> read();
    static MemoryDAO getDAOInstance(){return null;}
}
