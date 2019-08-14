package com.nan.architecture;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel mViewModel;
    private static final String KEY_USER_ID = "key_user_id";
    private TextView mTvName;
    private Button mBtnUpdate;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String userId = getArguments().getString(KEY_USER_ID);

        mViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        mViewModel.init(userId);
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvName = view.findViewById(R.id.tv_name);
        mBtnUpdate = view.findViewById(R.id.btn_update);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.
            }
        });
    }
}
