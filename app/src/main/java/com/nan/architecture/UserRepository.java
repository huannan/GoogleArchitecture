package com.nan.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

    private Api api;

    public LiveData<User> getUser(String userId) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        Disposable subscribe = api.getUser(userId)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        data.setValue(user);
                    }
                });
        return data;
    }

}
