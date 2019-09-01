package com.example.rxrequesttoserver.network;

import android.content.Context;

import com.example.rxrequesttoserver.model.GetImages;
import com.example.rxrequesttoserver.utility.ClientConfig;
import com.example.rxrequesttoserver.utility.PreferenceStorage;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ServiceProvider {

    private Service mService;

    public ServiceProvider(Context context) {

        //config client and retrofit:

        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES);
        clientBuilder.cache(null);

        PreferenceStorage storage = PreferenceStorage.getInstance(context);
        if (!storage.retriveToken().equals("0")) {//user token was not null so we add access token in all of request

            clientBuilder.addInterceptor(chain -> {
                Request request = chain.request().newBuilder().
                        addHeader("lang", storage.retriveLanguage())
                        .addHeader("token", PreferenceStorage.getInstance(context).retriveToken()).build();
                return chain.proceed(request);
            });
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ClientConfig.BASE_ADDRESS).client(clientBuilder.build()).
                addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        mService = retrofit.create(Service.class);
    }

    public Service getmService() {
        return mService;
    }
}
