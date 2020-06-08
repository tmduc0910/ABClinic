package com.abclinic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.abclinic.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface ISaveable extends Parcelable {
    long getId();

    int getDataType();

    LocalDateTime getCreatedAt();

    @JsonIgnore
    default long getNotificationId() {
        throw new UnsupportedOperationException();
    }

    @Override
    default int describeContents() {
        return 0;
    }

    @Override
    default void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeInt(getDataType());
        dest.writeString(DateTimeUtils.toString(getCreatedAt()));
    }
}
