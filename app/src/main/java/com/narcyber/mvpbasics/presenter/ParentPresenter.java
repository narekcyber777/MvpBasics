package com.narcyber.mvpbasics.presenter;

import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.repository.UserRepository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class ParentPresenter implements UserRepository.PresenterContract {

    private final CompositeDisposable compositeDisposable;
    private final UserRepository userRepository;

    public ParentPresenter() {
        this.compositeDisposable = new CompositeDisposable();
        this.userRepository = new UserRepository(this);
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    @Override
    public void respondSuccessPushing() {
    }

    @Override
    public void respondPushingFailed(String message) {
    }

    @Override
    public void respondUserByEmail(User user) {
    }

    @Override
    public void respondUserByUserName(User user) {
    }

    @Override
    public void respondUserByEmailAndPassword(User user) {
    }

    @Override
    public void respondDeletedUserCount(int count) {
    }

    @Override
    public void respondFailedDeleteUser(String message) {
    }

    @Override
    public void respondForAllUsers(List<User> users) {
    }


    @Override
    public CompositeDisposable getDisposables() {
        return this.compositeDisposable;
    }

    public void clearDisposables() {
        this.compositeDisposable.clear();
    }
}
