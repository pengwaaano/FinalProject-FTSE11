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
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.player.PlayerAdapter;
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
public class Tab2 extends Fragment implements TeamContract.View{

    Communicator comm;
    @BindView(R.id.tab2List)
    RecyclerView rv;
    View v;
    private Unbinder unbinder;

    TeamPresenter fPresenter;

    TeamAPI _teamapi;

    PlayerAdapter playerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_2,container,false);
        unbinder = ButterKnife.bind(this,v);
        comm = (Communicator) getActivity();

        Log.d("test", "hello this is tab 2");
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

        String players = MainActivity.chosenTeamObject.getLinks().getPlayers().getHref();

        fPresenter = new TeamPresenter(this, new TeamRepository(getActivity().getApplicationContext()));
        fPresenter.loadPlayers(_teamapi, false, players.substring(32,players.length()));
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

    }
    @Override
    public void setFixtureAdapter(List<Fixture> fixtures) {

    }

    @Override
    public void setPlayerAdapter(List<Player> players) {

        playerAdapter = new PlayerAdapter(players, R.layout.playercard, v.getContext(), false);
        rv.setAdapter(playerAdapter);
        playerAdapter.notifyDataSetChanged();

        Log.d("test","Adapter attached!");

    }

    @Override
    public void showDialog() {

        Log.d("test", "set photoooooo");

    }
}