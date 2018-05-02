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
import retrofit2.http.GET;
import retrofit2.http.Header;



public class VehiclesEndpoint extends BaseEndpoint {

    private interface VehiclesService {

        @GET("api/1/vehicles")
        Call<AccessTokenResponse> getVehicles(@Header("Authorization") String authHeader);
    }

    private final VehiclesService vehiclesService;

    public VehiclesEndpoint(final String accessToken) {
        super(accessToken);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        vehiclesService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(VehiclesService.class);
    }

    public LiveData<String> getVehicles() {
        final MutableLiveData<String> data = new MutableLiveData<>();

        vehiclesService.getVehicles("bearer " + accessToken).enqueue(new Callback<AccessTokenResponse>() {
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
