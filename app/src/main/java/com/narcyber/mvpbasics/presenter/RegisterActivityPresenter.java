package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class RegisterActivityPresenter {

    private final RegisterView view;
    private final DataSaveHelper<User> dataSaveHelper;

    public RegisterActivityPresenter(RegisterView view, Context context) {
        this.view = view;
        dataSaveHelper = new DataSaveHelper<User>(context);
    }

    public boolean isUsernameTaken(@NonNull String username) {
        List<User> users = dataSaveHelper.getAllCurrentObjects(User.class);
        for (User user : users) {
            if (user != null) {
                if (user.getUserName().trim().equalsIgnoreCase(username.trim())) {
                    view.isUsernameUsed(true);
                    return true;
                }
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
            if (user == null) {
                continue;
            }
            if (user.getEmail().trim().equalsIgnoreCase(email.trim())) {
                view.isEmailUsed(true);
                return true;
            }
        }
        view.isEmailUsed(false);
        return false;
    }

    public void pushUserIntoDb(final String userEmail, final String userFullName,
                               final String userPassword, final String userName) {
        final User user = new User(userEmail, userFullName, userName, userPassword);
        dataSaveHelper.writeObject(user.getId(), user, User.class);
    }

    public interface RegisterView {

        boolean isEmailUsed(boolean isUsed);

        boolean isUsernameUsed(boolean isUsed);
    }

}
