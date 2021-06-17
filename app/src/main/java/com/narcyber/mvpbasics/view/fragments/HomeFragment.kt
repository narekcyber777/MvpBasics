package com.narcyber.mvpbasics.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.narcyber.mvpbasics.R
import com.narcyber.mvpbasics.adapter.CityAdapter
import com.narcyber.mvpbasics.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var root: FragmentHomeBinding
    lateinit var cityAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        root = FragmentHomeBinding.inflate(inflater, container, false)
        return root.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeWidgets()
        inIt()
    }

    private fun customizeWidgets() {
        root.recycler.addItemDecoration(
            DividerItemDecoration(
                root.root.context, DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun inIt() {
        var citiesList: MutableList<String> = (resources.getStringArray(R.array.cities)
                as Array<String>).toMutableList()
        cityAdapter = CityAdapter(object : CityAdapter.CityAdapterCallBack {
            override fun onClick(id: Int) {
            }

            override fun onClick(city: String) {
                var navAction: HomeFragmentDirections.ActionHomeFragmentToWeatherActivity =
                    HomeFragmentDirections
                        .actionHomeFragmentToWeatherActivity()
                navAction.cityWeather = city
                findNavController().navigate(navAction)
            }
        })
        cityAdapter.submitList(citiesList)
        cityAdapter.createItemBackUp()
        root.recycler.adapter = cityAdapter
        root.searchWidget.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cityAdapter.filter.filter(newText)
                return true
            }
        })
    }
}