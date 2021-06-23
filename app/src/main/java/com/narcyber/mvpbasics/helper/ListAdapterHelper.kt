package com.narcyber.mvpbasics.helper

import android.util.Log
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.narcyber.mvpbasics.adapter.holder.CityAdapterViewHolder
import com.narcyber.mvpbasics.model.AirlinesContent
import com.narcyber.mvpbasics.model.User
import java.util.*
import kotlin.collections.ArrayList

val StringDiffUtil: DiffUtil.ItemCallback<String> =
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(old: String, newItem: String): Boolean {
            return old == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.equals(newItem, ignoreCase = true)
        }
    }
val UserDiffUtil: DiffUtil.ItemCallback<User> = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
val AirlineModelDiffUtil: DiffUtil.ItemCallback<AirlinesContent> =
    object : DiffUtil.ItemCallback<AirlinesContent>() {
        override fun areItemsTheSame(
            oldItem: AirlinesContent,
            newItem: AirlinesContent
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AirlinesContent,
            newItem: AirlinesContent
        ): Boolean {
            return oldItem == newItem
        }

    }


fun airLineAdapterFilter(
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    allData: ArrayList<AirlinesContent>,
    backUpData: ArrayList<AirlinesContent>
): Filter {
    return object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterList: ArrayList<AirlinesContent> = ArrayList()
            if (constraint.isNullOrEmpty()) {
                filterList.addAll(allData)
            } else {
                backUpData.forEach {
                    if (it.name.contains(constraint, true)) {
                        filterList.add(it)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filterList

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            var size = allData.size
            allData.clear()
            adapter.notifyItemRangeRemoved(0, size)
            if (constraint.isNullOrEmpty()) {
                allData.addAll(backUpData)
            } else {
                allData.addAll(results.values as Collection<AirlinesContent>)
            }
            /*  adapter.notifyItemRangeInserted(0, allData.size)
              adapter.notifyItemRangeChanged(0, allData.size)*/
            Log.d("TAG", "publishResults: ${allData.size}")

            adapter.notifyDataSetChanged()
        }
    }
}

fun cityAdapterFilter(
    adapter: ListAdapter<String, CityAdapterViewHolder>,
    allData: ArrayList<String>
): Filter {
    return object : Filter() {
        override fun performFiltering(c: CharSequence?): FilterResults {
            val filteredList: MutableList<String> = mutableListOf()
            if (c == null || c.isEmpty()) {
                filteredList.addAll(allData)
            } else {
                allData.forEach { a ->
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
            val list = results?.values as MutableList<String>
            adapter.submitList(list)
        }

    }

}

val emptyLoaderObject: AirlinesContent by lazy {
    AirlinesContent(id = "-1", name = "")
}

abstract class CustomScrollListener(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    var int: Int = 0
    var limit: Int = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount: Int = linearLayoutManager.childCount
        val totalItemCount: Int = linearLayoutManager.itemCount
        val firstVisibleItemAt: Int = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
        val lastVisibleItemAt: Int = linearLayoutManager.findLastCompletelyVisibleItemPosition()

        if (dy > 0) {
            Log.d(
                "Nar",
                "now is visible items count   $visibleItemCount plus total is " +
                        " $totalItemCount" +
                        " firstVisibleItemAt${firstVisibleItemAt} \" lastitemVisible  ${lastVisibleItemAt} "
            )
            if (totalItemCount - firstVisibleItemAt == totalItemCount / 2 && firstVisibleItemAt >= 0 && limit != lastVisibleItemAt) {
                Log.d(
                    "Nar crunched ",
                    "now is visibleActionssss ${visibleItemCount} p lus total is" +
                            " ${totalItemCount} firstVisibleItemAt${firstVisibleItemAt} \"lastitemVisible ${lastVisibleItemAt} "
                )
                limit = lastVisibleItemAt
                loadMore()
            }
        }
    }

    abstract fun loadMore()

}
