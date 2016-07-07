package com.jacobgreenland.finalproject.league.remote;

import android.util.Log;

import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.league.LeagueAPI;
import com.jacobgreenland.finalproject.league.LeagueContract;
import com.jacobgreenland.finalproject.league.LeagueRepository;
import com.jacobgreenland.finalproject.league.model.League;
import com.jacobgreenland.finalproject.league.model.LeagueTable;
import com.jacobgreenland.finalproject.team.TeamAPI;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 17/06/16.
 */
public class RemoteLeagueSource {

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    List<League> leagues;
    LeagueTable leagueTable;
    List<Team> leagueTeams = new ArrayList<Team>();

    public RemoteLeagueSource()
    {

    }
    public List<League> getLeagueList()
    {
        return leagues;
    }

    public LeagueTable getLeagueTable()
    {
        return leagueTable;
    }

    public List<Team> getLeagueTeams()
    {
        return leagueTeams;
    }

    public void getLeagueTable(LeagueAPI _api, final LeagueContract.View mView, final LeagueRepository leagueRepository, String id)
    {
        _subscriptions.add(_api.getLeagueTable(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<LeagueTable>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", e.toString());
                    }
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                            leagueRepository.getLocalSource().addLeagueTableData(leagueTable);
                        Log.d("TEST", "ARRAY SIZE IS : " + leagueTable.getStanding().size());
                            mView.setAdapters(leagueTable, true);
                            mView.showDialog();
                    }
                    @Override
                    public void onNext(LeagueTable leagueT) {
                        Log.i("Retrofit", "onNext");

                        Log.d("test", leagueT.getLeagueCaption() + "Sansa");
                        leagueTable = leagueT;
                        MainActivity.chosenLeagueObject = leagueT;
                    }
                }));
    }
    public void getTeamsOfLeague(TeamAPI _api, final boolean initialLoad, final LeagueContract.View mView, final LeagueRepository leagueRepository, List<String> id, String league)
    {
        leagueTeams = new ArrayList<Team>();
        for(String s : id) {
            _subscriptions.add(_api.getTeamsOfLeague(s)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(15000, TimeUnit.MILLISECONDS)
                    .retry()
                    .distinct()
                    .subscribe(new Observer<Team>() {
                        @Override
                        public void onError(Throwable e) {
                            Log.i("Retrofit", e.toString());
                        }

                        @Override
                        public void onCompleted() {
                            Log.i("Retrofit", "onCompleted");
                        /*if(initialLoad) {
                            leagueRepository.getLocalSource().addData(leagues);
                        }
                        else {*/
                            //Log.d("TEST", "ARRAY SIZE IS : " + leagueTable.getStanding().size());
                            leagueRepository.getLocalSource().addLeagueTeamData(leagueTeams);
                            MainActivity.loadedLeagueTeams = new ArrayList<Team>();
                            MainActivity.loadedLeagueTeams = leagueTeams;
                            mView.setLeagueAdapters();
                            Log.i("test", "load league teams");
                            //mView.setAdapters(leagueTable, true);
                            //mView.showDialog();
                            //}
                        }

                        @Override
                        public void onNext(Team team) {
                            Log.i("Retrofit", "onNext");

                            Log.d("test", team.getName());
                            leagueTeams.add(team);
                            //MainActivity.chosenLeagueObject = leagueT;
                        }
                    }));
        }
    }
}
