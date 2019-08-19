package com.nan.architecture.mvvm;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {

    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = createViewModel();
        mViewModel.getCurrentState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer pageState) {
                if (pageState != null) {
                    switch (pageState) {
                        case BaseViewModel.PageState.LOADING:
                            showLoading();
                            break;
                        case BaseViewModel.PageState.LOAD_SUCCESS:
                            showLoadSuccess();
                            break;
                        case BaseViewModel.PageState.LOAD_ERROR:
                            showLoadError();
                            break;
                        case BaseViewModel.PageState.NO_NETWORK:
                            showNoNetwork();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        mViewModel.init();
    }

    protected abstract VM createViewModel();

    protected abstract void showLoading();

    protected abstract void showLoadSuccess();

    protected abstract void showLoadError();

    protected abstract void showNoNetwork();
}
