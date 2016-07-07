package com.jacobgreenland.finalproject.team.remote;

import android.util.Log;

import com.jacobgreenland.finalproject.fixture.model.Fixture;
import com.jacobgreenland.finalproject.fixture.model.FixtureParent;
import com.jacobgreenland.finalproject.player.model.Player;
import com.jacobgreenland.finalproject.player.model.TeamPlayers;
import com.jacobgreenland.finalproject.team.TeamAPI;
import com.jacobgreenland.finalproject.team.TeamContract;
import com.jacobgreenland.finalproject.team.TeamRepository;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 17/06/16.
 */
public class RemoteTeamSource {

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    private Team team;
    private List<Fixture> fixtures;
    private List<Player> players;
    FixtureParent fixtureParent;
    TeamPlayers teamPlayers;

    public RemoteTeamSource()
    {

    }

    public Team getTeamObject()
    {
        return team;
    }
    public List<Fixture> getFixtures()
    {
        return fixtures;
    }
    public List<Player> getPlayers()
    {
        return players;
    }

    public void getTeam(TeamAPI _api, final boolean initialLoad, final TeamContract.View mView, final TeamRepository teamRepository, String id)
    {
        _subscriptions.add(_api.getTeam(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<Team>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "Error");
                    }
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                        /*if(initialLoad) {
                            leagueRepository.getLocalSource().addData(leagues);
                        }
                        else {*/
                        //Log.d("TEST", "ARRAY SIZE IS : " + leagueTable.getStanding().size());
                            //mView.setTeam(team, true);
                            mView.showDialog();
                        //}
                    }
                    @Override
                    public void onNext(Team team2) {
                        Log.i("Retrofit", "onNext");

                        team = team2;
                    }
                }));
    }
    public void getFixtures(TeamAPI _api, final boolean initialLoad, final TeamContract.View mView, final TeamRepository teamRepository, String id, final Team t)
    {
        _subscriptions.add(_api.getFixtures(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<FixtureParent>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", e.toString());
                    }
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted fixtures");
                        teamRepository.getLocalSource().addFixtureData(fixtureParent);
                        //Log.d("TEST", "ARRAY SIZE IS : " + leagueTable.getStanding().size());
                        mView.setFixtureAdapter(fixtures);
                        //mView.showDialog();
                        //}
                    }
                    @Override
                    public void onNext(FixtureParent fixture) {
                        Log.i("Retrofit", "onNext");

                        fixtureParent = fixture;
                        fixtureParent.setTeam(t);
                        fixtureParent.setCode(t.getCode());
                        //Log.d("test",fixture.getFixture().getHomeTeamName());
                        fixtures = fixture.getFixtures();
                    }
                }));
    }

    public void getPlayers(TeamAPI _api, final boolean initialLoad, final TeamContract.View mView, final TeamRepository teamRepository, String id, final Team t)
    {
        _subscriptions.add(_api.getPlayers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<TeamPlayers>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", e.toString());
                    }
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted players");
                        /*if(initialLoad) {
                            leagueRepository.getLocalSource().addData(leagues);
                        }
                        else {*/
                        //Log.d("TEST", "ARRAY SIZE IS : " + leagueTable.getStanding().size());
                        teamRepository.getLocalSource().addPlayerData(teamPlayers);
                        mView.setPlayerAdapter(players);
                        //mView.showDialog();
                        //}
                    }
                    @Override
                    public void onNext(TeamPlayers team) {
                        Log.i("Retrofit", "onNext");

                        Log.d("test",team.getPlayers().get(0).getName());

                        teamPlayers = team;
                        teamPlayers.setTeam(t);
                        teamPlayers.setCode(t.getCode());

                        players = team.getPlayers();
                    }
                }));
    }
}
