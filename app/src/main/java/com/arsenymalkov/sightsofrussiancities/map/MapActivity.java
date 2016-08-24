package com.arsenymalkov.sightsofrussiancities.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arsenymalkov.sightsofrussiancities.R;

public class MapActivity extends AppCompatActivity {

    public static final String SEARCH_CITY = "SEARCH_CITY";

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
            boolean searchCity = getIntent().getBooleanExtra(SEARCH_CITY, true);
            mapFragment.setSearchCity(searchCity);
        }

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, mapFragment)
                    .commit();
        }
    }

}
