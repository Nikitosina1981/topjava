package ru.javawebinar.topjava;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main
{
    public static void main(String[] args)
    {
        GetDAO.getDAO().read().forEach(e-> System.out.println(e.getDate()+" "+e.getCalories()+" "+e.getDescription()));
       // List<MealTo> res = MealsUtil.getFilteredWithExcess(GetDAO.getDAO().read(), GlobalVariables.START,
       //         GlobalVariables.END, GlobalVariables.EXCESS);
       // res.forEach(e-> System.out.println(e.getDateTime()+" "+e.getCalories()+" "+e.getDescription()));
    }
}
