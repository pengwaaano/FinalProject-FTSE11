package com.jacobgreenland.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jacob on 29/06/16.
 */
public class MainFragment extends Fragment {
    View v;
    Communicator comm;
    Button donateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v =inflater.inflate(R.layout.mainfragment,container,false);
        setRetainInstance(true);
        comm = (Communicator) getActivity();

        donateButton = (Button) v.findViewById(R.id.mDonate);

        donateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), DonationsActivity.class);
                //startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //header.attachTo(rv);

        comm.initialiseNavigationDrawer();
        comm.initialiseTwitter();
        comm.initialiseFacebook();
        //fPresenter = new LeaguePresenter(this, MainActivity.leagueRepository);
        //fPresenter.loadLeagues(MainActivity._api, false, Constants.CURRENT_SEASON);
    }
}
