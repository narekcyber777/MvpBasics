package com.narcyber.mvpbasics.presenter;

import android.content.Context;
import android.util.Log;

import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.repository.UserRepository;

import java.util.List;

public class MainActivityPresenter extends ParentPresenter implements UserRepository.PresenterContract {
    private final MainActivityView view;
    private final DataSaveHelper<User> dataSaveHelper;

    public MainActivityPresenter(MainActivityView view, Context context) {
        super();
        this.view = view;
        dataSaveHelper = new DataSaveHelper<>(context);
        getUserRepository().requestForAllUsers();
    }

    public void findUserByEmailAndPassword(final String email, final String password) {
        getUserRepository().requestUserByEmailAndPassword(email, password);
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

    @Override
    public void respondUserByEmailAndPassword(User user) {
        if (user == null) {
            view.ifExistGetUsername(null);
        } else {
            view.ifExistGetUsername(user.getUserName());
        }
    }

    @Override
    public void respondForAllUsers(List<User> users) {
        Log.d("NAR", users.toString());
    }

    public interface MainActivityView {

        void ifExistGetUsername(final String key);

        void savedPasswordAndEmail(final String email, String password);

    }
}
