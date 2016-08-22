package com.arsenymalkov.sightsofrussiancities.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends com.google.android.gms.maps.MapFragment implements OnMapReadyCallback {

    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    boolean searchCity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getMapAsync(this);

//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(mLocationRequest);
//        PendingResult<LocationSettingsResult> result =
//                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
//                        builder.build());

//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(getContext())
//                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                        @Override
//                        public void onConnected(@Nullable Bundle bundle) {
//                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                Log.d("TEST", "no permission");
//                            } else {
//                                lastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                                        googleApiClient);
//                                if (lastLocation != null) {
////                                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
////                                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));ds
//                                    Log.d("TEST", "long:"+lastLocation.getLongitude()+"lat:"+lastLocation.getLatitude());
//                                } else {
//                                    Log.d("TEST", "last loc null");
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onConnectionSuspended(int i) {
//                            Log.d("TEST", "conn suspended");
//                        }
//                    })
//                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
//                        @Override
//                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                            Log.d("TEST", "conn failed");
//                        }
//                    })
//                    .addApi(LocationServices.API)
//                    .build();
//        } else {
//            Log.d("TEST", "googleApiClient null");
//        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_map, container, false);
//    }

    @Override
    public void onStart() {
//        googleApiClient.connect();

        super.onStart();
    }

    @Override
    public void onStop() {
//        googleApiClient.disconnect();

        super.onStop();
    }

    public void setSearchCity(boolean searchCity) {
        this.searchCity = searchCity;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//        } else {
//            // TODO request permission
//            // Show rationale and request permission.
//        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0,0), 13));
    }
}
