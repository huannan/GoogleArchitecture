package com.nan.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * https://www.jianshu.com/p/42eb71ec4a19
 */
public class UserProfileViewModel extends ViewModel  {

    private String mUserId;
    private LiveData<User> mUser;

    public void init(String userId) {
        mUserId = userId;
    }

    public LiveData<User> getUser() {

        return mUser;
    }
}
