package com.narcyber.mvpbasics.presenter

import com.narcyber.mvpbasics.remote.WeatherRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class WeatherFragmentPresenter(val view: WeatherView) : WeatherRepo.PresenterContract {
    private val weatherRepo: WeatherRepo by lazy {
        WeatherRepo()
    }
    private val cd: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onSubscribe() {
    }

    override fun responseBack(celsius: String?) {
        view.onWeatherCelsiusUpdate(celsius)
    }

    override fun failedResponse(error: String?) {
        view.onWeatherResponseError(error)
    }

    override fun isFinished(isFinished: Boolean) {
        view.onFinishedObserving()
    }

    override val disposables: CompositeDisposable
        get() = cd

    fun fetchWeatherByCityName(city: String?) {
        weatherRepo.getWeatherInfo(city!!, this)
    }
    fun destroyDisposable() {
        disposables.clear()
    }
    interface WeatherView {
        fun onResetViewsVisibility()
        fun onWeatherCelsiusUpdate(celsius: String?)
        fun onWeatherResponseError(error: String?)
        fun onFinishedObserving()
    }

}