package com.abclinic.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.abclinic.entity.UserInfo;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "is_logon")
    private boolean isLogon;
    @ColumnInfo(name = "checksum")
    private int checksum;

    public UserEntity() {
    }

    public UserEntity(UserInfo info) {
        this.userId = info.getId();
        this.name = info.getName();
        this.phone = info.getPhoneNumber();
        this.email = info.getEmail();
        this.password = info.getPassword();
        this.isLogon = info.isLogon();
        this.checksum = this.hashCode();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogon() {
        return isLogon;
    }

    public void setLogon(boolean logon) {
        isLogon = logon;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.name, this.email, this.phone, this.password, this.isLogon);
    }

    public UserEntity clone(UserEntity info) {
        this.name = info.getName();
        this.phone = info.getPhone();
        this.email = info.getEmail();
        this.password = info.getPassword();
        this.isLogon = info.isLogon();
        this.checksum = this.hashCode();
        return this;
    }
}
