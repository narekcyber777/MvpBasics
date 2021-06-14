package com.narcyber.mvpbasics.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivityWeatherBinding;

public class WeatherActivity extends AppCompatActivity {
    private ActivityWeatherBinding root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        setNav();
    }

    private void setNav() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.setGraph(R.navigation.nav_graph_weather, getIntent().getExtras());
    }

}

