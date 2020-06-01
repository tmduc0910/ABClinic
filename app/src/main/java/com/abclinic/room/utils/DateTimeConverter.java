package com.abclinic.room.utils;

import androidx.room.TypeConverter;

import com.abclinic.constant.Constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

public class DateTimeConverter {
    private static final DateFormat formatter = new SimpleDateFormat(Constant.ROOM_DATE_TIME_FORMAT, Locale.getDefault());

    @TypeConverter
    public static Date fromDate(LocalDateTime dateTime) {
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);
        return Date.from(instant);
    }
}
