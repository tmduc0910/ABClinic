package com.abclinic.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abclinic.room.entity.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM userentity where user_id = :id")
    UserEntity getUser(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(UserEntity user);
}
