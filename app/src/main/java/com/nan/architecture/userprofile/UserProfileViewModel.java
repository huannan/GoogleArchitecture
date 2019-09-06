package com.nan.architecture.userprofile;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nan.architecture.api.NetworkManager;
import com.nan.architecture.db.User;
import com.nan.architecture.mvvm.BaseViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class UserProfileViewModel extends BaseViewModel {

    private MutableLiveData<User> mUser = new MutableLiveData<>();
    private MutableLiveData<String> mUserName = new MutableLiveData<>();
    private Observer<String> mUserNameObserver;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void init() {
        super.init();
        mUserNameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String user) {
                NetworkManager.getInstance().getApi().getUser(mUserName.getValue())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<User>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                setCurrentState(PageState.LOADING);
                            }

                            @Override
                            public void onSuccess(User user) {
                                if (user != null) {
                                    setCurrentState(PageState.LOAD_SUCCESS);
                                    mUser.setValue(user);
                                } else {
                                    setCurrentState(PageState.LOAD_ERROR);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // 这里需要判断网络状态
                                setCurrentState(PageState.LOAD_ERROR);
                            }
                        });
            }
        };
        mUserName.observeForever(mUserNameObserver);
    }

    @Override
    public boolean needShowLoading() {
        return false;
    }

    public LiveData<User> getUser() {
        return mUser;
    }

    public void searchUser(String userName) {
        mUserName.setValue(userName);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mUserName.removeObserver(mUserNameObserver);
    }
}
