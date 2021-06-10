package com.narcyber.mvpbasics.presenter;

import android.content.Context;

import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.helper.DataSaveHelper;
import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.model.UserEmailPasswordStorage;

import java.util.List;

public class MainActivityPresenter {

    ;
    private final MainActivityView view;
    private final DataSaveHelper<User> dataSaveHelper;
    private final DataSaveHelper<UserEmailPasswordStorage> userEmailPasswordDataHelper;


    public MainActivityPresenter(MainActivityView view, Context context) {
        this.view = view;
        dataSaveHelper = new DataSaveHelper<>(context);
        userEmailPasswordDataHelper = new DataSaveHelper<>(context);
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
        UserEmailPasswordStorage userEmailPasswordStorage = new UserEmailPasswordStorage(email, password);
        userEmailPasswordDataHelper.writeObject(ConstantHelper.LOCAL, userEmailPasswordStorage, UserEmailPasswordStorage.class);
    }


    public void sendPasswordAndEmailLastRegistered() {
        UserEmailPasswordStorage obj = userEmailPasswordDataHelper.readObject(ConstantHelper.LOCAL, UserEmailPasswordStorage.class);
        if (obj != null) {
            view.savedPasswordAndEmail(obj.getEmail(), obj.getPassword());

        }
    }


    public void removeLocal() {
        userEmailPasswordDataHelper.removeObject(ConstantHelper.LOCAL);
    }
    public interface MainActivityView {

        void ifExistGetKey(final String key);

        void savedPasswordAndEmail(final String email, String password);


    }
}
