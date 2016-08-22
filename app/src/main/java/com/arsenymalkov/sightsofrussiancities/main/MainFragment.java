package com.arsenymalkov.sightsofrussiancities.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsenymalkov.sightsofrussiancities.R;
import com.arsenymalkov.sightsofrussiancities.map.MapActivity;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        AndroidSightsSaxParser androidSightsSaxParser = new AndroidSightsSaxParser(responseBody.string());
//        List<Sight> sightsList = androidSightsSaxParser.parse();
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        CardView cvMyLocation = (CardView) view.findViewById(R.id.cv_my_location);
        cvMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra("search_city", false);
                startActivity(intent);
            }
        });

        return view;
    }

}
