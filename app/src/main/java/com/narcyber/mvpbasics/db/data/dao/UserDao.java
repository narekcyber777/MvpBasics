package com.narcyber.mvpbasics.db.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.narcyber.mvpbasics.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrUpdate(User user);

    @Query("SELECT * FROM users ")
    Observable<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE email IS :email AND password IS :password")
    Single<User> getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM users WHERE userName IS :userName")
    Single<User> getUserByUsername(String userName);

    @Query("SELECT * FROM users WHERE email IS :email")
    Single<User> getUserByEmail(String email);

    @Delete
    Single<Integer> deleteUser(User user);

    @Update
    Completable update(User user);


}
