package com.thyago.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreActivity extends AppCompatActivity implements Callback<ApiResult<Store>> {

    private static final String LOG_TAG = StoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://app.personalcentroautomotivo.com.br/api/v1/")
                .build();

        StoreAPI api = retrofit.create(StoreAPI.class);
        api.store(1).enqueue(this);
    }

    @Override
    public void onResponse(Call<ApiResult<Store>> call, Response<ApiResult<Store>> response) {
        ApiResult<Store> body = response.body();
        Log.d(LOG_TAG, "response=" + body.getContent().getName());
    }

    @Override
    public void onFailure(Call<ApiResult<Store>> call, Throwable t) {
        Log.d(LOG_TAG, "failure=" + t);
    }
}
