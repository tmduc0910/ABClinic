package com.abclinic.entity;

import android.os.Parcel;

import com.abclinic.utils.DateTimeUtils;

import java.time.LocalDateTime;

public class SaveableImpl implements ISaveable {
    public static final Creator<SaveableImpl> CREATOR = new Creator<SaveableImpl>() {
        @Override
        public SaveableImpl createFromParcel(Parcel source) {
            return new SaveableImpl(source);
        }

        @Override
        public SaveableImpl[] newArray(int size) {
            return new SaveableImpl[size];
        }
    };

    private long id;
    private int dataType;
    private LocalDateTime createdAt;
    private long notificationId;

    public SaveableImpl() {

    }

    public SaveableImpl(long id, int dataType, LocalDateTime createdAt, long notificationId) {
        this.id = id;
        this.dataType = dataType;
        this.createdAt = createdAt;
        this.notificationId = notificationId;
    }

    protected SaveableImpl(Parcel in) {
        id = in.readLong();
        dataType = in.readInt();
        createdAt = DateTimeUtils.parseDateTime(in.readString());
        notificationId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(dataType);
        dest.writeString(DateTimeUtils.toString(createdAt));
        dest.writeLong(notificationId);
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }
}
