package com.narcyber.mvpbasics.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.narcyber.mvpbasics.helper.ConstantHelper;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = ConstantHelper.ROOM_USER_TABLE_NAME,
        indices = {@Index(value = {ConstantHelper.ROOM_USER_COLUMN_USERNAME}, unique = true)})
public class User implements Serializable {
    @Ignore
    static final long serialVersionUID = 42L;
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_NAME)
    private String fullName;
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_USERNAME)
    private String userName;
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_EMAIL)
    private String email;
    @ColumnInfo(name = ConstantHelper.ROOM_USER_COLUMN_PASSWORD)
    private String password;

    public User(String email, String fullName, String userName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                email.equals(user.email) &&
                fullName.equals(user.fullName) &&
                userName.equals(user.userName) &&
                password.equals(user.password);
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
