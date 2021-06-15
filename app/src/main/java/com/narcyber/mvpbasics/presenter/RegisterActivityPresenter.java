package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

public class RegisterActivityPresenter extends ParentPresenter {

    private final RegisterView view;
    private final DataSaveHelper<User> dataSaveHelper;

    public RegisterActivityPresenter(RegisterView view, Context context) {
        super();
        this.view = view;
        dataSaveHelper = new DataSaveHelper<User>(context);
    }

    public boolean isUsernameTaken(@NonNull String username) {

        getUserRepository().requestUserByUsername(username);

        return true;
    }

    @Override
    public void respondUserByUserName(User user) {
        if (user != null) {
            view.isUsernameUsed(true);
            return;
        }
        view.isUsernameUsed(false);
    }


    public void removeUser(String key) {
        dataSaveHelper.removeObject(key);
    }

    public boolean isEmailTaken(@NonNull String email) {
        getUserRepository().requestUserByEmail(email);
        return true;
    }

    @Override
    public void respondUserByEmail(User user) {
        if (user != null) {
            view.isEmailUsed(true);
            return;
        }
        view.isEmailUsed(false);
    }

    public void pushUserIntoDb(final String userEmail, final String userFullName,
                               final String userPassword, final String userName) {
        final User user = new User(userEmail, userFullName, userName, userPassword);
        getUserRepository().requestUserPush(user);
    }

    @Override
    public void respondSuccessPushing() {
        view.notifyUserSuccessRegistered();
    }

    @Override
    public void respondPushingFailed(String message) {
        view.notifyUserRegFailed();
    }

    public interface RegisterView {

        boolean isEmailUsed(boolean isUsed);

        boolean isUsernameUsed(boolean isUsed);

        boolean notifyUserSuccessRegistered();

        boolean notifyUserRegFailed();
    }

}
