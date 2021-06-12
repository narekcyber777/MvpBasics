package com.narcyber.mvpbasics.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivityHomeBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.presenter.HomeActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;
import com.narcyber.mvpbasics.view.fragments.HomeFragment;
import com.narcyber.mvpbasics.view.fragments.UserPersonalFragment;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener,
        HomeActivityPresenter.HomeViewActivity {
    public static Map<String, String> userMap;
    private ActivityHomeBinding root;
    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener;
    private HomeActivityPresenter homeActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        inIt();
    }

    private void inIt() {
        homeActivityPresenter = new HomeActivityPresenter(this, this);
        try {
            homeActivityPresenter.userGetAndUpdateView(getIntent().getStringExtra(ConstantHelper.KEY_ID));
        } catch (NullPointerException ignored) {
        }
        nav_listener = getNavListener();
        root.navBarBottom.setOnNavigationItemSelectedListener(nav_listener);
        inflateDefaultFragment();
    }

    public void attachFragment(@NonNull final Fragment fragment, final String TAG, int Where, boolean haveBackStack) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (haveBackStack) {
            ft.addToBackStack(TAG);
        } else {
            ft.addToBackStack(null);
        }
        ft.replace(Where, fragment, TAG);
        ft.commit();
    }

    public void removeLocal() {
        homeActivityPresenter.removeLocal();
    }

    private void inflateDefaultFragment() {
        HomeFragment homeFragment = new HomeFragment();
        attachFragment(homeFragment, getString(R.string.personal_fragment), R.id.frame, true);
    }

    @Override
    public void inflateWeatherActivity(Bundle bundle) {
        MyUtils.withArgumentsMoveTo(bundle, this, WeatherActivity.class);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener getNavListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    inflateDefaultFragment();

                    break;
                case R.id.personal:
                    UserPersonalFragment userPersonalFragment = new UserPersonalFragment();
                    attachFragment(userPersonalFragment,
                            getString(R.string.personal_fragment)
                            , R.id.frame, true);


                    break;
            }
            return true;
        };
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
