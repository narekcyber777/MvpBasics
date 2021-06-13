package com.narcyber.mvpbasics.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.narcyber.mvpbasics.databinding.ActivityUserBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.utils.MyUtils;
import com.narcyber.mvpbasics.view.HomeActivity;
import com.narcyber.mvpbasics.view.MainActivity;

public class UserPersonalFragment extends Fragment {
    private ActivityUserBinding root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = ActivityUserBinding.inflate(inflater, container, false);
        return root.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inIt();
    }

    private void inIt() {
        if (HomeActivity.userMap != null) {
            root.name.setText(HomeActivity.userMap.get(ConstantHelper.KEY_Full_Name));
            root.email.setText(HomeActivity.userMap.get(ConstantHelper.KEY_EMAIL));
            root.login.setText(HomeActivity.userMap.get(ConstantHelper.KEY_USERNAME));
        }
        root.logOut.setOnClickListener(v -> {
            if (getActivity() instanceof HomeActivity && getActivity() != null) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.removeLocal();
                MyUtils.moveToAndClear(homeActivity, MainActivity.class);
            }
        });
    }


}
