package com.narcyber.mvpbasics.repo;

import com.narcyber.mvpbasics.model.Weather;
import com.narcyber.mvpbasics.network.ApiWeatherInterface;
import com.narcyber.mvpbasics.network.WeatherApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepo {


    public void getWeatherInfo(String cityName, PresenterContract presentable) {
        ApiWeatherInterface wClient = WeatherApiClient.getClient().create(ApiWeatherInterface.class);
        Call<Weather> call = wClient.getWeatherData(cityName.trim());
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                String celsius = response.body().getMain().getTemp();
                presentable.isFinished(celsius);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                presentable.failed();
            }
        });
    }

    public interface PresenterContract {

        void isFinished(String cityName);

        void failed();
    }

}
