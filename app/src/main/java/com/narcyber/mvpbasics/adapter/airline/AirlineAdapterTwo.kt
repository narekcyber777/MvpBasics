package com.narcyber.mvpbasics.adapter.airline

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.narcyber.mvpbasics.adapter.holder.AirlineAdapterViewHolder
import com.narcyber.mvpbasics.databinding.RowItemAirlinesBinding
import com.narcyber.mvpbasics.helper.airLineAdapterFilter
import com.narcyber.mvpbasics.model.AirlinesContent
import com.narcyber.mvpbasics.presenter.AirLineFragmentPresenter

class AirlineAdapterTwo : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AirLineFragmentPresenter.AirlineAdapterView, Filterable {
    var sizeOfPage: Int = 10
    var page: Int = 1
    override val allData: ArrayList<AirlinesContent> = ArrayList()
    override val backUpData: ArrayList<AirlinesContent> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AirlineAdapterViewHolder(
            RowItemAirlinesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AirlineAdapterViewHolder).root.nameText.text = allData[position].name
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun getFilter(): Filter {
        return airLineAdapterFilter(this, allData, backUpData)
    }

    override fun notifyDataIsAdded(where: Int) {
        notifyItemInserted(where)

    }

    override fun notifyDataMoved(from: Int, where: Int) {
        notifyItemMoved(from, where)

    }

    override fun notifyItemCountChanged(from: Int, size: Int) {
        notifyItemRangeChanged(from, size)

    }

    override fun notifyItemsPush(from: Int, size: Int) {
        notifyItemRangeInserted(from, size)
    }

    override fun notifyAllChanges() {
        notifyDataSetChanged()
    }

    override fun hideProgress() {

    }

    override fun showProgress() {

    }


}