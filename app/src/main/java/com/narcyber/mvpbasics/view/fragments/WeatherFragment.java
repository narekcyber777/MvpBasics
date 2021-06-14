package com.narcyber.mvpbasics.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.narcyber.mvpbasics.databinding.FragmentWeatherBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.presenter.WeatherFragmentPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;

public class WeatherFragment extends Fragment implements WeatherFragmentPresenter.WeatherView {
    private FragmentWeatherBinding root;
    private WeatherFragmentPresenter weatherFragmentPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = FragmentWeatherBinding.inflate(inflater, container, false);
        return root.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherFragmentPresenter = new WeatherFragmentPresenter(this);
        if (getArguments() != null && getArguments().getString(ConstantHelper.KEY_WEATHER_CELSIUS) != null) {
            root.cityName.setText(getArguments().getString(ConstantHelper.KEY_WEATHER_CELSIUS));
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (root.cityName.getText() != null && !root.cityName.getText().toString().isEmpty()) {
            weatherFragmentPresenter.fetchWeatherByCityName(root.cityName.getText().toString());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        weatherFragmentPresenter.destroyDisposable();
    }

    @Override
    public void onResetViewsVisibility() {
        root.progressBar.setVisibility(View.VISIBLE);
        root.cityName.setVisibility(View.GONE);
        root.groupId.setVisibility(View.GONE);
    }

    @Override
    public void onWeatherCelsiusUpdate(String celsius) {
        root.temp.setText(celsius);
    }

    @Override
    public void onWeatherResponseError(String error) {
        MyUtils.showInToast(getContext(), error);
        requireActivity().onBackPressed();
    }

    @Override
    public void onFinishedObserving() {
        root.progressBar.setVisibility(View.GONE);
        root.cityName.setVisibility(View.VISIBLE);
        root.groupId.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        weatherFragmentPresenter = null;
    }

}
