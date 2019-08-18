package com.nan.architecture.userprofile;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nan.architecture.api.Api;
import com.nan.architecture.db.User;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserProfileViewModel extends AndroidViewModel {

    private MutableLiveData<User> mUser = new MutableLiveData<>();
    private MutableLiveData<String> mUserName = new MutableLiveData<>();
    private Api mApi;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public UserProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = retrofit.create(Api.class);

        mUserName.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String user) {
                mApi.getUser(mUserName.getValue())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<User>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(User user) {
                                mUser.setValue(user);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        });
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
        mCompositeDisposable.clear();
    }

}
