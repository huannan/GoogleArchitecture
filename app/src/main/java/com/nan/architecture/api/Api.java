package com.nan.architecture.api;

import com.nan.architecture.db.User;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("users/{username}")
    Single<User> getUser(@Path("username") String userName);

}
