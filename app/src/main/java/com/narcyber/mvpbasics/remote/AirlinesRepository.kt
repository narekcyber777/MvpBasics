package com.narcyber.mvpbasics.remote

import android.util.Log
import com.narcyber.mvpbasics.model.AirlinesContainer
import com.narcyber.mvpbasics.model.AirlinesContent
import com.narcyber.mvpbasics.network.AirlinesApiClient
import com.narcyber.mvpbasics.network.ApiAirlinesInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AirlinesRepository(val presenter: PresenterContract) {

    fun getAirlineInfo(page: Int, size: Int) {
        val api = AirlinesApiClient.client.create(ApiAirlinesInterface::class.java)
        api.getAirlineData(page, size).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<AirlinesContainer> {
                override fun onSubscribe(d: Disposable) {
                    presenter.disposables.add(d)
                }

                override fun onSuccess(t: AirlinesContainer) {
                    presenter.respondAirlines(t.data)
                }

                override fun onError(e: Throwable?) {
                    Log.d("NAR", e.toString())
                    presenter.respondError(e.toString())

                }

            })

    }


    interface PresenterContract {
        val disposables: CompositeDisposable
        fun respondAirlines(airlines: MutableList<AirlinesContent>)
        fun respondError(error: String)
    }
}