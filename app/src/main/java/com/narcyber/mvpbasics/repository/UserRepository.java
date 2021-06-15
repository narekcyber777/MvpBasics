package com.narcyber.mvpbasics.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.narcyber.mvpbasics.db.RoomDb;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRepository {
    private final PresenterContract presenter;
    private final RoomDb roomDb;

    public UserRepository(PresenterContract presenter) {
        this.presenter = presenter;
        this.roomDb = RoomDb.INSTANCE;

    }

    public void requestForAllUsers() {
        roomDb.userDao().getAllUsers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<User> users) {


                        presenter.respondForAllUsers(users);


                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Nar", "Oncompleted");
                    }
                });
    }

    public void requestDeleteUser(User user) {
        roomDb.userDao().deleteUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        presenter.getDisposables().add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                        presenter.respondDeletedUserCount(integer);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        presenter.respondFailedDeleteUser(e.toString());

                    }
                });

    }

    public void requestUserByEmailAndPassword(String email, String password) {
        roomDb.userDao().getUserByEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        presenter.getDisposables().add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull User user) {
                        presenter.respondUserByEmailAndPassword(user);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        presenter.respondUserByEmailAndPassword(null);
                    }
                });
    }

    public void requestUserByUsername(String userName) {
        roomDb.userDao().getUserByUsername(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        presenter.getDisposables().add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull User user) {
                        presenter.respondUserByUserName(user);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        presenter.respondUserByUserName(null);

                    }
                });

    }

    public void requestUserByEmail(String email) {
        roomDb.userDao().getUserByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        presenter.getDisposables().add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull User user) {
                        presenter.respondUserByEmail(user);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        presenter.respondUserByEmail(null);

                    }
                });

    }

    public void requestUserPush(@NonNull User user) {
        roomDb.userDao().insertOrUpdate(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        presenter.getDisposables().add(d);
                    }

                    @Override
                    public void onComplete() {
                        presenter.respondSuccessPushing();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        presenter.respondPushingFailed(
                                e.getMessage());
                    }
                });


    }


    public interface PresenterContract {
        CompositeDisposable getDisposables();

        void respondSuccessPushing();

        void respondPushingFailed(String message);

        void respondUserByEmail(User user);

        void respondUserByUserName(User user);

        void respondUserByEmailAndPassword(User user);

        void respondDeletedUserCount(int count);

        void respondFailedDeleteUser(String message);

        void respondForAllUsers(List<User> users);
    }


}
