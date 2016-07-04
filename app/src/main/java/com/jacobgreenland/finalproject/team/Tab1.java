package com.jacobgreenland.finalproject.team;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.caverock.androidsvg.SVG;
import com.jacobgreenland.finalproject.Communicator;
import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.R;
import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.player.model.Player;
import com.jacobgreenland.finalproject.services.Services;
import com.jacobgreenland.finalproject.team.model.Team;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab1 extends Fragment implements TeamContract.View{

    Communicator comm;
    @BindView(R.id.clubBadge) ImageView badge;
    @BindView(R.id.tTeamName) TextView name;
    @BindView(R.id.tPosition) TextView position;
    @BindView(R.id.tLeague) TextView league;
    @BindView(R.id.favouriteButton) ImageView favourite;

    View v;
    private Unbinder unbinder;

    GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    boolean favouriteBool = false;

    TeamAPI _teamapi;

    TeamTabs ttabs;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.tab_1,container,false);
        ButterKnife.bind(this,v);

        //rv = (RecyclerView) v.findViewById(R.id.tab1List);
        Log.d("test", "hello this is tab 1");

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        _teamapi = Services._createTeamAPI();

        Log.d("test", MainActivity.chosenTeamObject.getCrestUrl());

        Picasso.with(v.getContext())
                .load(Uri.parse(MainActivity.chosenTeamObject.getCrestUrl()))
                .into(badge);

        favourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favouriteTeam();
            }
        });

        name.setText(MainActivity.chosenTeamObject.getName());

        position.setText("" + MainActivity.chosenTeamPosition + getDayOfMonthSuffix(MainActivity.chosenTeamPosition));
        league.setText(MainActivity.chosenLeagueObject.getLeagueCaption());

        Log.d("test", "Adapter should have been set by now!");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }
    @Override
    public void setPresenter(TeamContract.Presenter presenter) {

    }

    @Override
    public void setTeam(Team team, boolean fromAPI) {

        comm = (Communicator) getActivity().getSupportFragmentManager().findFragmentByTag("tabs");

        MainActivity.chosenTeamObject = team;
        //comm.loadMoreTabs();

        /*String players = team.getLinks().getPlayers().getHref();
        ttabs.fPresenter.loadPlayers(_teamapi, false, players.substring(32,players.length()));
        String fixtures = MainActivity.chosenTeamObject.getLinks().getFixtures().getHref();
        ttabs.fPresenter.loadFixtures(_teamapi, false, fixtures.substring(32,fixtures.length()));

        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerow, v.getContext(), false);
        else
            leagueAdapter = new LeagueAdapter(leagueTable.getStanding(), R.layout.leaguetablerowlandscape, v.getContext(), true);
        rv.setAdapter(leagueAdapter);
        leagueAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void setFixtureAdapter(List<Fixture> fixtures) {
        //Tab3 t3 = (Tab3) getActivity().getSupportFragmentManager().findFragmentByTag("Tab3");
        //t3.setFixtureAdapter(ttabs.fPresenter.getRepo().getRemoteSource().getFixtures());
    }

    @Override
    public void setPlayerAdapter(List<Player> players) {
        //Tab2 t2 = (Tab2) getActivity().getSupportFragmentManager().findFragmentByTag("Tab2");
        //t2.setPlayerAdapter(ttabs.fPresenter.getRepo().getRemoteSource().getPlayers());
    }

    @Override
    public void showDialog() {
        //comm.initialiseNavigationDrawer();
        //leagueName.setText(MainActivity.chosenLeague);

        Log.d("test", "set photoooooo");

        //comm.loadMoreTabs();

        /*String players = MainActivity.chosenTeamObject.getLinks().getPlayers().getHref();

        fPresenter.loadPlayers(_teamapi, false, players.substring(32,players.length()));*/
    }
    String getDayOfMonthSuffix(final int n)
    {
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
    public void favouriteTeam()
    {
        if(!favouriteBool) {
            favourite.setImageResource(R.drawable.favouritechecked);
            favouriteBool = true;
        }
        else {
            favourite.setImageResource(R.drawable.favouriteunchecked);
            favouriteBool = false;
        }
    }
}
