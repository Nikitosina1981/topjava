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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
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
            if (startTime.isBefore(endTime))
            {
                Map<LocalDate, Integer> datesWithCalories = mealList.stream()
                        .collect(Collectors.groupingBy((r -> (r.getDateTime().toLocalDate())),
                                Collectors.summingInt(UserMeal::getCalories)));

                return mealList.stream()
                        .filter(e -> e.getDateTime().toLocalTime().isAfter(startTime))
                        .filter(e -> e.getDateTime().toLocalTime().isBefore(endTime))
                        .filter(e -> datesWithCalories.get(e.getDateTime().toLocalDate()) > caloriesPerDay)
                        .map(e -> new UserMealWithExceed(e.getDateTime(), e.getDescription(), e.getCalories(), true))
                        .collect(Collectors.toList());
            }
        throw new IllegalArgumentException();
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycles(List<UserMeal> mealList,
                                                                           LocalTime startTime, LocalTime endTime,
                                                                           int caloriesPerDay)
    {
        if (mealList != null && startTime != null && endTime != null)
            if (startTime.isBefore(endTime))
            {
                Map<LocalDate, Integer> datesWithCalories = new HashMap<>();
                List<UserMealWithExceed> result = new ArrayList<>();
                for (int i = 0; i < mealList.size(); i++)
                {
                    LocalDate date = mealList.get(i).getDateTime().toLocalDate();
                    datesWithCalories.merge(date, mealList.get(i).getCalories(), (oldVal, newVal) -> oldVal + newVal);
                }
                for (int i = 0; i < mealList.size(); i++)
                {
                    LocalDate date = mealList.get(i).getDateTime().toLocalDate();
                    if (datesWithCalories.get(date) > caloriesPerDay &&
                            mealList.get(i).getDateTime().toLocalTime().isAfter(startTime) &&
                            mealList.get(i).getDateTime().toLocalTime().isBefore(endTime))

                        result.add(new UserMealWithExceed(mealList.get(i).getDateTime(),
                                mealList.get(i).getDescription(), mealList.get(i).getCalories(), true));
                }
                return result;
            }
        throw new IllegalArgumentException();
    }
}
