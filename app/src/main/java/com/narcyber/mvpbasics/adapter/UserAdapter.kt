package com.narcyber.mvpbasics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.narcyber.mvpbasics.adapter.holder.UsersAdapterViewHolder
import com.narcyber.mvpbasics.databinding.RowAllUsersBinding
import com.narcyber.mvpbasics.helper.UserDiffUtil
import com.narcyber.mvpbasics.model.User
import java.util.*

class UserAdapter :
    ListAdapter<User, UsersAdapterViewHolder>(UserDiffUtil),
    Filterable {

    private lateinit var allList: MutableList<User>
    fun createItemBackUp() {
        allList = currentList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapterViewHolder {
        val rowAllUsersBinding = RowAllUsersBinding
            .inflate(
                LayoutInflater.from(parent.context), parent, false
            )

        return UsersAdapterViewHolder(rowAllUsersBinding)
    }

    override fun onBindViewHolder(holder: UsersAdapterViewHolder, position: Int) {
        holder.root.email.text = getItem(position).email
        holder.root.login.text = getItem(position).userName
        holder.root.name.text = getItem(position).fullName
    }

    override fun getFilter(): Filter {
        return filter
    }
    private val filter: Filter = object : Filter() {
        override fun performFiltering(c: CharSequence?): FilterResults {
            val filteredList: MutableList<User> = mutableListOf()
            if (c == null || c.isEmpty()) {
                filteredList.addAll(allList)
            } else {
                allList.forEach { a ->
                    run {
                        val b: Boolean = a.fullName.lowercase(Locale.getDefault())
                            .contains(c.toString().lowercase(Locale.getDefault()))
                                || a.userName.lowercase().contains(c.toString())
                        if (b) filteredList.add(a)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            var list = results?.values as MutableList<User>
            submitList(list)
        }
    }


}