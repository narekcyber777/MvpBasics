package com.narcyber.mvpbasics.model;

import java.util.Objects;
import java.util.UUID;

public class User {

    private String id;
    private String email;
    private String fullName;
    private String userName;
    private String password;

    public User(String email, String fullName, String userName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        generateRandomId();
    }

    public User() {
        generateRandomId();
    }

    private void generateRandomId() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                email.equals(user.email) &&
                fullName.equals(user.fullName) &&
                userName.equals(user.userName) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, fullName, userName, password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
