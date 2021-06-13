package com.narcyber.mvpbasics.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.adapter.CityAdapter;
import com.narcyber.mvpbasics.databinding.FragmentHomeBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.presenter.HomeActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding root;
    private HomeActivityPresenter homeActivityPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = FragmentHomeBinding.inflate(inflater, container, false);
        return root.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customizeWidgets();
        inIt();
    }

    private void customizeWidgets() {
        root.recycler.addItemDecoration(new DividerItemDecoration(
                root.getRoot().getContext(), DividerItemDecoration.VERTICAL));
    }

    public void inIt() {
        homeActivityPresenter = new
                HomeActivityPresenter((HomeActivityPresenter.HomeViewActivity) getActivity(), getActivity());
        final List<String> cities = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.cities)));
        CityAdapter cityAdapter = new CityAdapter(new CityAdapter.CityAdapterCallBack() {
            @Override
            public void onClick(int id) {
            }

            @Override
            public void onClick(String city) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantHelper.KEY_WEATHER_CELSIUS, city);
                try {
                    homeActivityPresenter.inflateWeatherActivity(bundle);
                } catch (NullPointerException e) {
                }
            }
        });
        cityAdapter.submitList(cities);
        cityAdapter.createItemsBackUp();
        root.recycler.setAdapter(cityAdapter);
        root.searchWidget.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cityAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }




}
