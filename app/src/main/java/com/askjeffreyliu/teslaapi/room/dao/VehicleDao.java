package com.askjeffreyliu.teslaapi.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.askjeffreyliu.teslaapi.model.Vehicle;


import java.util.List;


@Dao
public interface VehicleDao {

    @Insert
    void insert(Vehicle roomVehicle);

    @Query("DELETE FROM vehicles_table")
    void deleteAll();

    @Query("SELECT * from vehicles_table ORDER BY vehicle_id ASC")
    LiveData<List<Vehicle>> getAllVehicles();
}
