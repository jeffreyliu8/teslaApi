package com.askjeffreyliu.teslaapi.viewmodel;

import android.app.Application;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.repository.VehicleRepository;


import java.util.List;

public class VehiclesViewModel extends AndroidViewModel {

    private LiveData<List<Vehicle>> mAllVehicles;


    public VehiclesViewModel(Application application) {
        super(application);
        VehicleRepository mRepository = new VehicleRepository(application);
        mAllVehicles = mRepository.getAllVehicles();
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return mAllVehicles;
    }

//    public void insert(List<RoomVehicle> vehicles) {
//        mRepository.insert(vehicles);
//    }
}