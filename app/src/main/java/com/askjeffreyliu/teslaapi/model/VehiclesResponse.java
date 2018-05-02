package com.askjeffreyliu.teslaapi.model;

import java.util.List;

public class VehiclesResponse {
    private List<Vehicle> response;
    private int count;

    public List<Vehicle> getResponse() {
        return response;
    }

    public int getCount() {
        return count;
    }
}
