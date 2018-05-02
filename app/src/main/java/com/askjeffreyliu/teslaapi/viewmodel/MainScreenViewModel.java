package com.askjeffreyliu.teslaapi.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.askjeffreyliu.teslaapi.Constant;

import com.askjeffreyliu.teslaapi.endpoint.VehiclesEndpoint;
import com.pixplicity.easyprefs.library.Prefs;

public class MainScreenViewModel extends ViewModel {

    private LiveData<String> vehiclesLiveData;


    private VehiclesEndpoint vehiclesEndpoint;

    public MainScreenViewModel() {
        vehiclesEndpoint = new VehiclesEndpoint(Prefs.getString(Constant.ACCESS_TOKEN, null));
        vehiclesLiveData = new MutableLiveData<>();
    }

    public void getVehiclesLiveData() {
        vehiclesLiveData = vehiclesEndpoint.getVehicles();
    }

    public LiveData<String> getLiveData() {
        return vehiclesLiveData;
    }
}