package com.narcyber.mvpbasics.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.List;

public class RegisterActivityPresenter implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String TAG = "REG_PRESENTER";
    private final RegisterView view;
    private final DataSaveHelper dataSaveHelper;
    private User user;


    public RegisterActivityPresenter(RegisterView view, Context applicationContext) {
        this.view = view;
        this.user = new User();
        dataSaveHelper = DataSaveHelper.getINSTANCE(applicationContext);
    }


    public boolean isUsernameTaken(@NonNull String username) {
        List<Object> users = dataSaveHelper.getAllCurrentObjects(User.class);


        for (Object o : users) {
            User user = (User) o;

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
        List<Object> users = dataSaveHelper.getAllCurrentObjects(User.class);

        for (Object o : users) {
            User user = (User) o;
            if (user == null) continue;

            System.out.println(user.toString() + " ssss s ");
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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, key);
    }

    public interface RegisterView {

        boolean isEmailUsed(boolean isUsed);

        boolean isUsernameUsed(boolean isUsed);


    }
}
