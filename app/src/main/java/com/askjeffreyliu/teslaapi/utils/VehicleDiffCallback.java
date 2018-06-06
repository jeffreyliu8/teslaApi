package com.askjeffreyliu.teslaapi.utils;

import com.askjeffreyliu.teslaapi.model.Vehicle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class VehicleDiffCallback extends DiffUtil.Callback {

    private final List<Vehicle> mOldVehicleList;
    private final List<Vehicle> mNewVehicleList;

    public VehicleDiffCallback(List<Vehicle> oldVehicleList, List<Vehicle> newVehicleList) {
        this.mOldVehicleList = oldVehicleList;
        this.mNewVehicleList = newVehicleList;
    }

    @Override
    public int getOldListSize() {
        if (mOldVehicleList == null) {
            return 0;
        }
        return mOldVehicleList.size();
    }

    @Override
    public int getNewListSize() {
        if (mNewVehicleList == null) {
            return 0;
        }
        return mNewVehicleList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldVehicleList.get(oldItemPosition).getId() == mNewVehicleList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Vehicle oldVehicle = mOldVehicleList.get(oldItemPosition);
        final Vehicle newVehicle = mNewVehicleList.get(newItemPosition);
        return oldVehicle.equals(newVehicle);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}