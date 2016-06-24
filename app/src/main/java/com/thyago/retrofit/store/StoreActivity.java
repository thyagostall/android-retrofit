package com.thyago.retrofit.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.thyago.retrofit.R;

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
    private static final String STORE_TAG = "store";

    private Unbinder mUnbinder;

    @BindView(R.id.image)
    ImageView mImageView;

    @BindView(R.id.image_progress_bar)
    ProgressBar mImageProgressBar;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.description)
    TextView mDescription;

    @BindView(R.id.brand)
    TextView mBrand;

    @BindView(R.id.phone)
    TextView mPhone;

    @BindView(R.id.email)
    TextView mEmail;
    private Store mStore;

    private void setLoading(final boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        mUnbinder = ButterKnife.bind(this);
        if (savedInstanceState != null) {
            return;
        }

        Log.i(LOG_TAG, "cached=false");
        setLoading(true);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        GsonConverterFactory gsonFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonFactory)
                .baseUrl("http://app.personalcentroautomotivo.com.br/api/v1/")
                .build();

        StoreAPI api = retrofit.create(StoreAPI.class);
        api.store(1).enqueue(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(LOG_TAG, "cached=true");

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        mStore = gson.fromJson(savedInstanceState.getString(STORE_TAG), Store.class);
        fillInData(mStore);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG_TAG, "saving info");

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        outState.putString(STORE_TAG, gson.toJson(mStore));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void fillInData(Store store) {
        mAddress.setText(store.getAddress());
        mName.setText(store.getName());
        mDescription.setText(store.getDescription());
        mBrand.setText(store.getBrand());
        mPhone.setText(store.getPhone());
        mEmail.setText(store.getEmail());

        Picasso.with(this)
                .load(store.getImage().getImageDefault())
                .into(mImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        mImageProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        mImageProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onResponse(Call<ApiResult<Store>> call, Response<ApiResult<Store>> response) {
        ApiResult<Store> body = response.body();
        Log.d(LOG_TAG, "response=" + body.getStatusCode());
        if (body.getStatusCode() != 200) {
            return;
        }

        mStore = body.getContent();
        fillInData(mStore);

        setLoading(false);
    }

    @Override
    public void onFailure(Call<ApiResult<Store>> call, Throwable t) {
        Log.d(LOG_TAG, "failure_class=" + t.getClass().getSimpleName());
        Log.d(LOG_TAG, "failure=" + t.getLocalizedMessage());

        setLoading(false);
    }
}
