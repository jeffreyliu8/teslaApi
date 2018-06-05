package com.askjeffreyliu.teslaapi.endpoint;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.askjeffreyliu.teslaapi.model.ChargeStateResponse;
import com.askjeffreyliu.teslaapi.model.ChargeStateResponseObj;
import com.askjeffreyliu.teslaapi.model.MobileAccessEnableResponse;
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
import retrofit2.http.Path;


public class VehiclesEndpoint extends BaseEndpoint {

    private interface VehiclesService {

        @GET("api/1/vehicles")
        Call<VehiclesResponse> getVehicles(@Header("Authorization") String authHeader);

        @GET("api/1/vehicles/{id}/mobile_enabled")
        Call<MobileAccessEnableResponse> getIsMobileAccessEnabled(@Header("Authorization") String authHeader, @Path("id") long id);

        @GET("api/1/vehicles/{id}/data_request/charge_state")
        Call<ChargeStateResponse> getChargeState(@Header("Authorization") String authHeader, @Path("id") long id);
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
                        Logger.d("response is successful, you have " + vehiclesResponse.getCount());
                        data.setValue(vehiclesResponse.getResponse());
                    } else {
                        data.setValue(null);
                    }
                } else {
                    data.setValue(null);
                    Logger.e("response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<VehiclesResponse> call, Throwable t) {
                data.setValue(null);
                Logger.e("onFailure unsuccessful");
            }
        });

        return data;
    }

    public LiveData<Boolean> getIsMobileAccessEnabled(long id) {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();

        vehiclesService.getIsMobileAccessEnabled("bearer " + accessToken, id).enqueue(new Callback<MobileAccessEnableResponse>() {
            @Override
            public void onResponse(Call<MobileAccessEnableResponse> call, Response<MobileAccessEnableResponse> response) {
                if (response.isSuccessful()) {
                    MobileAccessEnableResponse mobileAccessEnableResponse = response.body();
                    if (mobileAccessEnableResponse != null) {
                        data.setValue(mobileAccessEnableResponse.getResponse());
                    } else {
                        data.setValue(null);
                    }
                } else if (response.code() == 401) {
                    Logger.e(response.message());
                    data.setValue(null);
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MobileAccessEnableResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<ChargeStateResponseObj> getChargerState(long id) {
        final MutableLiveData<ChargeStateResponseObj> data = new MutableLiveData<>();

        vehiclesService.getChargeState("bearer " + accessToken, id).enqueue(new Callback<ChargeStateResponse>() {
            @Override
            public void onResponse(Call<ChargeStateResponse> call, Response<ChargeStateResponse> response) {
                if (response.isSuccessful()) {
                    ChargeStateResponse chargeStateResponse = response.body();
                    if (chargeStateResponse != null) {
                        data.setValue(chargeStateResponse.getResponse());
                    } else {
                        data.setValue(null);
                    }
                } else if (response.code() == 401) {
                    Logger.e(response.message());
                    data.setValue(null);
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ChargeStateResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
