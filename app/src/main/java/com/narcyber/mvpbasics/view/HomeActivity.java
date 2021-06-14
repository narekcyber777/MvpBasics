package com.narcyber.mvpbasics.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivityHomeBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.presenter.HomeActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements
        HomeActivityPresenter.HomeViewActivity {
    public static Map<String, String> userMap;
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

        try {
            homeActivityPresenter.userGetAndUpdateView(getIntent().getStringExtra(ConstantHelper.KEY_ID));
        } catch (NullPointerException ignored) {
        }
        setUpNavigation();
    }

    public void removeLocal() {
        homeActivityPresenter.removeLocal();
    }

    public void inflateWeatherActivity(Bundle bundle) {
        MyUtils.withArgumentsMoveTo(bundle, this, WeatherActivity.class);
    }

    @Override
    public void userProperties(String email, String username, String fullName) {
        if (userMap == null) {
            userMap = new HashMap<>();
        }
        userMap.put(ConstantHelper.KEY_EMAIL, email);
        userMap.put(ConstantHelper.KEY_Full_Name, fullName);
        userMap.put(ConstantHelper.KEY_USERNAME, username);
    }

}
