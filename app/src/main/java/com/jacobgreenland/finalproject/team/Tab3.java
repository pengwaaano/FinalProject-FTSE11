package com.jacobgreenland.finalproject.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.fixture.FixtureAdapter;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.player.model.Player;
import com.jacobgreenland.finalproject.services.Services;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab3 extends Fragment implements TeamContract.View{

    Communicator comm;
    @BindView(R.id.tab3List)
    RecyclerView rv;
    View v;
    private Unbinder unbinder;

    TeamPresenter fPresenter;

    TeamAPI _teamapi;

    FixtureAdapter fixtureAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_3,container,false);
        unbinder = ButterKnife.bind(this,v);
        comm = (Communicator) getActivity();

        Log.d("test", "hello this is tab 3");
        //rv = (RecyclerView) v.findViewById(R.id.tab2List);
        //comm.setRView(rv, 2);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        comm = (Communicator) getActivity();
        //comm.afterViewsCreated();
        //comm = (Communicator) getActivity();

        _teamapi = Services._createTeamAPI();

        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());



        fPresenter = new TeamPresenter(this, new TeamRepository(getActivity().getApplicationContext()));
        String fixtures = MainActivity.chosenTeamObject.getLinks().getFixtures().getHref();
        fPresenter.loadFixtures(_teamapi, false, fixtures.substring(32,fixtures.length()));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void setPresenter(TeamContract.Presenter presenter) {

    }

    @Override
    public void setTeam(Team team, boolean fromAPI) {

        //MainActivity.chosenTeamObject = team;
        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerow, v.getContext(), false);
        else
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerowlandscape, v.getContext(), true);
        rv.setAdapter(leagueAdapter);
        leagueAdapter.notifyDataSetChanged();*/
    }
    @Override
    public void setFixtureAdapter(List<Fixture> fixtures) {

        fixtureAdapter = new FixtureAdapter(fixtures, R.layout.fixturecard, v.getContext(), false);
        rv.setAdapter(fixtureAdapter);
        fixtureAdapter.notifyDataSetChanged();

        Log.d("test","Adapter attached!");
        //MainActivity.chosenTeamObject = team;
        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerow, v.getContext(), false);
        else
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerowlandscape, v.getContext(), true);
        rv.setAdapter(leagueAdapter);
        leagueAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void setPlayerAdapter(List<Player> players) {

    }

    @Override
    public void showDialog() {
        //comm.initialiseNavigationDrawer();
        //leagueName.setText(MainActivity.chosenLeague);

        Log.d("test", "set photoooooo");

        /*name.setText(MainActivity.chosenTeamObject.getName());

        position.setText("" + MainActivity.chosenTeamPosition + getDayOfMonthSuffix(MainActivity.chosenTeamPosition));
        league.setText(MainActivity.chosenLeagueObject.getLeagueCaption());

        String fixtures = MainActivity.chosenTeamObject.getLinks().getFixtures().getHref();

        fPresenter.loadFixtures(_teamapi, false, fixtures.substring(32,fixtures.length()));

        /*String players = MainActivity.chosenTeamObject.getLinks().getPlayers().getHref();

        fPresenter.loadPlayers(_teamapi, false, players.substring(32,players.length()));*/
    }
}