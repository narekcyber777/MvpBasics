package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class UserActivityPresenter {
    private final UserActivityView view;
    private final DataSaveHelper<User> dataSaveHelper;

    public UserActivityPresenter(UserActivityView view, Context context) {
        this.view = view;
        this.dataSaveHelper = new DataSaveHelper<>(context);
    }

    public void userGetAndUpdateView(String key) {
        List<User> users = dataSaveHelper.getAllCurrentObjects(User.class);
        for (User us : users) {
            if (us.getId().equalsIgnoreCase(key)) {
                view.setEmail(us.getEmail());
                view.setName(us.getFullName());
                view.setUserName(us.getUserName());
                return;
            }
        }
    }
    public interface UserActivityView {

        void setUserName(String userName);

        void setName(String name);

        void setEmail(String email);
    }

}
