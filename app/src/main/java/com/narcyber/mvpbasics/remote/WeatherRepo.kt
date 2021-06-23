package com.narcyber.mvpbasics.remote

import com.narcyber.mvpbasics.helper.ConstantHelper
import com.narcyber.mvpbasics.model.Weather
import com.narcyber.mvpbasics.network.ApiWeatherInterface
import com.narcyber.mvpbasics.network.WeatherApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherRepo {
    fun getWeatherInfo(cityName: String = "", presenter: PresenterContract) {
        val api: ApiWeatherInterface =
            WeatherApiClient.weatherClient.create(ApiWeatherInterface::class.java)
        val observable: Observable<Weather> =
            api.getWeatherData(ConstantHelper.WEATHER_API_KEY, cityName.toString())
        observable.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Weather?> {
                override fun onSubscribe(d: @NonNull Disposable?) {
                    presenter.disposables.add(d)
                    presenter.onSubscribe()
                }

                override fun onNext(weather: @NonNull Weather?) {
                    val celsius = weather!!.main.temp
                    presenter.responseBack(celsius)
                }

                override fun onError(e: @NonNull Throwable?) {
                    presenter.failedResponse(e!!.message)
                }

                override fun onComplete() {
                    presenter.isFinished(true)
                }
            })
    }

    interface PresenterContract {
        fun onSubscribe()
        fun responseBack(celsius: String?)
        fun failedResponse(error: String?)
        fun isFinished(isFinished: Boolean)
        val disposables: CompositeDisposable
    }

}