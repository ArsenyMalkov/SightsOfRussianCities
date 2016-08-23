package com.arsenymalkov.sightsofrussiancities.network;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface RestApi {

    @FormUrlEncoded
    @POST("/")
    Observable<ResponseBody> getSights(@FieldMap HashMap<String, String> params);

}
