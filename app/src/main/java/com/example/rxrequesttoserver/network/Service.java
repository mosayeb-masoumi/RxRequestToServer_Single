package com.example.rxrequesttoserver.network;

import com.example.rxrequesttoserver.model.GetImages;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    @GET("{api}/user/dashboard")
    Single<GetImages> getImages(@Path("api") String version);
}
