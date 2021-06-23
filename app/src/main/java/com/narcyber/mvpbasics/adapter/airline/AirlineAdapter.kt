package com.narcyber.mvpbasics.adapter.airline

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.narcyber.mvpbasics.adapter.holder.AirlineAdapterViewHolder
import com.narcyber.mvpbasics.adapter.holder.AirlineAdapterViewHolderEmpty
import com.narcyber.mvpbasics.databinding.RowItemAirlinesBinding
import com.narcyber.mvpbasics.databinding.RowLoadingBinding
import com.narcyber.mvpbasics.helper.airLineAdapterFilter
import com.narcyber.mvpbasics.helper.emptyLoaderObject
import com.narcyber.mvpbasics.model.AirlinesContent
import com.narcyber.mvpbasics.presenter.AirLineFragmentPresenter

class AirlineAdapter(var action: ActionNotifyCallBack) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable,
    AirLineFragmentPresenter.AirlineAdapterView {
    var sizeOfPage: Int = 10
    var page: Int = 10
    private val all: ArrayList<AirlinesContent> = ArrayList()
    private val back: ArrayList<AirlinesContent> = ArrayList()

    init {
        all.add(emptyLoaderObject)
    }

    override val allData: ArrayList<AirlinesContent> = all
    override val backUpData: ArrayList<AirlinesContent> = back

    interface ActionNotifyCallBack {
        fun notifyLastItem()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> AirlineAdapterViewHolderEmpty(
                RowLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            else -> AirlineAdapterViewHolder(
                RowItemAirlinesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            (holder as AirlineAdapterViewHolder).root.nameText.text = allData[position].name

        } else {
            action.notifyLastItem()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (allData[position].id == "-1") 1 else 0
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun getFilter(): Filter {
        return airLineAdapterFilter(this, allData, backUpData)
    }

    override fun notifyItemCountChanged(from: Int, size: Int) {
        notifyItemRangeChanged(from, size)
    }

    override fun notifyDataIsAdded(where: Int) {
        notifyItemInserted(where)
    }

    override fun notifyDataMoved(from: Int, size: Int) {
        notifyItemMoved(from, size)
    }

    override fun notifyItemsPush(from: Int, size: Int) {
        notifyItemRangeInserted(from, size)
    }

    override fun notifyAllChanges() {
        notifyDataSetChanged()
    }

    override fun hideProgress() {

        val index = allData.indexOf(emptyLoaderObject)
        if (index >= 0) allData.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeRemoved(0, allData.size)
    }

    override fun showProgress() {

        if (allData.indexOf(emptyLoaderObject) == -1) {
            allData.add(emptyLoaderObject)
            notifyItemInserted(allData.lastIndex)
        }

    }


}
