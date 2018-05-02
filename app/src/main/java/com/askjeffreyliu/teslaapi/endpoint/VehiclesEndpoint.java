package com.askjeffreyliu.teslaapi.endpoint;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.model.VehiclesResponse;
import com.orhanobut.logger.Logger;

import java.util.List;

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
        Call<VehiclesResponse> getVehicles(@Header("Authorization") String authHeader);
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

    public LiveData<List<Vehicle>> getVehicles() {
        final MutableLiveData<List<Vehicle>> data = new MutableLiveData<>();

        vehiclesService.getVehicles("bearer " + accessToken).enqueue(new Callback<VehiclesResponse>() {
            @Override
            public void onResponse(Call<VehiclesResponse> call, Response<VehiclesResponse> response) {
                if (response.isSuccessful()) {
                    VehiclesResponse vehiclesResponse = response.body();
                    if (vehiclesResponse != null) {
                        Logger.d("response isSuccessful " + "you have " + vehiclesResponse.getCount());
                        data.setValue(vehiclesResponse.getResponse());
                    } else {
                        data.setValue(null);
                    }
                } else {
                    data.setValue(null);
                    Logger.e("response unsuccessful ");
                }
            }

            @Override
            public void onFailure(Call<VehiclesResponse> call, Throwable t) {
                data.setValue(null);
                Logger.e("onFailure unsuccessful ");
            }
        });

        return data;
    }
}
