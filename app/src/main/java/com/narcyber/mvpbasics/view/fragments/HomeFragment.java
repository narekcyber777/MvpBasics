package com.narcyber.mvpbasics.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.adapter.CityAdapter;
import com.narcyber.mvpbasics.databinding.FragmentHomeBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    public HomeFragmentListener homeFragmentListener;
    private FragmentHomeBinding root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = FragmentHomeBinding.inflate(inflater, container, false);
        customizeWidgets();
        inIt();
        return root.getRoot();
    }

    private void customizeWidgets() {
        root.recycler.addItemDecoration(new DividerItemDecoration(
                root.getRoot().getContext(), DividerItemDecoration.VERTICAL));

    }

    public void inIt() {
        final List<String> cities = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.cities)));

        CityAdapter cityAdapter = new CityAdapter(new CityAdapter.CityAdapterCallBack() {
            @Override
            public void onClick(int id) {
            }

            @Override
            public void onClick(String city) {
                if (homeFragmentListener != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantHelper.KEY_WEATHER_CELSIUS, city);
                    homeFragmentListener.inflateWeatherActivity(bundle);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof HomeFragmentListener) {
            homeFragmentListener = (HomeFragmentListener) getActivity();
        } else {
            throw new ClassCastException("This class is not child of HomeFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (homeFragmentListener != null) homeFragmentListener = null;
    }

    public interface HomeFragmentListener {
        void inflateWeatherActivity(Bundle bundle);
    }


}
