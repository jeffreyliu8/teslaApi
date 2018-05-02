package com.askjeffreyliu.teslaapi.endpoint;


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


public class AuthEndpoint extends BaseEndpoint {

    private interface AuthService {
        @FormUrlEncoded
        @POST("oauth/token")
        Call<AccessTokenResponse> getToken(@Field("grant_type") String grant_type,
                                           @Field("client_id") String client_id,
                                           @Field("client_secret") String client_secret,
                                           @Field("email") String email,
                                           @Field("password") String password);
    }

    private final AuthService authService;

    public AuthEndpoint() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        authService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(AuthService.class);
    }

    public LiveData<String> getToken(String grant_type, String client_id, String client_secret, String email, String password) {
        final MutableLiveData<String> data = new MutableLiveData<>();

        authService.getToken(grant_type, client_id, client_secret, email, password).enqueue(new Callback<AccessTokenResponse>() {
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
}
