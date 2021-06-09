package com.narcyber.mvpbasics.model;

import java.util.Objects;

public class UserEmailPasswordStorage {
    private  String email;
    private  String password;

    public UserEmailPasswordStorage(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEmailPasswordStorage)) return false;
        UserEmailPasswordStorage that = (UserEmailPasswordStorage) o;
        return getEmail().equals(that.getEmail()) &&
                getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return "UserEmailPasswordStorage{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
