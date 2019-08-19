package com.nan.architecture.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends AndroidViewModel {

    protected MutableLiveData<Integer> mCurrentState = new MutableLiveData<>();
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @IntDef({
            PageState.LOADING,
            PageState.LOAD_SUCCESS,
            PageState.LOAD_ERROR,
            PageState.NO_NETWORK
    })
    public @interface PageState {
        int LOADING = 0;
        int LOAD_SUCCESS = 1;
        int LOAD_ERROR = 2;
        int NO_NETWORK = 3;
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getCurrentState() {
        return mCurrentState;
    }

    public void setCurrentState(@PageState int currentState) {
        mCurrentState.setValue(currentState);
    }

    protected void init() {
        if (needShowLoading()) {
            setCurrentState(PageState.LOADING);
        } else {
            setCurrentState(PageState.LOAD_SUCCESS);
        }
    }

    protected abstract boolean needShowLoading();

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}
