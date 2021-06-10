package com.narcyber.mvpbasics.presenter;

import android.content.Context;
import android.util.Log;

import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class UserActivityPresenter {
    private final UserActivityView view;
    private final DataSaveHelper dataSaveHelper;
    private User user;

    public UserActivityPresenter(UserActivityView view, Context context) {
        this.view = view;
        this.dataSaveHelper = DataSaveHelper.getINSTANCE(context);
    }

    private void createNewUser(User user) {
        this.user = user;
    }

    public void logOut() {
        user = null;
    }

    public void UserGetFromDb(String key) {
        try {
            List users = dataSaveHelper.getAllCurrentObjects(User.class);
            Log.d("NAR", users.toString());
            for (Object o : users) {
                User us = (User) o;
                if (us.getId().equalsIgnoreCase(key)) {
                    view.setEmail(us.getEmail());
                    view.setName(us.getFullName());
                    view.setUserName(us.getUserName());
                    return;
                }
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface UserActivityView {

        void setUserName(String userName);

        void setName(String name);

        void setEmail(String email);
    }

}
