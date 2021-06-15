package com.narcyber.mvpbasics.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivityHomeBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.presenter.HomeActivityPresenter;

public class HomeActivity extends AppCompatActivity implements
        HomeActivityPresenter.HomeViewActivity {
    public static User user;
    private ActivityHomeBinding root;
    private HomeActivityPresenter homeActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        inIt();

    }
    private void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(root.navBarBottom, navController);

    }

    private void inIt() {
        homeActivityPresenter = new HomeActivityPresenter(this, this);
        if (getIntent().hasExtra(ConstantHelper.KEY_ID)) {
            user = (User) getIntent().getSerializableExtra(ConstantHelper.KEY_ID);
            setUpNavigation();
        }


    }

    public void removeLocal() {
        homeActivityPresenter.removeLocal();
    }






}
