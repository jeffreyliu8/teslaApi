package com.askjeffreyliu.teslaapi.model;

import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.List;

public class ChargeStateResponseObj implements Cloneable {
    private String charging_state;
    private boolean charge_to_max_range;
    private long max_range_charge_counter;
    private boolean fast_charger_present;
    private float battery_range;
    private float est_battery_range;
    private float ideal_battery_range;
    private int battery_level;
    private float battery_current;
    private String charge_starting_range;
    private String charge_starting_soc;
    private int charger_voltage;
    private int charger_pilot_current;
    private int charger_actual_current;
    private int charger_power;
    private String time_to_full_charge;
    private float charge_rate;
    private boolean charge_port_door_open;

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
                charge_to_max_range,
                max_range_charge_counter,
                fast_charger_present,
                battery_range,
                est_battery_range,
                ideal_battery_range,
                battery_level,
                battery_current,
                charge_starting_range,
                charge_starting_soc,
                charger_voltage,
                charger_pilot_current,
                charger_actual_current,
                charger_power,
                time_to_full_charge,
                charge_rate,
                charge_port_door_open
        );
    }

    public String getCharging_state() {
        return charging_state;
    }

    public void setCharging_state(String charging_state) {
        this.charging_state = charging_state;
    }

    public boolean isCharge_to_max_range() {
        return charge_to_max_range;
    }

    public void setCharge_to_max_range(boolean charge_to_max_range) {
        this.charge_to_max_range = charge_to_max_range;
    }

    public long getMax_range_charge_counter() {
        return max_range_charge_counter;
    }

    public void setMax_range_charge_counter(long max_range_charge_counter) {
        this.max_range_charge_counter = max_range_charge_counter;
    }

    public boolean isFast_charger_present() {
        return fast_charger_present;
    }

    public void setFast_charger_present(boolean fast_charger_present) {
        this.fast_charger_present = fast_charger_present;
    }

    public float getBattery_range() {
        return battery_range;
    }

    public void setBattery_range(float battery_range) {
        this.battery_range = battery_range;
    }

    public float getEst_battery_range() {
        return est_battery_range;
    }

    public void setEst_battery_range(float est_battery_range) {
        this.est_battery_range = est_battery_range;
    }

    public float getIdeal_battery_range() {
        return ideal_battery_range;
    }

    public void setIdeal_battery_range(float ideal_battery_range) {
        this.ideal_battery_range = ideal_battery_range;
    }

    public int getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(int battery_level) {
        this.battery_level = battery_level;
    }

    public float getBattery_current() {
        return battery_current;
    }

    public void setBattery_current(float battery_current) {
        this.battery_current = battery_current;
    }

    public String getCharge_starting_range() {
        return charge_starting_range;
    }

    public void setCharge_starting_range(String charge_starting_range) {
        this.charge_starting_range = charge_starting_range;
    }

    public String getCharge_starting_soc() {
        return charge_starting_soc;
    }

    public void setCharge_starting_soc(String charge_starting_soc) {
        this.charge_starting_soc = charge_starting_soc;
    }

    public int getCharger_voltage() {
        return charger_voltage;
    }

    public void setCharger_voltage(int charger_voltage) {
        this.charger_voltage = charger_voltage;
    }

    public int getCharger_pilot_current() {
        return charger_pilot_current;
    }

    public void setCharger_pilot_current(int charger_pilot_current) {
        this.charger_pilot_current = charger_pilot_current;
    }

    public int getCharger_actual_current() {
        return charger_actual_current;
    }

    public void setCharger_actual_current(int charger_actual_current) {
        this.charger_actual_current = charger_actual_current;
    }

    public int getCharger_power() {
        return charger_power;
    }

    public void setCharger_power(int charger_power) {
        this.charger_power = charger_power;
    }

    public String getTime_to_full_charge() {
        return time_to_full_charge;
    }

    public void setTime_to_full_charge(String time_to_full_charge) {
        this.time_to_full_charge = time_to_full_charge;
    }

    public float getCharge_rate() {
        return charge_rate;
    }

    public void setCharge_rate(float charge_rate) {
        this.charge_rate = charge_rate;
    }

    public boolean isCharge_port_door_open() {
        return charge_port_door_open;
    }

    public void setCharge_port_door_open(boolean charge_port_door_open) {
        this.charge_port_door_open = charge_port_door_open;
    }
}
