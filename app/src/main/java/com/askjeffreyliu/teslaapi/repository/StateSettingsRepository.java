package com.askjeffreyliu.teslaapi.repository;

import android.app.Application;

import com.askjeffreyliu.teslaapi.endpoint.VehiclesEndpoint;

import androidx.lifecycle.LiveData;


public class StateSettingsRepository {

    private VehiclesEndpoint endpoint;


    public StateSettingsRepository(Application application, VehiclesEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public LiveData<Boolean> getIsMobileAccessEnabled(long id) {
        return endpoint.getIsMobileAccessEnabled(id);
    }
}
