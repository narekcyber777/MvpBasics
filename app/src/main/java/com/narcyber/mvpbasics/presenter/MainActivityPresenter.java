package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class MainActivityPresenter {
    private final MainActivityView view;
    private final DataSaveHelper<User> dataSaveHelper;
    public MainActivityPresenter(MainActivityView view, Context context) {
        this.view = view;
        dataSaveHelper = new DataSaveHelper<>(context);
    }

    public void findUserByEmailAndPassword(final String email, final String password) {
        List<User> users = dataSaveHelper.getAllCurrentObjects(User.class);
        for (User user : users) {
            if (user.getEmail().trim().equalsIgnoreCase(email.trim())
                    && user.getPassword().trim().equalsIgnoreCase(password.trim())) {
                view.ifExistGetKey(user.getId());
                return;
            }
        }
        view.ifExistGetKey(null);
    }

    public void rememberPasswordAndEmail(final String email, final String password) {
        dataSaveHelper.writeString(ConstantHelper.LOCAL_EMAIL, email);
        dataSaveHelper.writeString(ConstantHelper.LOCAL_PASSWORD, password);
    }

    public void sendPasswordAndEmailLastRegistered() {
        final String password = dataSaveHelper.getString(ConstantHelper.LOCAL_PASSWORD);
        final String login = dataSaveHelper.getString(ConstantHelper.LOCAL_EMAIL);
        if (!password.isEmpty() && !login.isEmpty()) {
            view.savedPasswordAndEmail(login, password);
        }

    }

    public void removeLocal() {
        dataSaveHelper.removeObject(ConstantHelper.LOCAL_PASSWORD);
        dataSaveHelper.removeObject(ConstantHelper.LOCAL_EMAIL);
    }

    public void clearData() {
        dataSaveHelper.clear();
    }

    public interface MainActivityView {

        void ifExistGetKey(final String key);

        void savedPasswordAndEmail(final String email, String password);

    }
}
