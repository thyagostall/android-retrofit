package com.thyago.retrofit.github;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by thyago on 6/24/16.
 */
public interface GithubService {
    String SERVICE_ENDPOING = "https://api.github.com";

    @GET("/users/{login}")
    Observable<Github> getUser(@Path("login") String login);
}
