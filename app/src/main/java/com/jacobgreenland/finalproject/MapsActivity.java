package com.jacobgreenland.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doctoror.geocoder.Address;
import com.doctoror.geocoder.Geocoder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    View v;
    List<Address> adresses;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_maps,container,false);
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

        String stadium = "stadium: Tottenham Hotspur";
        //String stadium = MainActivity.chosenTeamObject.getName();

        new GetGeocodeLocation(getContext(),"stadium: Tottenham Hotspur",mMap);
        // Add a marker in Sydney and move the camera

    }
}

 class GetGeocodeLocation extends AsyncTask<String, Void, Drawable> {

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
    protected Drawable doInBackground(String... params)
    {
        try {
            List<Address> geoResults = gc.getFromLocationName(stadiumSearch,1,false);
            while (geoResults.size()==0) {
                geoResults = gc.getFromLocationName(stadiumSearch, 1, false);
            }
            if (geoResults.size()>0) {
                Address addr = geoResults.get(0);

                LatLng myLocation = new LatLng(addr.getLocation().latitude,addr.getLocation().longitude);
                mMap.addMarker(new MarkerOptions().position(myLocation).title(addr.getStreetAddress()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        // Update the view
        updateImageView(drawable);
        //this.progressBar.setVisibility(View.GONE);
    }

    @SuppressLint("NewApi")
    private void updateImageView(Drawable drawable) {

    }
}

