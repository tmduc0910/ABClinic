package com.abclinic.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.abclinic.room.utils.DateConverter;
import com.abclinic.utils.DateTimeUtils;

import java.time.LocalDateTime;

@Entity
public class DataEntity {
    @PrimaryKey
    private long id;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "payload_id")
    private long payloadId;
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private String date;

    public DataEntity() {
    }

    public DataEntity(long userId, int type, long payloadId, LocalDateTime date) {
        this.userId = userId;
        this.type = type;
        this.payloadId = payloadId;
        this.date = DateTimeUtils.toString(date);
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

    public long getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(long payloadId) {
        this.payloadId = payloadId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
