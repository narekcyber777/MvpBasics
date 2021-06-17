package com.narcyber.mvpbasics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.narcyber.mvpbasics.databinding.RowItemCityBinding
import com.narcyber.mvpbasics.helper.ListAdapterHelper
import java.util.*

class CityAdapter(var cityAdapterCallBack: CityAdapterCallBack?) :
    ListAdapter<String, CityAdapter.CityAdapterViewHolder>(ListAdapterHelper.StringDiffUtil),
    Filterable {
    private lateinit var allList: MutableList<String>
    fun createItemBackUp() {
        allList = currentList
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

    class CityAdapterViewHolder(r: RowItemCityBinding) : RecyclerView.ViewHolder(r.root) {
        internal val root: RowItemCityBinding = r
    }

    interface CityAdapterCallBack {
        fun onClick(id: Int)
        fun onClick(city: String)
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(c: CharSequence?): FilterResults {
            val filteredList: MutableList<String> = mutableListOf()
            if (c == null || c.isEmpty()) {
                filteredList.addAll(allList)
            } else {
                allList.forEach { a ->
                    run {
                        val b: Boolean = a.lowercase(Locale.getDefault())
                            .contains(c.toString().lowercase(Locale.getDefault()))
                        if (b) filteredList.add(a)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            var list = results?.values as MutableList<String>
            submitList(list)
        }
    }


}