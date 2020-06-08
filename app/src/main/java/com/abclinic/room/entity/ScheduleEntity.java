package com.abclinic.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.abclinic.entity.HealthIndexSchedule;
import com.abclinic.room.utils.DateConverter;

import java.time.LocalDateTime;

@Entity(tableName = "schedule",
        indices = {@Index("user_id"), @Index("schedule_id")})
public class ScheduleEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "schedule_id")
    private long scheduleId;
    @ColumnInfo(name = "index_name")
    private String indexName;
    @ColumnInfo(name = "index_description")
    private String indexDescription;
    @ColumnInfo(name = "scheduled_time")
    private int scheduledTime;
    @ColumnInfo(name = "status")
    private int status;
    @ColumnInfo(name = "started_at")
    @TypeConverters({DateConverter.class})
    private LocalDateTime startedAt;
    @ColumnInfo(name = "ended_at")
    @TypeConverters({DateConverter.class})
    private LocalDateTime endedAt;

    public ScheduleEntity() {
    }

    public ScheduleEntity(long userId, HealthIndexSchedule s) {
        this.userId = userId;
        this.scheduleId = s.getId();
        this.indexName = s.getIndex().getName();
        this.indexDescription = s.getIndex().getDescription();
        this.scheduledTime = s.getScheduledTime();
        this.status = s.getStatus();
        this.startedAt = s.getStartedAt();
        this.endedAt = s.getEndedAt();
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

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexDescription() {
        return indexDescription;
    }

    public void setIndexDescription(String indexDescription) {
        this.indexDescription = indexDescription;
    }

    public int getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(int scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
}
