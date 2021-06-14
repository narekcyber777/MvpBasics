package com.narcyber.mvpbasics.repository;

import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.model.Weather;
import com.narcyber.mvpbasics.network.ApiWeatherInterface;
import com.narcyber.mvpbasics.network.WeatherApiClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeatherRepo {
    public void getWeatherInfo(String cityName, PresenterContract presentable) {
        ApiWeatherInterface wClient = WeatherApiClient.getClient().create(ApiWeatherInterface.class);
        Observable<Weather> call = wClient.getWeatherData(ConstantHelper.WEATHER_API_KEY, cityName.trim());
        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        presentable.getDisposables().add(d);
                        presentable.onSubscribe();
                    }

                    @Override
                    public void onNext(@NonNull Weather weather) {
                        String celsius = weather.getMain().getTemp();
                        presentable.responseBack(celsius);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        presentable.failedResponse(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        presentable.isFinished(true);
                    }
                });
    }

    public interface PresenterContract {
        void onSubscribe();

        void responseBack(String celsius);

        void failedResponse(String error);

        void isFinished(boolean isFinished);


        CompositeDisposable getDisposables();
    }

}
