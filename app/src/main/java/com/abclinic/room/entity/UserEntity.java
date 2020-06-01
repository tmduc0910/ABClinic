package com.abclinic.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.abclinic.entity.UserInfo;
import com.abclinic.room.utils.DateConverter;
import com.abclinic.utils.DateTimeUtils;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "avatar")
    private String avatar;
    @ColumnInfo(name = "gender")
    private int gender;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "dob")
    @TypeConverters({DateConverter.class})
    private String dateOfBirth;
    @TypeConverters({DateConverter.class})
    @ColumnInfo(name = "join_date")
    private String joinDate;
    @ColumnInfo(name = "checksum")
    private int checksum;

    public UserEntity() {
    }

    public UserEntity(UserInfo info) {
        this.userId = info.getId();
        this.name = info.getName();
        this.gender = info.getGender();
        this.avatar = info.getAvatar();
        this.address = info.getAddress();
        this.phone = info.getPhoneNumber();
        this.email = info.getEmail();
        this.dateOfBirth = DateTimeUtils.toString(info.getDateOfBirth());
        this.joinDate = DateTimeUtils.toString(info.getCreatedAt());
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.address, this.avatar, this.dateOfBirth, this.email, this.gender, this.phone, this.dateOfBirth);
    }

    public UserEntity clone(UserEntity info) {
        this.name = info.getName();
        this.gender = info.getGender();
        this.avatar = info.getAvatar();
        this.address = info.getAddress();
        this.phone = info.getPhone();
        this.email = info.getEmail();
        this.dateOfBirth = info.getDateOfBirth();
        this.joinDate = info.getJoinDate();
        this.checksum = this.hashCode();
        return this;
    }
}
