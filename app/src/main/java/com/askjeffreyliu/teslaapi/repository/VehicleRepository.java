package com.askjeffreyliu.teslaapi.repository;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;

import android.arch.lifecycle.Transformations;
import android.os.AsyncTask;


import com.askjeffreyliu.teslaapi.Constant;
import com.askjeffreyliu.teslaapi.endpoint.VehiclesEndpoint;
import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.room.dao.VehicleDao;
import com.askjeffreyliu.teslaapi.room.db.VehicleRoomDatabase;

import com.orhanobut.logger.Logger;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {
    private VehicleDao mVehicleDao;
    private LiveData<List<Vehicle>> mAllVehicles;
    private LiveData<List<Vehicle>> vehiclesLiveData = new VehiclesEndpoint(Prefs.getString(Constant.ACCESS_TOKEN, null)).getVehicles();
    private LiveData<List<Vehicle>> resultLiveData = Transformations.switchMap(vehiclesLiveData, new Function<List<Vehicle>, LiveData<List<Vehicle>>>() {
        @Override
        public LiveData<List<Vehicle>> apply(List<Vehicle> input) {
            if (input != null) {
                Logger.d("update db");
                insert(input);
            }
            return null;
        }
    });

    public VehicleRepository(Application application) {
        VehicleRoomDatabase db = VehicleRoomDatabase.getDatabase(application);
        mVehicleDao = db.vehicleDao();
        mAllVehicles = mVehicleDao.getAllVehicles();
    }

    public LiveData<List<Vehicle>> getAllDbVehicles() {
        return mAllVehicles;
    }

    public LiveData<List<Vehicle>> getAllNetworkVehicles() {
        return resultLiveData;
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
