package com.abclinic.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.abclinic.room.entity.DataEntity;

import java.util.List;

@Dao
public interface DataDao {
    @Query("select * from dataentity d where strftime('%m', d.date) = :month and strftime('%y', d.date) = :year")
    List<DataEntity> getDatas(int month, int year);

    @Update
    void updateData(DataEntity data);

    @Insert
    void addData(DataEntity data);
}
