package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.model.UserEmailPasswordStorage;

import java.util.List;

public class MainActivityPresenter {

    private static final String LOCAL = "Local";
    private final MainActivityView view;
    private final DataSaveHelper<User> dataSaveHelper;
    private final DataSaveHelper<UserEmailPasswordStorage> userEmailPasswordDataHelper;
    private User user;

    public MainActivityPresenter(MainActivityView view, Context context) {
        this.view = view;
        dataSaveHelper = new DataSaveHelper<>(context);
        userEmailPasswordDataHelper = new DataSaveHelper<>(context);
        newUserRequest();
    }

    public void newUserRequest() {
        this.user = new User();
    }

    public void setUserPassword(String password) {
        this.user.setPassword(password);
    }

    public void setUserEmail(String email) {
        this.user.setEmail(email);
    }

    public void findUserByEmailAndPassword() {
        if (this.user.getEmail() == null || this.user.getPassword() == null) {
            view.ifExistGetKey(null);
            return;
        }
        List<User> users = dataSaveHelper.getAllCurrentObjects(User.class);
        for (User user : users) {
            if (user.getEmail().trim().equalsIgnoreCase(this.user.getEmail().trim())
                    && user.getPassword().trim().equalsIgnoreCase(this.user.getPassword())) {
                view.ifExistGetKey(user.getId());
                return;
            }
        }
        view.ifExistGetKey(null);
    }
    //save values while checkbox is checked ;
    public void rememberPasswordAndEmail(final String email, final String password) {
        UserEmailPasswordStorage userEmailPasswordStorage = new UserEmailPasswordStorage(email, password);
        userEmailPasswordDataHelper.writeObject(LOCAL, userEmailPasswordStorage, UserEmailPasswordStorage.class);
    }


    public void sendPasswordAndEmailLastRegistered() {
        UserEmailPasswordStorage obj = userEmailPasswordDataHelper.readObject(LOCAL, UserEmailPasswordStorage.class);
        if (obj != null) {
            view.savedPasswordAndEmail(obj.getEmail(), obj.getPassword());
        }
    }

    public void removeLocal() {
        userEmailPasswordDataHelper.removeObject(LOCAL);
    }
    public interface MainActivityView {

        void ifExistGetKey(final String key);

        void savedPasswordAndEmail(final String email, String password);
    }
}
