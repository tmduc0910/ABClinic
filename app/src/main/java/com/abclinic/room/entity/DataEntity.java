package com.abclinic.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.abclinic.room.utils.DateConverter;

import java.time.LocalDateTime;

@Entity(tableName = "data",
        indices = {@Index({"m_date", "y_date"}), @Index("notification_id")})
public class DataEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "notification_id")
    private long notificationId;
    @ColumnInfo(name = "payload_id")
    private long payloadId;
    @TypeConverters({DateConverter.class})
    @ColumnInfo(name = "date")
    private LocalDateTime date;
    @ColumnInfo(name = "m_day")
    private int day;
    @ColumnInfo(name = "m_date")
    private int month;
    @ColumnInfo(name = "y_date")
    private int year;

    public DataEntity() {
    }

    public DataEntity(long userId, int type, long payloadId, LocalDateTime date) {
        this.userId = userId;
        this.type = type;
        this.payloadId = payloadId;
        this.date = date;
        this.day = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public DataEntity(long userId, int type, long notificationId, long payloadId, LocalDateTime date) {
        this.userId = userId;
        this.type = type;
        this.notificationId = notificationId;
        this.payloadId = payloadId;
        this.date = date;
        this.day = date.getDayOfMonth();
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public long getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(long payloadId) {
        this.payloadId = payloadId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
        this.day = date.getDayOfMonth();
        this.year = date.getYear();
        this.month = date.getMonthValue();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
