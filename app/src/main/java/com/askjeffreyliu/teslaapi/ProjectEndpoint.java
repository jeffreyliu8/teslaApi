package com.askjeffreyliu.teslaapi;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import com.askjeffreyliu.teslaapi.model.AccessTokenResponse;
import com.orhanobut.logger.Logger;



import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.POST;


public class ProjectEndpoint extends BaseEndpoint {

    private interface LikesService {
        @FormUrlEncoded
        @POST("oauth/token")
        Call<AccessTokenResponse> getToken(@Field("grant_type") String grant_type,
                                           @Field("client_id") String client_id,
                                           @Field("client_secret") String client_secret,
                                           @Field("email") String email,
                                           @Field("password") String password);

//        @GET("/repos/{user}/{reponame}")
//        Call<GithubProject> getProjectDetails(@Path("user") String user, @Path("reponame") String projectName);
    }

    private final LikesService projectService;

    public ProjectEndpoint() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        projectService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(LikesService.class);
    }

    public LiveData<String> getToken(String grant_type, String client_id, String client_secret, String email, String password) {
        final MutableLiveData<String> data = new MutableLiveData<>();

        projectService.getToken(grant_type, client_id, client_secret, email, password).enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.isSuccessful()) {
                    AccessTokenResponse accessTokenResponse = response.body();
                    data.setValue(accessTokenResponse.getAccess_token());
                    Logger.d("response isSuccessful ");
                } else {
                    data.setValue(null);
                    Logger.e("response unsuccessful ");
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                // TODO better error handling
                data.setValue(null);

                Logger.e("onFailure unsuccessful ");
            }
        });

        return data;
    }

//    public LiveData<GithubProject> getProjectDetails(String userID, String projectName) {
//        final MutableLiveData<GithubProject> data = new MutableLiveData<>();
//
//        projectService.getProjectDetails(userID, projectName).enqueue(new Callback<GithubProject>() {
//            @Override
//            public void onResponse(Call<GithubProject> call, Response<GithubProject> response) {
//                data.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<GithubProject> call, Throwable t) {
//                // TODO better error handling
//                data.setValue(null);
//            }
//        });
//
//        return data;
//    }
}
