package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
       List<UserMealWithExceed> listOfExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        System.out.println(listOfExceeded.size());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

       if (mealList!=null&&startTime!=null&&endTime!=null)
       if (startTime.isBefore(endTime)) {
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
}
