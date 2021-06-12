package com.narcyber.mvpbasics.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivityWeatherBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.presenter.WeatherActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;

public class WeatherActivity extends AppCompatActivity implements WeatherActivityPresenter.WeatherView {
    private ActivityWeatherBinding root;
    private WeatherActivityPresenter weatherActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        inIt();
    }

    private void inIt() {
        final String city = getIntent().getStringExtra(ConstantHelper.KEY_WEATHER_CELSIUS);
        weatherActivityPresenter = new WeatherActivityPresenter(this);
        if (city != null && !city.isEmpty()) {
            root.cityName.setText(city);
            weatherActivityPresenter.requestForCityWeather(city);
        }
    }

    @Override
    public void setTempWithCelsius(String celsius) {
        root.temp.setText(celsius);
    }

    @Override
    public void notifyWeatherResponse() {
        MyUtils.showInToast(this, getString(R.string.error_request_weather));
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherActivityPresenter = null;
    }
}
