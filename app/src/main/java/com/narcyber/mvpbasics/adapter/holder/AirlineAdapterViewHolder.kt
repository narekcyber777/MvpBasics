package com.narcyber.mvpbasics.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.narcyber.mvpbasics.databinding.RowItemAirlinesBinding

class AirlineAdapterViewHolder(private val root_: RowItemAirlinesBinding) :
    RecyclerView.ViewHolder(root_.root) {
    internal val root: RowItemAirlinesBinding = root_
}