package com.droid.basawa.halodoc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {
    String Base_URL = "https://hn.algolia.com/api/v1/";

    @GET("search")
    Call<List<POJO>> getDataFromImages(@Query("query") String user_inpuut);
}
