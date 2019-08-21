package com.nan.architecture.test;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.nan.architecture.R;
import com.nan.architecture.mvvm.BaseActivity;

public class TestActivity extends BaseActivity<TestViewModel> {

    private TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTvTest = findViewById(R.id.tv_test);
        mViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String text) {
                if (null != text) {
                    mTvTest.setText(text);
                }
            }
        });
    }

    @Override
    protected TestViewModel createViewModel() {
        return ViewModelProviders.of(this).get(TestViewModel.class);
    }

    @Override
    protected void showLoading() {

    }

    @Override
    protected void showLoadSuccess() {

    }

    @Override
    protected void showLoadError() {

    }

    @Override
    protected void showNoNetwork() {

    }
}
