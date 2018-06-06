package com.askjeffreyliu.teslaapi;

import android.content.Context;

import com.askjeffreyliu.teslaapi.model.ChargeStateResponseObj;
import com.askjeffreyliu.teslaapi.model.Vehicle;


import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.askjeffreyliu.teslaapi", appContext.getPackageName());

        ChargeStateResponseObj a = new ChargeStateResponseObj();
        ChargeStateResponseObj b = new ChargeStateResponseObj();

        assertEquals(true, a.equals(b));

        Vehicle vehicle1 = new Vehicle(1, 2, "3");
        Vehicle vehicle2 = new Vehicle(1, 2, "3");

        vehicle1.setChargeStateResponseObj(a);
        vehicle2.setChargeStateResponseObj(b);

        a.setBattery_current(1.2f);
        b.setBattery_current(1.2f);

        assertEquals(true, vehicle1.equals(vehicle2));


        Vehicle vehicle3 = (Vehicle) vehicle1.clone();

        assertEquals(true, vehicle3.equals(vehicle1));


        vehicle2.setChargeStateResponseObj(null);

        assertEquals(true, vehicle3.equals(vehicle1));
    }
}
