package com.askjeffreyliu.teslaapi.model;

import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.List;

public class ChargeStateResponseObj implements Cloneable {
    private String charging_state;
    private String fast_charger_type;
    private String fast_charger_brand;
    private int charge_limit_soc;
    private int charge_limit_soc_std;
    private int charge_limit_soc_min;
    private int charge_limit_soc_max;
    private boolean charge_to_max_range;
    private long max_range_charge_counter;
    private boolean fast_charger_present;
    private float battery_range;
    private float est_battery_range;
    private float ideal_battery_range;
    private int battery_level;
    private int usable_battery_level;
    private float charge_energy_added;
    private float charge_miles_added_rated;
    private float charge_miles_added_ideal;
    private int charger_voltage;
    private int charger_pilot_current;
    private int charger_actual_current;
    private int charger_power;
    private float time_to_full_charge;
    private boolean trip_charging;
    private float charge_rate;
    private boolean charge_port_door_open;
    private String conn_charge_cable;
    private String scheduled_charging_start_time;
    private boolean scheduled_charging_pending;
    private String user_charge_enable_request;
    private boolean charge_enable_request;
    private String charger_phases;
    private String charge_port_latch;
    private int charge_current_request;
    private int charge_current_request_max;
    private boolean managed_charging_active;
    private boolean managed_charging_user_canceled;
    private String managed_charging_start_time;
    private boolean battery_heater_on;
    private boolean not_enough_power_to_heat;
    private long timestamp;

    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Logger.e(e.toString());
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ChargeStateResponseObj)
                && ((ChargeStateResponseObj) obj).values().equals(values());
    }

    private List<?> values() {
        return Arrays.asList(
                charging_state,
                fast_charger_type,
                fast_charger_brand,
                charge_limit_soc,
                charge_limit_soc_std,
                charge_limit_soc_min,
                charge_limit_soc_max,
                charge_to_max_range,
                max_range_charge_counter,
                fast_charger_present,
                battery_range,
                est_battery_range,
                ideal_battery_range,
                battery_level,
                usable_battery_level,
                charge_energy_added,
                charge_miles_added_rated,
                charge_miles_added_ideal,
                charger_voltage,
                charger_pilot_current,
                charger_actual_current,
                charger_power,
                time_to_full_charge,
                trip_charging,
                charge_rate,
                charge_port_door_open,
                conn_charge_cable,
                scheduled_charging_start_time,
                scheduled_charging_pending,
                user_charge_enable_request,
                charge_enable_request,
                charger_phases,
                charge_port_latch,
                charge_current_request,
                charge_current_request_max,
                managed_charging_active,
                managed_charging_user_canceled,
                managed_charging_start_time,
                battery_heater_on,
                not_enough_power_to_heat,
                timestamp
        );
    }

    public String getCharging_state() {
        return charging_state;
    }

    public String getFast_charger_type() {
        return fast_charger_type;
    }

    public String getFast_charger_brand() {
        return fast_charger_brand;
    }

    public int getCharge_limit_soc() {
        return charge_limit_soc;
    }

    public int getCharge_limit_soc_std() {
        return charge_limit_soc_std;
    }

    public int getCharge_limit_soc_min() {
        return charge_limit_soc_min;
    }

    public int getCharge_limit_soc_max() {
        return charge_limit_soc_max;
    }

    public boolean isCharge_to_max_range() {
        return charge_to_max_range;
    }

    public long getMax_range_charge_counter() {
        return max_range_charge_counter;
    }

    public boolean isFast_charger_present() {
        return fast_charger_present;
    }

    public float getBattery_range() {
        return battery_range;
    }

    public float getEst_battery_range() {
        return est_battery_range;
    }

    public float getIdeal_battery_range() {
        return ideal_battery_range;
    }

    public int getBattery_level() {
        return battery_level;
    }

    public int getUsable_battery_level() {
        return usable_battery_level;
    }

    public float getCharge_energy_added() {
        return charge_energy_added;
    }

    public float getCharge_miles_added_rated() {
        return charge_miles_added_rated;
    }

    public float getCharge_miles_added_ideal() {
        return charge_miles_added_ideal;
    }

    public int getCharger_voltage() {
        return charger_voltage;
    }

    public int getCharger_pilot_current() {
        return charger_pilot_current;
    }

    public int getCharger_actual_current() {
        return charger_actual_current;
    }

    public int getCharger_power() {
        return charger_power;
    }

    public float getTime_to_full_charge() {
        return time_to_full_charge;
    }

    public boolean isTrip_charging() {
        return trip_charging;
    }

    public float getCharge_rate() {
        return charge_rate;
    }

    public boolean isCharge_port_door_open() {
        return charge_port_door_open;
    }

    public String getConn_charge_cable() {
        return conn_charge_cable;
    }

    public String getScheduled_charging_start_time() {
        return scheduled_charging_start_time;
    }

    public boolean isScheduled_charging_pending() {
        return scheduled_charging_pending;
    }

    public String getUser_charge_enable_request() {
        return user_charge_enable_request;
    }

    public boolean isCharge_enable_request() {
        return charge_enable_request;
    }

    public String getCharger_phases() {
        return charger_phases;
    }

    public String getCharge_port_latch() {
        return charge_port_latch;
    }

    public int getCharge_current_request() {
        return charge_current_request;
    }

    public int getCharge_current_request_max() {
        return charge_current_request_max;
    }

    public boolean isManaged_charging_active() {
        return managed_charging_active;
    }

    public boolean isManaged_charging_user_canceled() {
        return managed_charging_user_canceled;
    }

    public String getManaged_charging_start_time() {
        return managed_charging_start_time;
    }

    public boolean isBattery_heater_on() {
        return battery_heater_on;
    }

    public boolean isNot_enough_power_to_heat() {
        return not_enough_power_to_heat;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
