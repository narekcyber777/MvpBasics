package com.narcyber.mvpbasics.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.narcyber.mvpbasics.model.User;

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


    public static DiffUtil.ItemCallback<User> UserDiffUtil = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

}
