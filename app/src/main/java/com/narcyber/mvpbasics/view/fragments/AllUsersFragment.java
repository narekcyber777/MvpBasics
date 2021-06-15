package com.narcyber.mvpbasics.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.narcyber.mvpbasics.adapter.UsersAdapter;
import com.narcyber.mvpbasics.databinding.FragmentAllUsersBinding;
import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.presenter.AllUsersFragmentPresenter;

import java.util.List;

public class AllUsersFragment extends Fragment implements AllUsersFragmentPresenter.AllUserView {
    private FragmentAllUsersBinding root;
    private AllUsersFragmentPresenter presenter;
    private UsersAdapter usersAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = FragmentAllUsersBinding.inflate(inflater, container, false);
        return root.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customizeWidgets();
        inIt();
    }

    private void customizeWidgets() {
        root.recycler.addItemDecoration(new DividerItemDecoration(
                root.getRoot().getContext(), DividerItemDecoration.VERTICAL));
    }

    private void inIt() {
        presenter = new AllUsersFragmentPresenter(this);
        presenter.getAllUsers();
        usersAdapter = new UsersAdapter();
        root.recycler.setAdapter(usersAdapter);
        root.searchWidget.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                usersAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void allUsers(List<User> userList) {
        usersAdapter.submitList(userList);
        usersAdapter.ansyncItemsBackUp(userList);
    }
}
