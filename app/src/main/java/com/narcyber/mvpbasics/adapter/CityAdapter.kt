package com.narcyber.mvpbasics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.narcyber.mvpbasics.adapter.holder.CityAdapterViewHolder
import com.narcyber.mvpbasics.databinding.RowItemCityBinding
import com.narcyber.mvpbasics.helper.StringDiffUtil
import com.narcyber.mvpbasics.helper.cityAdapterFilter

class CityAdapter(private var cityAdapterCallBack: CityAdapterCallBack?) :
    ListAdapter<String, CityAdapterViewHolder>(StringDiffUtil),
    Filterable {
    private var allList: ArrayList<String> = ArrayList()
    fun createItemBackUp() {
        allList.addAll(currentList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapterViewHolder {
        val rowItemCityBinding = RowItemCityBinding
            .inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return CityAdapterViewHolder(rowItemCityBinding)
    }

    override fun onBindViewHolder(holder: CityAdapterViewHolder, position: Int) {
        holder.root.cardViewItem.text = getItem(position)
        holder.root.cardViewItem.setOnClickListener {
            cityAdapterCallBack?.onClick(holder.root.cardViewItem.text.toString())
        }
    }

    interface CityAdapterCallBack {
        fun onClick(id: Int)
        fun onClick(city: String)
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = cityAdapterFilter(this, allList)


}