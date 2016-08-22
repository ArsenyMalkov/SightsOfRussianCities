package com.arsenymalkov.sightsofrussiancities.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsenymalkov.sightsofrussiancities.R;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        AndroidSightsSaxParser androidSightsSaxParser = new AndroidSightsSaxParser(responseBody.string());
//        List<Sight> sightsList = androidSightsSaxParser.parse();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
