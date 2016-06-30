package com.jacobgreenland.finalproject.league;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;

/**
 * Created by Jacob on 28/06/16.
 */
public class LeagueFragment extends Fragment implements LeagueContract.View {

    View v;
    LeaguePresenter fPresenter;
    Communicator comm;
    RecyclerView rv;
    TextView leagueName;
    RecyclerViewHeader header;
    LeagueAdapter leagueAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v =inflater.inflate(R.layout.leaguetable,container,false);
        setRetainInstance(true);
        comm = (Communicator) getActivity();

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        leagueName = (TextView) v.findViewById(R.id.ltLeagueName);
        rv = (RecyclerView) v.findViewById(R.id.leagueTable);

        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        //header.attachTo(rv);

        fPresenter = new LeaguePresenter(this, MainActivity.leagueRepository);
        fPresenter.loadLeagueTable(MainActivity._api, false, MainActivity.chosenLeagueID);
    }

    @Override
    public void setPresenter(LeagueContract.Presenter presenter) {

    }

    @Override
    public void setAdapters(LeagueTable leagueTable, boolean fromAPI) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerow, v.getContext(), false);
        else
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerowlandscape, v.getContext(), true);
            rv.setAdapter(leagueAdapter);
            leagueAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog() {
        //comm.initialiseNavigationDrawer();
        leagueName.setText(MainActivity.chosenLeague);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        //LeagueFragment currentFragment = (LeagueFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateAdapter();
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            updateAdapter();
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateAdapter()
    {
        leagueAdapter.notifyDataSetChanged();
    }
}
