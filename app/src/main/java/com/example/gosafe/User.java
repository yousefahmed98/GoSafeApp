package com.example.gosafe;

public class User {

    String fullName , userName, email, phoneNo, password;

    public User(String fullName, String userName, String email, String phoneNo, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public User() {
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPassword() {
        return password;
    }
}
