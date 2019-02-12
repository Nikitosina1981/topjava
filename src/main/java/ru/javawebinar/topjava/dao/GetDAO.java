package ru.javawebinar.topjava.dao;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetDAO
{
   private static HashMap<Enum, MemoryDAO> DAOStrategy = new HashMap<>();
    public static MemoryDAO getDAO()
    {
        DAOStrategy.put(DAOTypes.memory, MemoryDAOimpl.getDAOInstance());
        ResourceBundle rb = ResourceBundle.getBundle("constants", new Locale("en", "US"));
        String strategy = rb.getString("strategy");
        for (Enum a:DAOTypes.values())
        {
            if (a.toString().contains(strategy)) return DAOStrategy.get(a);
        }

        try
        {
            throw new DatatypeConfigurationException();
        }
        catch (DatatypeConfigurationException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
