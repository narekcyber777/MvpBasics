package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class RegisterActivityPresenter {

    private final String TAG = "REG_PRESENTER";
    private final RegisterView view;
    private final DataSaveHelper<User> dataSaveHelper;
    private User user;

    public RegisterActivityPresenter(RegisterView view, Context context) {
        this.view = view;
        this.user = new User();
        dataSaveHelper = new DataSaveHelper<User>(context);
    }

    public boolean isUsernameTaken(@NonNull String username) {
        List<User> users = dataSaveHelper.getAllCurrentObjects(User.class);
        for (User user : users) {
            if (user.getUserName().trim().equalsIgnoreCase(username.trim())) {
                view.isUsernameUsed(true);
                return true;
            }
        }
        view.isUsernameUsed(false);
        return false;
    }

    public void removeUser(String key) {
        dataSaveHelper.removeObject(key);
    }

    public boolean isEmailTaken(@NonNull String email) {
        List<User> users = dataSaveHelper.getAllCurrentObjects(User.class);
        for (User user : users) {
            if (user == null) continue;
            if (user.getEmail().trim().equalsIgnoreCase(email.trim())) {
                view.isEmailUsed(true);
                return true;
            }
        }
        view.isEmailUsed(false);
        return false;
    }

    public void setUserFullName(String fullName) {
        user.setFullName(fullName);
    }

    public void setUsersUserName(String userName) {
        user.setUserName(userName);
    }

    public void setUserPassword(String password) {
        user.setPassword(password);
    }

    public void setUserEmail(String userEmail) {
        user.setEmail(userEmail);
    }

    public void createNewUser() {
        this.user = new User();
    }

    public void pushUserIntoDb() {
        dataSaveHelper.writeObject(user.getId(), user, User.class);
        createNewUser();
    }

    public RegisterActivityPresenter setUser(User user) {
        this.user = user;
        return this;
    }

    public interface RegisterView {

        boolean isEmailUsed(boolean isUsed);

        boolean isUsernameUsed(boolean isUsed);
    }

}
