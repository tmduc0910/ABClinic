package com.abclinic.entity;

import android.os.Parcel;

import com.abclinic.constant.NotificationType;
import com.abclinic.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "patient",
        "doctor",
        "index",
        "description",
        "scheduledTime",
        "status",
        "createdAt",
        "startedAt",
        "endedAt"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthIndexSchedule implements ISaveable {

    public static final Creator<HealthIndexSchedule> CREATOR = new Creator<HealthIndexSchedule>() {
        @Override
        public HealthIndexSchedule createFromParcel(Parcel source) {
            return new HealthIndexSchedule(source);
        }

        @Override
        public HealthIndexSchedule[] newArray(int size) {
            return new HealthIndexSchedule[size];
        }
    };

    @JsonProperty("id")
    private long id;
    @JsonProperty("patient")
    private UserInfo patient;
    @JsonProperty("doctor")
    private UserInfo doctor;
    @JsonProperty("index")
    private HealthIndex index;
    @JsonProperty("description")
    private String description;
    @JsonProperty("scheduledTime")
    private int scheduledTime;
    @JsonProperty("status")
    private int status;
    @JsonProperty("createdAt")
    private List<Integer> createdAt = null;
    @JsonProperty("startedAt")
    private List<Integer> startedAt = null;
    @JsonProperty("endedAt")
    private List<Integer> endedAt = null;


    public HealthIndexSchedule() {
    }

    protected HealthIndexSchedule(Parcel in) {
        this.id = in.readLong();
        this.index = in.readParcelable(HealthIndex.class.getClassLoader());
        this.description = in.readString();
        this.scheduledTime = in.readInt();
        this.status = in.readInt();
        this.startedAt = DateTimeUtils.toList(in.readString());
        this.endedAt = DateTimeUtils.toList(in.readString());
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @Override
    public int getDataType() {
        return NotificationType.SCHEDULE_REMINDER.getValue();
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("patient")
    public UserInfo getPatient() {
        return patient;
    }

    @JsonProperty("patient")
    public void setPatient(UserInfo patient) {
        this.patient = patient;
    }

    @JsonProperty("doctor")
    public UserInfo getDoctor() {
        return doctor;
    }

    @JsonProperty("doctor")
    public void setDoctor(UserInfo doctor) {
        this.doctor = doctor;
    }

    @JsonProperty("index")
    public HealthIndex getIndex() {
        return index;
    }

    @JsonProperty("index")
    public void setIndex(HealthIndex index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("scheduledTime")
    public int getScheduledTime() {
        return scheduledTime;
    }

    @JsonProperty("scheduledTime")
    public void setScheduledTime(int scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return DateTimeUtils.parseDateTime(createdAt);
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(List<Integer> createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("startedAt")
    public LocalDateTime getStartedAt() {
        return DateTimeUtils.parseDateTime(startedAt);
    }

    @JsonProperty("startedAt")
    public void setStartedAt(List<Integer> startedAt) {
        this.startedAt = startedAt;
    }

    @JsonProperty("endedAt")
    public LocalDateTime getEndedAt() {
        return DateTimeUtils.parseDateTime(endedAt);
    }

    @JsonProperty("endedAt")
    public void setEndedAt(List<Integer> endedAt) {
        this.endedAt = endedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.index, flags);
        dest.writeString(description);
        dest.writeInt(this.scheduledTime);
        dest.writeInt(this.status);
        dest.writeString(DateTimeUtils.toString(getStartedAt()));
        dest.writeString(DateTimeUtils.toString(getEndedAt()));
    }
}
