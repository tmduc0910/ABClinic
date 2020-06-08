package com.abclinic.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abclinic.room.entity.UserEntity;

@Dao
public interface UserDao {
    @Query("update user set is_logon = 0")
    void resetLogon();

    @Query("SELECT * FROM user where user_id = :id")
    UserEntity getUser(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(UserEntity user);

    @Query("SELECT * FROM user where is_logon = 1")
    UserEntity getLogonUser();

    @Query("SELECT * FROM user where (email = :username or phone = :username) and password = :password")
    UserEntity getUserByUsernameAndPassword(String username, String password);
}
