package com.askjeffreyliu.teslaapi.repository;

import android.app.Application;

import com.askjeffreyliu.teslaapi.endpoint.VehiclesEndpoint;
import com.askjeffreyliu.teslaapi.model.ChargeStateResponseObj;

import androidx.lifecycle.LiveData;


public class StateSettingsRepository {

    private VehiclesEndpoint endpoint;


    public StateSettingsRepository(Application application, VehiclesEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public LiveData<Boolean> getIsMobileAccessEnabled(long id) {
        return endpoint.getIsMobileAccessEnabled(id);
    }

    public LiveData<ChargeStateResponseObj> getChargerState(long id) {
        return endpoint.getChargerState(id);
    }
}
