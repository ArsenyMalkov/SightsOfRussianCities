package com.arsenymalkov.sightsofrussiancities.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.util.Log;

import com.arsenymalkov.sightsofrussiancities.R;
import com.arsenymalkov.sightsofrussiancities.network.RestClient;
import com.arsenymalkov.sightsofrussiancities.utils.xmlparser.AndroidSaxParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapFragment extends com.google.android.gms.maps.MapFragment implements OnMapReadyCallback {

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private GoogleMap googleMap;

    private List<Sight> sightList;

    private String selectedCity;

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getMapAsync(this);

        context = getActivity();

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                Log.d("TEST", "no permission");
                            } else {
                                lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                        googleApiClient);
                                if (lastLocation != null && googleMap != null) {
                                    Log.d("TEST", "long:"+lastLocation.getLongitude()+"lat:"+lastLocation.getLatitude());
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 13));
                                    fetchSightsCity();
                                } else {
                                    Log.d("TEST", "last loc null");
                                }
                            }
                            if (selectedCity != null) {
                                fetchSights();
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            Log.d("TEST", "conn suspended");
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Log.d("TEST", "conn failed");
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        } else {
            Log.d("TEST", "googleApiClient null");
        }
    }

    @Override
    public void onStart() {
        googleApiClient.connect();

        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();

        super.onStop();
    }

    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if (lastLocation != null && selectedCity == null) {
                        fetchSights();
                    }
                    return false;
                }
            });
        } else {
            // TODO request permission
            // Show rationale and request permission.
        }

        this.googleMap = googleMap;

        if (lastLocation != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 13));
        }

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    public void fetchSights() {
        HashMap<String, String> params = new HashMap<>();
        params.put("russia_travel_hash", getString(R.string.russia_travel_hash));
        params.put("russia_travel_passwd", getString(R.string.russia_travel_passwd));
        params.put("russia_travel_login", getString(R.string.russia_travel_login));
        params.put("xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<request action=\"get-objects-for-update\" >" +
                "<point radius=\"1\">"+lastLocation.getLatitude()+","+lastLocation.getLongitude()+"</point>" +//52.276941,104.282650
                "<attributes>" +
                "<name/>" +
                "<url/>" +
                "<photos/>" +
                "<streetAddress/>" +
                "<openingHours/>" +
                "<telephone/>" +
                "</attributes>" +
                "</request>");

        final rx.Observable<ResponseBody> call = RestClient.getRestApi().getSights(params);
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
                googleMap.clear();

                LatLng myCoordinates = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                googleMap.addCircle(new CircleOptions()
                        .strokeColor(ContextCompat.getColor(context, R.color.colorAccent))
                        .center(myCoordinates)
                        .radius(1000) // meters
                );

                try {
                    AndroidSaxParser androidSightsSaxParser = new AndroidSaxParser(responseBody.string());
                    List<Sight> sightList = androidSightsSaxParser.parseSights();

                    for (Sight sight : sightList) {
//                        if (sight == currentSight) {
//                            continue;
//                        }

                        Pair<Double, Double> coordinates = getCoordinates(sight.getGeo());
                        LatLng sightPosition = new LatLng(coordinates.first, coordinates.second);

                        googleMap.addMarker(new MarkerOptions()
                                .position(sightPosition)
                                // TODO "&quot;" in text
                                .title(sight.getName())
                        );
                    }
//                    adapter.swap(sightsList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchSightsCity() {

    }

    public Pair<Double, Double> getCoordinates(String geo) {
        String[] geoArray = geo.split(",");
        double latitude = Double.parseDouble(geoArray[0].trim());
        double longitude = Double.parseDouble(geoArray[1].trim());

        return Pair.create(latitude, longitude);
    }

}
