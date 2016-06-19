package com.thyago.retrofit;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<StackOverflowQuestions> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Unbinder mUnbinder;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        mProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StackOverflowAPI api = retrofit.create(StackOverflowAPI.class);
        Call<StackOverflowQuestions> call = api.loadQuestions("android");
        call.enqueue(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onResponse(Call<StackOverflowQuestions> call, Response<StackOverflowQuestions> response) {
        mProgressBar.setVisibility(View.GONE);
        List<Question> result = response.body().items;

        ArrayAdapter<Question> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onFailure(Call<StackOverflowQuestions> call, Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG).show();
    }
}
