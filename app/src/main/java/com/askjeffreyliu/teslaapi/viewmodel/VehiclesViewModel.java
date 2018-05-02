package com.askjeffreyliu.teslaapi.viewmodel;

import android.app.Application;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.repository.VehicleRepository;


import java.util.List;

public class VehiclesViewModel extends AndroidViewModel {

    private LiveData<List<Vehicle>> mAllDbVehicles;
    private LiveData<List<Vehicle>> mAllNetworkVehicles;

    public VehiclesViewModel(Application application) {
        super(application);
        VehicleRepository mRepository = new VehicleRepository(application);
        mAllDbVehicles = mRepository.getAllDbVehicles();
        mAllNetworkVehicles = mRepository.getAllNetworkVehicles();
    }

    public LiveData<List<Vehicle>> getAllDbVehicles() {
        return mAllDbVehicles;
    }

    public LiveData<List<Vehicle>> getAllNetworkVehicles() {
        return mAllNetworkVehicles;
    }

//    public void insert(List<RoomVehicle> vehicles) {
//        mRepository.insert(vehicles);
//    }
}