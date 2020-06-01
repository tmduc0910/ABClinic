package com.abclinic.room.utils;

import androidx.room.TypeConverter;

import com.abclinic.constant.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final DateFormat formatter = new SimpleDateFormat(Constant.DATE_TIME_FORMAT, Locale.getDefault());

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return formatter.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
