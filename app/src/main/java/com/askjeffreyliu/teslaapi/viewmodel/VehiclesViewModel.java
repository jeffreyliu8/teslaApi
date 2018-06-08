package com.askjeffreyliu.teslaapi.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.askjeffreyliu.teslaapi.Constant;
import com.askjeffreyliu.teslaapi.endpoint.VehiclesEndpoint;

import com.askjeffreyliu.teslaapi.model.Vehicle;

import com.askjeffreyliu.teslaapi.repository.VehicleRepository;
import com.pixplicity.easyprefs.library.Prefs;


import java.util.List;

public class VehiclesViewModel extends AndroidViewModel {

    private LiveData<List<Vehicle>> mAllVehicles;
    private VehicleRepository mRepository;

    public VehiclesViewModel(Application application) {
        super(application);
        VehiclesEndpoint endpoint = new VehiclesEndpoint(Prefs.getString(Constant.ACCESS_TOKEN, null));
        mRepository = new VehicleRepository(application, endpoint);
        mAllVehicles = mRepository.getAllVehicles();
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return mAllVehicles;
    }

    public void honkHorn(int index) {
        mRepository.honkHorn(index);
    }
}