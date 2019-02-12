package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryDAOimpl implements MemoryDAO
{
    private static ConcurrentHashMap<Integer, Meal> base = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger();
    private static MemoryDAOimpl memoryDAOimpl;

    private static void init()
    {
        getDAOInstance().create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        getDAOInstance().create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        getDAOInstance().create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        getDAOInstance().create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        getDAOInstance().create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        getDAOInstance().create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public boolean create(Meal meal)
    {
      if (meal==null)  return false;
      base.put(counter.getAndIncrement(),meal);
      return true;
    }

    @Override
    public boolean update(Integer key, Meal meal)
    {
       if (meal==null) return false;
       base.put(key,meal);
       return true;
    }

    @Override
    public boolean delete(Integer key)
    {
        base.remove(key);
        return true;
    }

    @Override
    public Meal read(Integer key)
    {
        return base.get(key);
    }

    public List<Meal> read()
    {
        return new ArrayList<>(base.values());
    }


    private MemoryDAOimpl()
    {
        memoryDAOimpl = this;
        init();
    }
    public static MemoryDAOimpl getDAOInstance()
    {
        if (memoryDAOimpl==null) memoryDAOimpl = new MemoryDAOimpl();
        return memoryDAOimpl;
    }
}
