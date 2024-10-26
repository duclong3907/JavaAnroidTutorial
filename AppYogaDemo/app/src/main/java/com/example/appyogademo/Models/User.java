package com.example.appyogademo.Models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class User implements Serializable {
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULLNAME = "fullName";
    public static final String COLUMN_PHONENUMBER = "phoneNumber";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_EXPERIENCE = "experience";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";

    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String image;
    private String address;
    private Integer experience;
    private String password;
    private String role;

    public User(String id, String fullName, String phoneNumber, String email, @Nullable String image, @Nullable String address, @Nullable Integer experience, String password, String role) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = image;
        this.address = address;
        this.experience = experience != null ? experience : 0; // Handle null value
        this.password = password;
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public int getExperience() {
        return experience;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT,"
                    + COLUMN_FULLNAME + " TEXT,"
                    + COLUMN_PHONENUMBER + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_IMAGE + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_EXPERIENCE + " INTEGER,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_ROLE + " TEXT"
                    + ")";

}
