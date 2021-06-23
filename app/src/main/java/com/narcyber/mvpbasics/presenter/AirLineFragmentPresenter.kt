package com.narcyber.mvpbasics.presenter

import com.narcyber.mvpbasics.adapter.airline.AirlineAdapter
import com.narcyber.mvpbasics.helper.emptyLoaderObject
import com.narcyber.mvpbasics.model.AirlinesContent
import com.narcyber.mvpbasics.remote.AirlinesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AirLineFragmentPresenter(
    val view: AirlineFragmentView,
    private val adapterView: AirlineAdapterView
) :
    AirlinesRepository.PresenterContract {
    private val airlinesRepository: AirlinesRepository by lazy {
        AirlinesRepository(this)
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    override val disposables: CompositeDisposable
        get() = compositeDisposable

    fun requestForAirlines(page: Int, size: Int, delay: Int) {
        airlinesRepository.getAirlineInfo(page, size)
    }

    fun clearDisposables() {
        compositeDisposable.clear()
    }

    fun insertIntoAdapter(list: ArrayList<AirlinesContent>) {
        if (adapterView is AirlineAdapter) {
            val index = adapterView.allData.indexOf(emptyLoaderObject)
            if (index == 0) {
                adapterView.allData.addAll(0, list)
                adapterView.notifyItemCountChanged(0, adapterView.allData.size)
            } else {
                adapterView.allData.addAll(adapterView.allData.lastIndex, list)
                adapterView.notifyItemCountChanged(index, adapterView.allData.size)
            }
        } else {
            val lastPos = adapterView.allData.lastIndex
            adapterView.allData.addAll(list)
            adapterView.notifyItemCountChanged(lastPos, adapterView.allData.size)
        }
        adapterBackUp()
    }

    private fun adapterBackUp() {
        adapterView.backUpData.clear()
        adapterView.backUpData.addAll(adapterView.allData)
    }

    override fun respondAirlines(airlines: MutableList<AirlinesContent>) {
        if (airlines.isEmpty()) {
            adapterView.hideProgress()
        } else {
            adapterView.showProgress()
            view.takeAirlineContent(airlines)
        }
    }

    override fun respondError(error: String) {

        view.error(error)
    }

    interface AirlineFragmentView {
        fun takeAirlineContent(list: MutableList<AirlinesContent>)
        fun error(error: String)
    }

    interface AirlineAdapterView {
        fun notifyDataIsAdded(where: Int)
        fun notifyDataMoved(from: Int, size: Int)
        fun notifyItemCountChanged(from: Int, size: Int)
        fun notifyItemsPush(from: Int, size: Int)
        fun notifyAllChanges()
        fun hideProgress()
        fun showProgress()
        val allData: ArrayList<AirlinesContent>
        val backUpData: ArrayList<AirlinesContent>

    }
}