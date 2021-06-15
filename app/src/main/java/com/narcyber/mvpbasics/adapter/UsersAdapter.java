package com.narcyber.mvpbasics.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.narcyber.mvpbasics.databinding.RowAllUsersBinding;
import com.narcyber.mvpbasics.helper.ListAdapterHelper;
import com.narcyber.mvpbasics.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsersAdapter extends ListAdapter<User, UsersAdapter.UsersAdapterViewHolder> implements Filterable {
    List<User> allUsers;
    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence c) {
            final List<User> filteredList = new ArrayList<>();
            if (c == null || c.toString().isEmpty()) {
                filteredList.addAll(allUsers);
            } else {
                for (User item : allUsers) {
                    if (item.getUserName().toLowerCase().contains(c.toString().toLowerCase())
                            || item.getFullName().contains(c.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence c, FilterResults results) {
            final List<User> arrayList = new ArrayList<User>((Collection<? extends User>) results.values);
            UsersAdapter.this.submitList(arrayList);
        }
    };

    public UsersAdapter() {
        super(ListAdapterHelper.UserDiffUtil);
    }

    public void createItemsBackUp() {
        this.allUsers = new ArrayList<>(getCurrentList());

    }

    public void ansyncItemsBackUp(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    @Override
    public UsersAdapter.UsersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RowAllUsersBinding rowAllUsersBinding = RowAllUsersBinding
                .inflate(LayoutInflater.from(parent.getContext())
                        , parent, false);
        return new UsersAdapterViewHolder(rowAllUsersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapterViewHolder holder, int position) {
        holder.root.email.setText(getItem(position).getEmail());
        holder.root.login.setText(getItem(position).getUserName());
        holder.root.name.setText(getItem(position).getFullName());
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public static class UsersAdapterViewHolder extends RecyclerView.ViewHolder {
        private final RowAllUsersBinding root;

        public UsersAdapterViewHolder(RowAllUsersBinding r) {
            super(r.getRoot());
            root = r;
        }
    }

}
