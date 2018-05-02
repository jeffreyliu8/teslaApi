package com.askjeffreyliu.teslaapi.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;


import com.askjeffreyliu.teslaapi.Constant;
import com.askjeffreyliu.teslaapi.endpoint.VehiclesEndpoint;
import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.room.dao.VehicleDao;
import com.askjeffreyliu.teslaapi.room.db.VehicleRoomDatabase;

import com.orhanobut.logger.Logger;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class VehicleRepository {
    private VehicleDao mVehicleDao;
    private LiveData<List<Vehicle>> vehiclesFromDb;
    private LiveData<List<Vehicle>> vehiclesFromNetwork = new VehiclesEndpoint(Prefs.getString(Constant.ACCESS_TOKEN, null)).getVehicles();
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

    public VehicleRepository(Application application) {
        VehicleRoomDatabase db = VehicleRoomDatabase.getDatabase(application);
        mVehicleDao = db.vehicleDao();
        vehiclesFromDb = mVehicleDao.getAllVehicles();

        vehiclesLiveData.addSource(vehiclesFromDb, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable List<Vehicle> vehicles) {
                Logger.d("onChanged");
                vehiclesLiveData.setValue(vehicles);
            }
        });

        vehiclesLiveData.addSource(vehiclesFromNetwork, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable List<Vehicle> vehicles) {
                // just update the database, view model will observe db changes
                if (vehicles != null) {
                    Logger.d("update db");
                    insert(vehicles);
                }
            }
        });
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return vehiclesLiveData;
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
            mAsyncTaskDao.deleteAll();
            for (int i = 0; i < params[0].size(); i++) {
                mAsyncTaskDao.insert(params[0].get(i));
            }
            return null;
        }
    }
}
