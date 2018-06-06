package com.askjeffreyliu.teslaapi.utils;

import com.askjeffreyliu.teslaapi.model.Vehicle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class VehicleDiffCallback extends DiffUtil.Callback {

    private final List<Vehicle> mOldEmployeeList;
    private final List<Vehicle> mNewEmployeeList;

    public VehicleDiffCallback(List<Vehicle> oldEmployeeList, List<Vehicle> newEmployeeList) {
        this.mOldEmployeeList = oldEmployeeList;
        this.mNewEmployeeList = newEmployeeList;
    }

    @Override
    public int getOldListSize() {
        if (mOldEmployeeList == null) {
            return 0;
        }
        return mOldEmployeeList.size();
    }

    @Override
    public int getNewListSize() {
        if (mNewEmployeeList == null) {
            return 0;
        }
        return mNewEmployeeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldEmployeeList.get(oldItemPosition).getId() == mNewEmployeeList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Vehicle oldEmployee = mOldEmployeeList.get(oldItemPosition);
        final Vehicle newEmployee = mNewEmployeeList.get(newItemPosition);
        return oldEmployee.equals(newEmployee); // todo: check equal deep compare
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}