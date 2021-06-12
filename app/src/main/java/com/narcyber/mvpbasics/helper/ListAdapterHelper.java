package com.narcyber.mvpbasics.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ListAdapterHelper {
    public static DiffUtil.ItemCallback<String> StringDiffUtil = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String old, @NonNull String newItem) {
            return old.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equalsIgnoreCase(newItem);
        }
    };

}
