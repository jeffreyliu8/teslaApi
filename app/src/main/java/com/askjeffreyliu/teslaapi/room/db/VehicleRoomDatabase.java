package com.askjeffreyliu.teslaapi.room.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.room.dao.VehicleDao;


@Database(entities = {Vehicle.class}, version = 1, exportSchema = false)
public abstract class VehicleRoomDatabase extends RoomDatabase {

    public abstract VehicleDao vehicleDao();

    private static VehicleRoomDatabase INSTANCE;

    public static VehicleRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VehicleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VehicleRoomDatabase.class, "vehicle_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}