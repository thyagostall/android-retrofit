package com.thyago.retrofit.store;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by thyago on 6/21/16.
 */
public interface StoreAPI {
    @GET("store/{id}")
    Call<ApiResult<Store>> store(@Path("id") int id);
}
