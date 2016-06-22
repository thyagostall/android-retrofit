package com.thyago.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreActivity extends AppCompatActivity implements Callback<ApiResult<Store>> {

    private static final String LOG_TAG = StoreActivity.class.getSimpleName();

    private Unbinder mUnbinder;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private void setLoading(final boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mUnbinder = ButterKnife.bind(this);
        setLoading(true);

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
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onResponse(Call<ApiResult<Store>> call, Response<ApiResult<Store>> response) {
        ApiResult<Store> body = response.body();
        Log.d(LOG_TAG, "response=" + body.getStatusCode());

        setLoading(false);
    }

    @Override
    public void onFailure(Call<ApiResult<Store>> call, Throwable t) {
        Log.d(LOG_TAG, "failure_class=" + t.getClass().getSimpleName());
        Log.d(LOG_TAG, "failure=" + t.getLocalizedMessage());

        setLoading(false);
    }
}
