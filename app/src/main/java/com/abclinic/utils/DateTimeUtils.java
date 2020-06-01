package com.abclinic.utils;

import com.abclinic.constant.Constant;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {
    public static LocalDateTime parseDateTime(int[] arr) {
        if (arr != null)
            return LocalDateTime.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
        return null;
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
        if (arr != null)
            return LocalDate.of(arr[0], arr[1], arr[2]);
        return null;
    }

    public static LocalDate parseDate(List<Integer> list) {
        return parseDate(list.stream().mapToInt(i -> i).toArray());
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT);
            return LocalDateTime.parse(dateTime, formatter);
        } else return null;
    }

    public static LocalDate parseDate(String date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT);
            return LocalDate.parse(date, formatter);
        } else return null;
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

    public static long toMilli(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static Date toDate(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime().toInstant(ZoneOffset.UTC);
        return Date.from(instant);
    }
}
