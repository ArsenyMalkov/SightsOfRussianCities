package com.arsenymalkov.sightsofrussiancities.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arsenymalkov.sightsofrussiancities.R;
import com.arsenymalkov.sightsofrussiancities.network.RestClient;
import com.arsenymalkov.sightsofrussiancities.utils.xmlparser.AndroidSaxParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseCityFragment extends Fragment {

    private String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchRegions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);

//        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);

//        listView.setAdapter(adapter);

        return view;
    }

    public void fetchRegions() {
        HashMap<String, String> params = new HashMap<>();
        params.put("russia_travel_hash", "tohlUaNuaa7a9aa70a48c411f8e7ea9171d5c785");
        params.put("russia_travel_passwd", "greentoll");
        params.put("russia_travel_login", "madnessw");
        params.put("xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<request action=\"get-library\" type=\"addressRegion\" />");

        final rx.Observable<ResponseBody> call = RestClient.getRestApi().getRegions(params);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
//                progressBar.setVisibility(View.GONE);

                // Non-2XX http error happened
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    Response response = httpException.response();

                    switch (response.code()) {
                        // Hotel not found
                        case 404:
//                            SessionManager sessionManager = new SessionManager(getContext());
//                            sessionManager.logOut();
                            break;
                    }
//                    response.errorBody();
                }

                // A network error happened
                if (e instanceof IOException) {
//                    Toast.makeText(getContext(), R.string.error_network_check_internet, Toast.LENGTH_LONG).show();

//                    getActivity().getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.fragment_container, new ConnectionErrorFragment())
//                            .addToBackStack(null)
//                            .commit();
                }

                e.printStackTrace();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    AndroidSaxParser androidSaxParser = new AndroidSaxParser(responseBody.string());
                    List<Region> regionList = androidSaxParser.parseRegions();

                    List<String> stringList = new ArrayList<>();
                    for (Region region : regionList) {
                        stringList.add(region.getName());
                    }
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stringList);
                    listView.setAdapter(adapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    AndroidSaxParser androidSightsSaxParser = new AndroidSaxParser(responseBody.string());
//                    List<Sight> sightList = androidSightsSaxParser.parseSights();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

}
