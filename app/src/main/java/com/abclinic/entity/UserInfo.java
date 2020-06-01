package com.abclinic.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abclinic.constant.Gender;
import com.abclinic.utils.DateTimeUtils;
import com.abclinic.utils.services.JsonService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "id",
//        "role",
//        "name",
//        "email",
//        "gender",
//        "dateOfBirth",
//        "age",
//        "phoneNumber",
//        "avatar",
//        "createdAt",
//        "address",
//        "practitioner",
//        "dietitians",
//        "specialists"
//})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

    @JsonProperty("id")
    private long id;
    @JsonProperty("role")
    private String role;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private int gender;
    @JsonProperty("dateOfBirth")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dateOfBirth;
    @JsonProperty("age")
    private int age;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("createdAt")
    private int[] createdAt = null;
    @JsonProperty("address")
    private String address;
    @JsonProperty("practitioner")
    private UserInfo practitioner;
    @JsonProperty("dietitians")
    private List<Dietitian> dietitians = null;
    @JsonProperty("specialists")
    private List<Specialist> specialists = null;
    @JsonProperty("specialties")
    private List<Specialty> specialties = null;
    @JsonProperty("specialty")
    private Specialty specialty;
    @JsonProperty("description")
    private String description;

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("gender")
    public int getGender() {
        return gender;
    }

    @JsonIgnore
    public String getGenderString() {
        try {
            return Gender.toGender(gender).toString();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @JsonProperty("gender")
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @JsonProperty("dateOfBirth")
    public LocalDate getDateOfBirth() {
        return DateTimeUtils.parseDate(dateOfBirth);
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("age")
    public int getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return DateTimeUtils.parseDateTime(createdAt);
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(int[] createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("practitioner")
    public UserInfo getPractitioner() {
        return practitioner;
    }

    @JsonProperty("practitioner")
    public void setPractitioner(UserInfo practitioner) {
        this.practitioner = practitioner;
    }

    @JsonProperty("dietitians")
    public List<Dietitian> getDietitians() {
        return dietitians;
    }

    @JsonProperty("dietitians")
    public void setDietitians(List<Dietitian> dietitians) {
        this.dietitians = dietitians;
    }

    @JsonProperty("specialists")
    public List<Specialist> getSpecialists() {
        return specialists;
    }

    @JsonProperty("specialists")
    public void setSpecialists(List<Specialist> specialists) {
        this.specialists = specialists;
    }

    @NonNull
    @Override
    public String toString() {
        return JsonService.toString(this);
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof UserInfo)
            return id == ((UserInfo) obj).getId();
        return false;
    }
}