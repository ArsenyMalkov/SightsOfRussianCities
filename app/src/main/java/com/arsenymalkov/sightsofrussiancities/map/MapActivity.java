package com.arsenymalkov.sightsofrussiancities.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arsenymalkov.sightsofrussiancities.R;

public class MapActivity extends AppCompatActivity {

    public static final String SELECTED_CITY = "city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MapFragment mapFragment = new MapFragment();
        if (getIntent() != null) {
            String selectedCity = getIntent().getStringExtra(SELECTED_CITY);
            mapFragment.setSelectedCity(selectedCity);
        }

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, mapFragment)
                    .commit();
        }
    }

}
