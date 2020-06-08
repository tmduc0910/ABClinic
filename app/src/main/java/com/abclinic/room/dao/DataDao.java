package com.abclinic.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abclinic.room.entity.DataEntity;

import java.util.List;

@Dao
public interface DataDao {
    @Query("select * from data d order by d.date desc limit 1")
    DataEntity getLatestData();

    @Query("select * from data d where d.type = :type order by d.date desc")
    List<DataEntity> getAllByType(int type);

    @Query("select * from data d order by date asc limit 1")
    DataEntity getOldestData();

    @Query("select count(id) from data")
    int getSize();

    @Query("select * from data d where d.user_id = :userId and d.m_date = :month and d.y_date = :year")
    LiveData<List<DataEntity>> getDatas(long userId, int month, int year);

    @Query("select * from data d where d.user_id = :userId limit :from, :to")
    LiveData<List<DataEntity>> getPagedDatas(long userId, int from, int to);

    @Query("select * from data d where d.notification_id = :notificationId")
    DataEntity findByNotificationId(long notificationId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addData(DataEntity data);

    @Insert
    void save(DataEntity... datas);
}
