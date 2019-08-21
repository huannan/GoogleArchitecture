package com.nan.architecture.test;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nan.architecture.mvvm.BaseViewModel;

public class TestViewModel extends BaseViewModel {

    MutableLiveData<String> mText = new MutableLiveData<>();

    public TestViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected boolean needShowLoading() {
        return false;
    }

    public MutableLiveData<String> getText() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mText.postValue("加载成功");
            }
        }).start();

        return mText;
    }
}
