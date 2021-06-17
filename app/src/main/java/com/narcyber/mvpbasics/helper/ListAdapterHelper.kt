package com.narcyber.mvpbasics.helper

import androidx.recyclerview.widget.DiffUtil
import com.narcyber.mvpbasics.model.User

class ListAdapterHelper {
    companion object {
        var StringDiffUtil: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(old: String, newItem: String): Boolean {
                    return old == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem.equals(newItem, ignoreCase = true)
                }
            }


        var UserDiffUtil: DiffUtil.ItemCallback<User> = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

}