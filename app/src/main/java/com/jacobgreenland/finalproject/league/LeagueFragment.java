package com.jacobgreenland.finalproject.league;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.league.model.LeagueTable;
import com.jacobgreenland.finalproject.league.model.Standing;
import com.jacobgreenland.finalproject.services.Services;
import com.jacobgreenland.finalproject.team.TeamAPI;

import java.util.ArrayList;

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
    TeamAPI _teamapi;
    LeagueTable lT;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v =inflater.inflate(R.layout.leaguetable,container,false);
        setRetainInstance(true);
        comm = (Communicator) getActivity();
        _teamapi = Services._createTeamAPI();

        Log.d("LeagueFragment", "League fragment loaded again and again");

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

        fPresenter = new LeaguePresenter(this, new LeagueRepository(getActivity().getApplicationContext()));
        Log.d("Test", "LeagueTable loaded");
        if(fPresenter.getRepo().getLocalSource().isRealmEmpty(MainActivity.chosenLeague))
        {
            fPresenter.loadLeagueTable(MainActivity._api, false, MainActivity.chosenLeagueID);
        }
        else
        {
            fPresenter.loadLocalLeagueTable(MainActivity.chosenLeague);
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if (MainActivity.isOnline)
                fPresenter.loadLeagueTable(MainActivity._api, false, MainActivity.chosenLeagueID);
                /*else {
                    Snackbar.make(v.findViewById(R.id.snackbarPosition), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }*/
            }
        });
    }

    /*@Override
    public void onResume()
    {
        super.onResume();
        if(MainActivity.chosenLeagueObject != null) {
            if (fPresenter.getRepo().getLocalSource().isTeamRealmEmpty()) {
                ArrayList<String> ids = new ArrayList<>();
                for (Standing t : MainActivity.chosenLeagueObject.getStanding()) {
                    ids.add(t.getLinks().getTeamLink().getHref().substring(32, t.getLinks().getTeamLink().getHref().length()));
                }
                fPresenter.loadTeamsOfLeague(_teamapi, false, ids, MainActivity.chosenLeagueObject.getLeagueCaption());
            } else {
                Log.d("test", "boo " + MainActivity.chosenLeagueObject.getLeagueCaption());
                fPresenter.loadLocalLeagueTeams(MainActivity.chosenLeagueObject.getLeagueCaption());
            }
        }
    }*/

    @Override
    public void setPresenter(LeagueContract.Presenter presenter) {

    }

    @Override
    public void setAdapters(LeagueTable leagueTable, boolean fromAPI) {
        lT = leagueTable;

        if(fPresenter.getRepo().getLocalSource().isTeamRealmEmpty(lT.getLeagueCaption())) {
            ArrayList<String> ids = new ArrayList<>();
            for(Standing t : leagueTable.getStanding())
            {
                ids.add(t.getLinks().getTeamLink().getHref().substring(32,t.getLinks().getTeamLink().getHref().length()));
            }
            fPresenter.loadTeamsOfLeague(_teamapi, false, ids, lT.getLeagueCaption());
        }
        else {
            Log.d("test","boo " + lT.getLeagueCaption());
            fPresenter.loadLocalLeagueTeams(lT.getLeagueCaption());
        }
    }
    @Override
    public void setLeagueAdapters()
    {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            leagueAdapter = new LeagueAdapter(MainActivity.chosenLeagueObject.getStanding(), R.layout.leaguetablerow, v.getContext(), false, fPresenter.getRepo());
        else
            leagueAdapter = new LeagueAdapter(MainActivity.chosenLeagueObject.getStanding(), R.layout.leaguetablerowlandscape, v.getContext(), true,fPresenter.getRepo());
        rv.setAdapter(leagueAdapter);
        leagueAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog() {
        //comm.initialiseNavigationDrawer();
        leagueName.setText(MainActivity.chosenLeague);
        mSwipeRefreshLayout.setRefreshing(false);
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
