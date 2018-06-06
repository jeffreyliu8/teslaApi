package com.askjeffreyliu.teslaapi.model;


public class ChargeStateResponseObj implements Cloneable{
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

    public String getCharging_state() {
        return charging_state;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
