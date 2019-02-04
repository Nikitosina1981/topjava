package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil
{
    public static void main(String[] args)
    {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
        new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 7, 0), "Завтрак", 250),
        new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 11, 0), "Ещё завтрак", 500),
        new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 14, 0), "Обед", 550),
        new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 19, 0), "Ужин", 250)

        );
        List<UserMealWithExceed> listOfExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000);
        System.out.println(listOfExceeded.size());
        listOfExceeded = getFilteredWithExceededByCycles(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(listOfExceeded.size());

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesPerDay)
    {
        if (mealList != null && startTime != null && endTime != null)
            {
                Map<LocalDate, Integer> datesWithCalories = mealList.stream()
                        .collect(Collectors.groupingBy((r -> (r.getDateTime().toLocalDate())),
                                Collectors.summingInt(UserMeal::getCalories)));

                return mealList.stream()
                        .filter(e-> TimeUtil.isBetween(e.getDateTime().toLocalTime(), startTime, endTime))
                        .map(e -> new UserMealWithExceed(e.getDateTime(), e.getDescription(), e.getCalories(),
                                datesWithCalories.get(e.getDateTime().toLocalDate())>caloriesPerDay))
                        .collect(Collectors.toList());
            }
        throw new IllegalArgumentException();
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycles(List<UserMeal> mealList,
                                                                           LocalTime startTime, LocalTime endTime,
                                                                           int caloriesPerDay)
    {
        if (mealList != null && startTime != null && endTime != null)
            {
                Map<LocalDate, Integer> datesWithCalories = new HashMap<>();
                List<UserMealWithExceed> result = new ArrayList<>();
                for (UserMeal userMeal : mealList)
                {
                    LocalDate date = userMeal.getDateTime().toLocalDate();
                    datesWithCalories.merge(date, userMeal.getCalories(), Integer::sum);
                }
                for (UserMeal userMeal : mealList)
                {
                    LocalDate date = userMeal.getDateTime().toLocalDate();
                    if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime))
                        result.add(new UserMealWithExceed(userMeal.getDateTime(),
                                userMeal.getDescription(), userMeal.getCalories(),
                                datesWithCalories.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
                }
                return result;
            }
        throw new IllegalArgumentException();
    }
}
