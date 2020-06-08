package com.abclinic.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abclinic.room.entity.ScheduleEntity;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("select * from schedule where schedule_id = :id")
    ScheduleEntity getByScheduleId(long id);

    @Query("select * from schedule where user_id = :id and status = 0")
    LiveData<List<ScheduleEntity>> getAvailableSchedules(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(ScheduleEntity... scheduleEntity);
}
