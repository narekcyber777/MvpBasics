package com.narcyber.mvpbasics.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.narcyber.mvpbasics.databinding.RowItemCityBinding;
import com.narcyber.mvpbasics.helper.ListAdapterHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CityAdapter extends ListAdapter<String, CityAdapter.CityAdapterViewHolder> implements Filterable {
    private final CityAdapterCallBack cityAdapterCallBack;
    private ArrayList<String> allItems;
    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence c) {
            final List<String> filteredList = new ArrayList<>();
            if (c == null || c.toString().isEmpty()) {
                filteredList.addAll(allItems);
            } else {
                for (String item : allItems) {
                    if (item.toLowerCase().contains(c.toString().toLowerCase())) {
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
            List<String> arrayList = new ArrayList<String>((Collection<? extends String>) results.values);
            CityAdapter.this.submitList(arrayList);

        }
    };

    public CityAdapter(CityAdapterCallBack cityAdapterCallBack) {
        super(ListAdapterHelper.StringDiffUtil);
        this.cityAdapterCallBack = cityAdapterCallBack;
    }

    public void createItemsBackUp() {
        this.allItems = new ArrayList<>(getCurrentList());
        System.out.println(allItems);
    }

    @Override
    public CityAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RowItemCityBinding rowItemCityBinding = RowItemCityBinding
                .inflate(LayoutInflater.from(parent.getContext())
                        , parent, false);
        return new CityAdapterViewHolder(rowItemCityBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityAdapterViewHolder holder, int position) {
        holder.root.cardViewItem.setText(getItem(position));
        holder.root.cardViewItem.setOnClickListener(v -> {
            if (cityAdapterCallBack != null)
                cityAdapterCallBack.onClick(holder.root.cardViewItem.getText().toString());
        });
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public interface CityAdapterCallBack {
        void onClick(int id);

        void onClick(String city);
    }

    public static class CityAdapterViewHolder extends RecyclerView.ViewHolder {
        private final RowItemCityBinding root;

        public CityAdapterViewHolder(RowItemCityBinding r) {
            super(r.getRoot());
            root = r;
        }
    }

}
