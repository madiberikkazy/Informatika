package com.example.myapplication;

public class User {
    public String email,name,phone,password,image,suraq1,progress;

    public User(){}

    public User(String email, String name, String phone, String password, String image, String suraq1,String progress) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.suraq1 = suraq1;
        this.progress = progress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSuraq1() {
        return suraq1;
    }

    public void setSuraq1(String suraq1) {
        this.suraq1 = suraq1;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
