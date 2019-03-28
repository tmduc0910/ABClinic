package com.abclinic.test.login;

public class Account {
    private String username;
    private String password;
    private int id;
    private String name;
    private String email;
    private String gender;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account username: " + username +
                "\nAccount password: " + password +
                "\nID: " + id +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nGender: " + gender +
                "\nPhone: " + phone;
    }
}
