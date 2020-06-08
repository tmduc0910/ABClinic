package com.abclinic.room.utils;

import androidx.room.TypeConverter;

import com.abclinic.constant.Constant;
import com.abclinic.utils.DateTimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public class DateConverter {
    private static final DateFormat formatter = new SimpleDateFormat(Constant.ROOM_DATE_TIME_FORMAT, Locale.getDefault());

    @TypeConverter
    public static LocalDateTime timeFromTimestamp(String value) {
        return DateTimeUtils.parseDateTimeSqlite(value);
    }

    @TypeConverter
    public static LocalDate dateFromTimestamp(String value) {
        return DateTimeUtils.parseDateSqlite(value);
    }

    @TypeConverter
    public static String toTimestamp(LocalDateTime date) {
        return DateTimeUtils.toSqliteString(date);
    }

    @TypeConverter
    public static String toTimestamp(LocalDate date) {
        return DateTimeUtils.toSqliteString(date);
    }
}
