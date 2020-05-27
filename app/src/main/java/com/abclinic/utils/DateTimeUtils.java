package com.abclinic.utils;

import com.abclinic.constant.Constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateTimeUtils {
    public static LocalDateTime parseDateTime(int[] arr) {
        return LocalDateTime.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
    }

    public static LocalDateTime parseDateTime(List<Integer> list) {
        int size = list.size();
        while (size < 6) {
            list.add(0);
            size++;
        }
        return parseDateTime(list.stream().mapToInt(i -> i).toArray());
    }

    public static LocalDate parseDate(int[] arr) {
        return LocalDate.of(arr[0], arr[1], arr[2]);
    }

    public static LocalDate parseDate(List<Integer> list) {
        return parseDate(list.stream().mapToInt(i -> i).toArray());
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT);
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }

    public static String toString(LocalDateTime time) {
        if (time == null)
            return null;
        return time.format(DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT));
    }

    public static String toString(LocalDate date) {
        if (date == null)
            return null;
        return date.format(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT));
    }

    public static String toUrlString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(Constant.SENSITIVE_DATETIME_FORMAT));
    }
}
