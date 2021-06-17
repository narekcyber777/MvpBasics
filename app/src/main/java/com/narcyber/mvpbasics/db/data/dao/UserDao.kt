package com.narcyber.mvpbasics.db.data.dao

import androidx.room.*
import com.narcyber.mvpbasics.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(user: User?): Completable

    @Query("SELECT * FROM users ")
    fun getAllUsers(): Observable<List<User>>

    @Query("SELECT * FROM users WHERE email IS :email AND password IS :password")
    fun getUserByEmailAndPassword(email: String, password: String): Single<User>

    @Query("SELECT * FROM users WHERE userName IS :userName")
    fun getUserByUsername(userName: String): Single<User>

    @Query("SELECT * FROM users WHERE email IS :email")
    fun getUserByEmail(email: String): Single<User>

    @Delete
    fun deleteUser(user: User): Single<Int>

    @Update
    fun update(user: User): Completable
}