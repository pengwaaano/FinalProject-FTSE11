package com.jacobgreenland.finalproject.league;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.Constants;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;

import java.util.List;

/**
 * Created by Jacob on 28/06/16.
 */
public class LeagueFragment extends Fragment implements LeagueContract.View {

    View v;
    LeaguePresenter fPresenter;
    Communicator comm;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.leaguefragment,container,false);
        setRetainInstance(true);
        comm = (Communicator) getActivity();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //header.attachTo(rv);

        fPresenter = new LeaguePresenter(this, MainActivity.leagueRepository);
        fPresenter.loadLeagues(MainActivity._api, false, Constants.CURRENT_SEASON);
    }

    @Override
    public void setPresenter(LeagueContract.Presenter presenter) {

    }

    @Override
    public void setAdapters(List<League> results, boolean fromAPI) {

    }

    @Override
    public void showDialog() {
        comm.initialiseNavigationDrawer();
    }
}
