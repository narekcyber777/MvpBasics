package com.narcyber.mvpbasics.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.narcyber.mvpbasics.databinding.FragmentUserPersonalBinding;
import com.narcyber.mvpbasics.utils.MyUtils;
import com.narcyber.mvpbasics.view.HomeActivity;
import com.narcyber.mvpbasics.view.MainActivity;

public class UserPersonalFragment extends Fragment {
    private FragmentUserPersonalBinding root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = FragmentUserPersonalBinding.inflate(inflater, container, false);
        return root.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inIt(view);
    }

    private void inIt(View view) {

        if (HomeActivity.user != null) {
            root.name.setText(HomeActivity.user.getFullName());
            root.email.setText(HomeActivity.user.getEmail());
            root.login.setText(HomeActivity.user.getUserName());
        }
        root.logOut.setOnClickListener(v -> {
            if (getActivity() instanceof HomeActivity && getActivity() != null) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.removeLocal();
                MyUtils.moveToAndClear(homeActivity, MainActivity.class);
            }
        });
        root.allUsers.setOnClickListener(v -> {
            UserPersonalFragmentDirections.ActionUserPersonalFragmentToAllUsersFragment uAction
                    = UserPersonalFragmentDirections
                    .actionUserPersonalFragmentToAllUsersFragment(root.email.getText().toString());
            Navigation.findNavController(view).navigate(uAction);
        });
    }


}
