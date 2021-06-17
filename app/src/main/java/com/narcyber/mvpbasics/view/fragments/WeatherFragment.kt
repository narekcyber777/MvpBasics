package com.narcyber.mvpbasics.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.narcyber.mvpbasics.databinding.FragmentWeatherBinding
import com.narcyber.mvpbasics.presenter.WeatherFragmentPresenter
import com.narcyber.mvpbasics.utils.showInToast

class WeatherFragment : Fragment(), WeatherFragmentPresenter.WeatherView {
    lateinit var root: FragmentWeatherBinding
    lateinit var weatherFragmentPres: WeatherFragmentPresenter
    val safeWeatherArgs: WeatherFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        root = FragmentWeatherBinding.inflate(inflater, container, false)
        return root.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inIt()
    }

    private fun inIt() {
        weatherFragmentPres = WeatherFragmentPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        weatherFragmentPres.fetchWeatherByCityName(safeWeatherArgs.cityWeather)
    }

    override fun onResetViewsVisibility() {
        root.progressBar.visibility = View.VISIBLE
        root.cityName.visibility = View.GONE
        root.groupId.visibility = View.GONE
    }

    override fun onWeatherCelsiusUpdate(celsius: String?) {
        root.temp.text = celsius
    }

    override fun onWeatherResponseError(error: String?) {
        showInToast(requireContext(), error!!)
        requireActivity().onBackPressed()
    }

    override fun onFinishedObserving() {
        root.progressBar.visibility = View.GONE
        root.cityName.visibility = View.VISIBLE
        root.groupId.visibility = View.VISIBLE
    }


}