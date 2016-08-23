package com.arsenymalkov.sightsofrussiancities.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class RestClient {

    public static final String BASE_URL = "http://api.russia.travel/";

    private static volatile Retrofit retrofit;
    private static volatile RestApi restApi;

    private static Retrofit getRetrofit() {
        Retrofit localRetrofit = retrofit;
        if (localRetrofit == null) {
            synchronized (Retrofit.class) {
                localRetrofit = retrofit;
                if (localRetrofit == null) {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Gson gson = new GsonBuilder()
//                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                    RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

                    retrofit = localRetrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(rxAdapter)
                            .build();
                }
            }
        }
        return localRetrofit;
    }

    public static RestApi getRestApi() {
        RestApi localRestApi = restApi;
        if (localRestApi == null) {
            synchronized (RestApi.class) {
                localRestApi = restApi;
                if (localRestApi == null) {
                    restApi = localRestApi = getRetrofit().create(RestApi.class);
                }
            }
        }
        return localRestApi;
    }

}
