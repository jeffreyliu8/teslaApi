package com.askjeffreyliu.teslaapi.activity;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.askjeffreyliu.teslaapi.R;
import com.askjeffreyliu.teslaapi.adapter.MainScreenRecyclerAdapter;
import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.viewmodel.VehiclesViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private MainScreenRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        com.google.android.material.floatingactionbutton.FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                viewModel.getVehiclesLiveData();
            }
        });

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MainScreenRecyclerAdapter();
        mRecyclerView.setAdapter(adapter);

        setDataListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDataListener() {
        // Get a new or existing ViewModel from the ViewModelProvider.
        final VehiclesViewModel viewModel = ViewModelProviders.of(this).get(VehiclesViewModel.class);

        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        viewModel.getAllVehicles().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable final List<Vehicle> vehicles) {
                adapter.setList(vehicles);
            }
        });
    }
}
