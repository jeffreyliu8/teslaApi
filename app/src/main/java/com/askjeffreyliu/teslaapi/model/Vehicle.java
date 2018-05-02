package com.askjeffreyliu.teslaapi.model;

import com.google.gson.annotations.SerializedName;

public class Vehicle {
    private String id;
    @SerializedName("vehicle_id")
    private String vehicleId;
    private String vin;
    private String display_name;
    private String color;
    private String state;
    private String in_service;
    private String id_s;
    private boolean remote_start_enabled;
    private boolean calendar_enabled;
    private boolean notifications_enabled;
    private String backseat_token;
    private String backseat_token_updated_at;

    public String getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getVin() {
        return vin;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getColor() {
        return color;
    }

    public String getState() {
        return state;
    }

    public String getIn_service() {
        return in_service;
    }

    public String getId_s() {
        return id_s;
    }

    public boolean isRemote_start_enabled() {
        return remote_start_enabled;
    }

    public boolean isCalendar_enabled() {
        return calendar_enabled;
    }

    public boolean isNotifications_enabled() {
        return notifications_enabled;
    }

    public String getBackseat_token() {
        return backseat_token;
    }

    public String getBackseat_token_updated_at() {
        return backseat_token_updated_at;
    }
}
