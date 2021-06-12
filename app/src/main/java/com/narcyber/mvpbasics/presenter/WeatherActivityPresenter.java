package com.narcyber.mvpbasics.presenter;

import com.narcyber.mvpbasics.repo.WeatherRepo;

public class WeatherActivityPresenter implements WeatherRepo.PresenterContract {

    private final WeatherView view;
    private final WeatherRepo weatherRepo;

    public WeatherActivityPresenter(WeatherView view) {
        this.view = view;
        this.weatherRepo = new WeatherRepo();
    }

    public void requestForCityWeather(String city) {
        if (city != null && !city.isEmpty()) {
            weatherRepo.getWeatherInfo(city, this);
        }
    }

    @Override
    public void isFinished(String cityName) {
        view.setTempWithCelsius(cityName);
    }

    @Override
    public void failed() {
        view.notifyWeatherResponse();
    }

    public interface WeatherView {
        void setTempWithCelsius(String celsius);

        void notifyWeatherResponse();

    }
}
