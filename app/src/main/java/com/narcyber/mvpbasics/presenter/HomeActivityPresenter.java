package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

public class HomeActivityPresenter extends ParentPresenter {
    private final HomeViewActivity view;
    private final DataSaveHelper<User> dataSaveHelper;

    public HomeActivityPresenter(HomeViewActivity view, Context context) {
        this.view = view;
        dataSaveHelper = new DataSaveHelper<>(context);
    }

    public void userGetAndUpdateView(String key) {
        getUserRepository().requestUserByUsername(key);
    }

    @Override
    public void respondUserByUserName(User user) {
        if (user == null) {
            view.networkError();
        } else {
            view.userProperties(user.getEmail(), user.getUserName(), user.getFullName());
        }
    }

    public void removeLocal() {
        dataSaveHelper.removeObject(ConstantHelper.LOCAL_PASSWORD);
        dataSaveHelper.removeObject(ConstantHelper.LOCAL_EMAIL);
    }

    public interface HomeViewActivity {
        void userProperties(String email, String username, String fullName);

        void networkError();
    }

}
