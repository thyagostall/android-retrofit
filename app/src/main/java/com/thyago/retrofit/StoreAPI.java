package com.thyago.retrofit;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by thyago on 6/21/16.
 */
public interface StoreAPI {
//    http://app.personalcentroautomotivo.com.br/api/v1/store/1
    @GET("store/{id}")
    Call<ApiResult<Store>> store(@Path("id") int id);
}
