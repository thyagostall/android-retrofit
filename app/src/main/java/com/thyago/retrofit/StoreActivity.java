package com.thyago.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        GsonConverterFactory gsonFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonFactory)
                .baseUrl("http://app.personalcentroautomotivo.com.br/api/v1/")
                .build();

        StoreAPI api = retrofit.create(StoreAPI.class);
        api.store(1).enqueue(this);
    }

    @Override
    public void onResponse(Call<ApiResult<Store>> call, Response<ApiResult<Store>> response) {
        ApiResult<Store> body = response.body();
        Log.d(LOG_TAG, "response=" + body.getStatusCode());
    }

    @Override
    public void onFailure(Call<ApiResult<Store>> call, Throwable t) {
        Log.d(LOG_TAG, "failure_class=" + t.getClass().getSimpleName());
        Log.d(LOG_TAG, "failure=" + t.getLocalizedMessage());
    }
}
