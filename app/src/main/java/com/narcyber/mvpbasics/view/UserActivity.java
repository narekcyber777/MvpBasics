package com.narcyber.mvpbasics.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.narcyber.mvpbasics.databinding.ActivityUserBinding;
import com.narcyber.mvpbasics.presenter.UserActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;

public class UserActivity extends AppCompatActivity implements UserActivityPresenter.UserActivityView {

    //root
    private ActivityUserBinding root;
    private UserActivityPresenter userActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        inIt();
    }

    private void inIt() {
        final String id = String.valueOf(getIntent().getStringExtra(MyUtils.KEY_ID));
        userActivityPresenter = new UserActivityPresenter(this, getApplicationContext());
        userActivityPresenter.UserGetFromDb(id);
    }

    @Override
    public void setUserName(String userName) {
        root.login.setText(userName);
    }

    @Override
    public void setName(String name) {
        root.name.setText(name);
    }

    @Override
    public void setEmail(String email) {
        root.email.setText(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyUtils.moveToAndClear(this, MainActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userActivityPresenter = null;
    }
}
