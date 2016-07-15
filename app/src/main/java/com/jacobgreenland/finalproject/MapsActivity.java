package com.jacobgreenland.finalproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    View v;
    List<Address> adresses;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_maps, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        return v;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        adresses = new ArrayList<Address>();


        Address addr = MainActivity.stadiumAddress;

        LatLng myLocation = new LatLng(addr.getLatitude(), addr.getLongitude());
        mMap.addMarker(new MarkerOptions().position(myLocation).title(addr.getFeatureName()));
        //mMap.animateCamera( CameraUpdateFactory.zoomTo(10.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

        LocationManager lm = (LocationManager) v.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        GoogleDirection.withServerKey("AIzaSyAPbKkw2VAS8lftQfUkJSJkOHsKPG0CpHg")
                .from(new LatLng(location.getLatitude(), location.getLongitude()))
                .to(new LatLng(myLocation.latitude,myLocation.longitude))
                .avoid(AvoidType.FERRIES)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            Log.d("direction", "pass");
                            // Do something
                        } else {
                            Log.d("direction", "not good");
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                        Log.d("direction", "failed");
                    }
                });
        /*if (geoResults.size()>0) {

        }
        else
        {
            Snackbar snackbar = Snackbar
                    .make(MainActivity.mainFrame, "Location Not Found.", Snackbar.LENGTH_INDEFINITE);
        }

        // Add a marker in Sydney and move the camera
*/
    }
}

/*
 class GetGeocodeLocation extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String stadiumSearch;
    private GoogleMap mMap;
    Geocoder gc;

    public GetGeocodeLocation(Context context, String stadium, GoogleMap map) {
        this.context = context;
        this.stadiumSearch = stadium;
        gc = new Geocoder(context, Locale.ENGLISH);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        try {
            Log.d("map test","try to get location");
            List<Address> geoResults = gc.getFromLocationName(stadiumSearch, 1);
            while (geoResults.size()==0) {
                geoResults = gc.getFromLocationName(stadiumSearch, 1);
            }
            if (geoResults.size()>0) {
                Address addr = geoResults.get(0);

                LatLng myLocation = new LatLng(addr.getLatitude(),addr.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLocation).title(addr.getFeatureName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Update the view
        super.onPostExecute(result);
        //updateImageView(drawable);
        //this.progressBar.setVisibility(View.GONE);
    }
}*/

