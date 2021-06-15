package com.narcyber.mvpbasics.presenter;

import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class AllUsersFragmentPresenter extends ParentPresenter {

    private final AllUserView view;

    public AllUsersFragmentPresenter(AllUserView view) {
        this.view = view;
    }

    public void getAllUsers() {
        getUserRepository().requestForAllUsers();
    }

    @Override
    public void respondForAllUsers(List<User> users) {
        view.allUsers(users);
    }

    public interface AllUserView {
        void allUsers(List<User> userList);
    }

}
