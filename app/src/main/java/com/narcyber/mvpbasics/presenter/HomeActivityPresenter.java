package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class HomeActivityPresenter {
    private final HomeViewActivity view;
    private final DataSaveHelper<User> dataSaveHelper;

    public HomeActivityPresenter(HomeViewActivity view, Context context) {
        this.view = view;
        dataSaveHelper = new DataSaveHelper<>(context);
    }

    public void userGetAndUpdateView(String key) {
        List<User> users = dataSaveHelper.getAllCurrentObjects(User.class);
        for (User us : users) {
            if (us.getId().equalsIgnoreCase(key)) {
                view.userProperties(us.getEmail(), us.getUserName(), us.getFullName());
                return;
            }
        }
    }

    public void removeLocal() {
        dataSaveHelper.removeObject(ConstantHelper.LOCAL);
    }

    public interface HomeViewActivity {
        void userProperties(String email, String username, String fullName);
    }

}
