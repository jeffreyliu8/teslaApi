package com.askjeffreyliu.teslaapi.endpoint;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.askjeffreyliu.teslaapi.model.ChargeStateResponse;

import com.askjeffreyliu.teslaapi.model.MobileAccessEnableResponse;
import com.askjeffreyliu.teslaapi.model.SimplePostResponse;
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
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class VehiclesEndpoint extends BaseEndpoint {

    private interface VehiclesService {

        @GET("api/1/vehicles")
        Call<VehiclesResponse> getVehicles(@Header("Authorization") String authHeader);

        @GET("api/1/vehicles/{id}/mobile_enabled")
        Call<MobileAccessEnableResponse> getIsMobileAccessEnabled(@Header("Authorization") String authHeader, @Path("id") long id);

        @GET("api/1/vehicles/{id}/data_request/charge_state")
        Call<ChargeStateResponse> getChargeState(@Header("Authorization") String authHeader, @Path("id") long id);

        @POST("api/1/vehicles/{id}/wake_up")
        Call<SimplePostResponse> wakeUp(@Header("Authorization") String authHeader, @Path("id") long id);

        @POST("api/1/vehicles/{id}/command/flash_lights")
        Call<SimplePostResponse> flashLights(@Header("Authorization") String authHeader, @Path("id") long id);

        @POST("api/1/vehicles/{id}/command/honk_horn")
        Call<SimplePostResponse> honkHorn(@Header("Authorization") String authHeader, @Path("id") long id);

        @POST("api/1/vehicles/{id}/command/trunk_open")
        Call<SimplePostResponse> openTrunk(@Header("Authorization") String authHeader, @Path("id") long id, @Field("which_trunk") String which_trunk);
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

    public void getIsMobileAccessEnabled(final int index, final MutableLiveData<List<Vehicle>> vehiclesLiveData) {
        vehiclesService.getIsMobileAccessEnabled("bearer " + accessToken, vehiclesLiveData.getValue().get(index).getId()).enqueue(new Callback<MobileAccessEnableResponse>() {
            @Override
            public void onResponse(Call<MobileAccessEnableResponse> call, Response<MobileAccessEnableResponse> response) {
                if (response.isSuccessful()) {
                    MobileAccessEnableResponse mobileAccessEnableResponse = response.body();
                    if (mobileAccessEnableResponse != null) {
                        vehiclesLiveData.getValue().get(index).setMobileAccessEnabled(mobileAccessEnableResponse.getResponse());
                        vehiclesLiveData.setValue(vehiclesLiveData.getValue());
                    } else {
                        Logger.e("getIsMobileAccessEnabled null");
                    }
                } else if (response.code() == 401) {
                    Logger.e("auth issue? " + response.message());
                } else if (response.code() == 408) {
                    Logger.e("time out?" + response.message());
                } else {
                    Logger.e("onResponse with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MobileAccessEnableResponse> call, Throwable t) {
                Logger.e("onFailure");
            }
        });
    }

    public void getChargerState(final int index, final MutableLiveData<List<Vehicle>> vehiclesLiveData) {
        vehiclesService.getChargeState("bearer " + accessToken, vehiclesLiveData.getValue().get(index).getId()).enqueue(new Callback<ChargeStateResponse>() {
            @Override
            public void onResponse(Call<ChargeStateResponse> call, Response<ChargeStateResponse> response) {
                if (response.isSuccessful()) {
                    ChargeStateResponse chargeStateResponse = response.body();
                    if (chargeStateResponse != null) {
                        vehiclesLiveData.getValue().get(index).setChargeStateResponseObj(chargeStateResponse.getResponse());
                        vehiclesLiveData.setValue(vehiclesLiveData.getValue());
                    } else {
                        Logger.e("getChargeState null");
                    }
                } else if (response.code() == 401) {
                    Logger.e("auth issue? " + response.message());
                } else if (response.code() == 408) {
                    Logger.e("time out?" + response.message());
                } else {
                    Logger.e("onResponse with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChargeStateResponse> call, Throwable t) {
                Logger.e("onFailure");
            }
        });
    }

    public void wakeUp(final int index, final MutableLiveData<List<Vehicle>> vehiclesLiveData) {
        vehiclesService.wakeUp("bearer " + accessToken, vehiclesLiveData.getValue().get(index).getId()).enqueue(new Callback<SimplePostResponse>() {
            @Override
            public void onResponse(Call<SimplePostResponse> call, Response<SimplePostResponse> response) {
                if (response.isSuccessful()) {
                    SimplePostResponse simplePostResponse = response.body();
                    if (simplePostResponse != null) {
                        if (simplePostResponse.getResponse().getResult()) {
                            Logger.d("wake up cmd success");
                        } else {
                            Logger.e("wake up false");
                        }
                    } else {
                        Logger.e("simplePostResponse null");
                    }
                } else if (response.code() == 401) {
                    Logger.e("auth issue? " + response.message());
                } else if (response.code() == 408) {
                    Logger.e("time out?" + response.message());
                } else {
                    Logger.e("onResponse with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SimplePostResponse> call, Throwable t) {
                Logger.e("onFailure");
            }
        });
    }

    public void flashLights(final int index, final MutableLiveData<List<Vehicle>> vehiclesLiveData) {
        vehiclesService.flashLights("bearer " + accessToken, vehiclesLiveData.getValue().get(index).getId()).enqueue(new Callback<SimplePostResponse>() {
            @Override
            public void onResponse(Call<SimplePostResponse> call, Response<SimplePostResponse> response) {
                if (response.isSuccessful()) {
                    SimplePostResponse simplePostResponse = response.body();
                    if (simplePostResponse != null) {
                        if (simplePostResponse.getResponse().getResult()) {
                            Logger.d("flashLights cmd success");
                        } else {
                            Logger.e("flashLights false");
                        }
                    } else {
                        Logger.e("flashLights null");
                    }
                } else if (response.code() == 401) {
                    Logger.e("auth issue? " + response.message());
                } else if (response.code() == 408) {
                    Logger.e("time out?" + response.message());
                } else {
                    Logger.e("onResponse with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SimplePostResponse> call, Throwable t) {
                Logger.e("onFailure");
            }
        });
    }

    public void honkHorn(final int index, final MutableLiveData<List<Vehicle>> vehiclesLiveData) {
        vehiclesService.honkHorn("bearer " + accessToken, vehiclesLiveData.getValue().get(index).getId()).enqueue(new Callback<SimplePostResponse>() {
            @Override
            public void onResponse(Call<SimplePostResponse> call, Response<SimplePostResponse> response) {
                if (response.isSuccessful()) {
                    SimplePostResponse simplePostResponse = response.body();
                    if (simplePostResponse != null) {
                        if (simplePostResponse.getResponse().getResult()) {
                            Logger.d("honkHorn cmd success");
                        } else {
                            Logger.e("honkHorn false");
                        }
                    } else {
                        Logger.e("honkHorn null");
                    }
                } else if (response.code() == 401) {
                    Logger.e("auth issue? " + response.message());
                } else if (response.code() == 408) {
                    Logger.e("time out?" + response.message());
                } else {
                    Logger.e("onResponse with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SimplePostResponse> call, Throwable t) {
                Logger.e("onFailure");
            }
        });
    }

    public void openTrunk(final int index, final MutableLiveData<List<Vehicle>> vehiclesLiveData) {
        vehiclesService.openTrunk("bearer " + accessToken, vehiclesLiveData.getValue().get(index).getId(),"rear").enqueue(new Callback<SimplePostResponse>() {
            @Override
            public void onResponse(Call<SimplePostResponse> call, Response<SimplePostResponse> response) {
                if (response.isSuccessful()) {
                    SimplePostResponse simplePostResponse = response.body();
                    if (simplePostResponse != null) {
                        if (simplePostResponse.getResponse().getResult()) {
                            Logger.d("openTrunk cmd success");
                        } else {
                            Logger.e("openTrunk false");
                        }
                    } else {
                        Logger.e("openTrunk null");
                    }
                } else if (response.code() == 401) {
                    Logger.e("auth issue? " + response.message());
                } else if (response.code() == 408) {
                    Logger.e("time out?" + response.message());
                } else {
                    Logger.e("onResponse with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SimplePostResponse> call, Throwable t) {
                Logger.e("onFailure");
            }
        });
    }
}
