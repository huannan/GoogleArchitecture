package com.nan.architecture;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("/users/{userId}")
    Observable<User> getUser(@Path("userId") String userId);

}
