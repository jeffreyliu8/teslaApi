package com.askjeffreyliu.teslaapi.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import android.os.AsyncTask;

import androidx.annotation.Nullable;


import com.askjeffreyliu.teslaapi.endpoint.VehiclesEndpoint;
import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.room.dao.VehicleDao;
import com.askjeffreyliu.teslaapi.room.db.VehicleRoomDatabase;

import com.orhanobut.logger.Logger;


import java.util.List;

public class VehicleRepository {
    private VehicleDao mVehicleDao;
    private VehiclesEndpoint endpoint;

    //    private LiveData<List<Vehicle>> resultLiveData = Transformations.switchMap(vehiclesFromNetwork, new Function<List<Vehicle>, LiveData<List<Vehicle>>>() {
//        @Override
//        public LiveData<List<Vehicle>> apply(List<Vehicle> input) {
//            if (input != null) {
//                Logger.d("update db");
//                insert(input);
//            }
//            return null;
//        }
//    });
    private MediatorLiveData<List<Vehicle>> vehiclesLiveData = new MediatorLiveData<>();

    public VehicleRepository(Application application, final VehiclesEndpoint endpoint) {
        this.endpoint = endpoint;
        VehicleRoomDatabase db = VehicleRoomDatabase.getDatabase(application);
        mVehicleDao = db.vehicleDao();
        LiveData<List<Vehicle>> vehiclesFromDb = mVehicleDao.getAllVehicles();

        LiveData<List<Vehicle>> vehiclesFromNetwork = endpoint.getVehicles();

        vehiclesLiveData.addSource(vehiclesFromDb, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable List<Vehicle> vehicles) {
                Logger.d("onChanged load from db");
                vehiclesLiveData.setValue(vehicles);
            }
        });

        vehiclesLiveData.addSource(vehiclesFromNetwork, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable List<Vehicle> vehicles) {
                // just update the database, view model will observe db changes
                if (vehicles != null) {
                    Logger.d("update db");
                    insert(vehicles); // update the whole table
                    vehiclesLiveData.setValue(vehicles); // need to make sure the following endpoints not have null live data
                    // make end point call for every item in the list? might be redundant if you only focus on 1 car
                    for (int i = 0; i < vehicles.size(); i++) {
                        endpoint.wakeUp(i, vehiclesLiveData);
                        endpoint.getIsMobileAccessEnabled(i, vehiclesLiveData);
                        endpoint.getChargerState(i, vehiclesLiveData);
                    }
                }
            }
        });
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return vehiclesLiveData;
    }

    public void honkHorn(int index) {
        endpoint.honkHorn(index, vehiclesLiveData);
    }

    public void insert(List<Vehicle> vehicles) {
        new insertAsyncTask(mVehicleDao).execute(vehicles);
    }

    private static class insertAsyncTask extends AsyncTask<List<Vehicle>, Void, Void> {

        private VehicleDao mAsyncTaskDao;

        insertAsyncTask(VehicleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Vehicle>... params) {
            mAsyncTaskDao.deleteAll(); // todo: worry about optimizing this later
            for (int i = 0; i < params[0].size(); i++) {
                mAsyncTaskDao.insert(params[0].get(i));
            }
            return null;
        }
    }
}
