package ru.javawebinar.topjava.dao;

import java.time.LocalTime;
import java.util.ResourceBundle;

public class GlobalVariables
{
    public static LocalTime START;
    public static LocalTime END;
    public static int EXCESS;
    public static final GlobalVariables instance = new GlobalVariables();
    static
    {
        synchronized (GlobalVariables.class)
        {
            ResourceBundle rb = ResourceBundle.getBundle("constants");
            START = LocalTime.of(Integer.parseInt(rb.getString("starthours")), Integer.parseInt(rb.getString(
                    "startminutes")));
            END = LocalTime.of(Integer.parseInt(rb.getString("endhours")), Integer.parseInt(rb.getString(
                    "endminutes")));
            EXCESS = Integer.parseInt(rb.getString("excess"));
        }
    }
    private GlobalVariables() {}
}

