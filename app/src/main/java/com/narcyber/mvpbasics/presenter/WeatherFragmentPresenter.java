package com.narcyber.mvpbasics.presenter;

import com.narcyber.mvpbasics.remote.WeatherRepo;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class WeatherFragmentPresenter implements WeatherRepo.PresenterContract {

    private final WeatherView view;
    private final WeatherRepo weatherRepo;
    private final CompositeDisposable disposables;

    public WeatherFragmentPresenter(WeatherView view) {
        this.view = view;
        this.weatherRepo = new WeatherRepo();
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void onSubscribe() {
        view.onResetViewsVisibility();
    }

    @Override
    public void responseBack(String cityName) {
        view.onWeatherCelsiusUpdate(cityName);
    }

    @Override
    public void failedResponse(String error) {
        view.onWeatherResponseError(error);
    }

    @Override
    public void isFinished(boolean isFinished) {
        view.onFinishedObserving();
    }

    @Override
    public CompositeDisposable getDisposables() {
        return this.disposables;
    }

    public void fetchWeatherByCityName(final String city) {
        weatherRepo.getWeatherInfo(city, this);
    }

    public void destroyDisposable() {
        disposables.clear();
    }

    public interface WeatherView {
        void onResetViewsVisibility();

        void onWeatherCelsiusUpdate(String celsius);

        void onWeatherResponseError(String error);

        void onFinishedObserving();
    }
}



