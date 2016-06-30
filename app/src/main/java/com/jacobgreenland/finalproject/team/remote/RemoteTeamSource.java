package com.jacobgreenland.finalproject.team.remote;

import android.util.Log;

import com.jacobgreenland.finalproject.team.TeamAPI;
import com.jacobgreenland.finalproject.team.TeamContract;
import com.jacobgreenland.finalproject.team.TeamRepository;
import com.jacobgreenland.finalproject.team.model.Team;

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

    public RemoteTeamSource()
    {

    }

    public Team getTeamObject()
    {
        return team;
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
                            //mView.setAdapters(leagueTable, true);
                            //mView.showDialog();
                        //}
                    }
                    @Override
                    public void onNext(Team team2) {
                        Log.i("Retrofit", "onNext");

                        team = team2;
                    }
                }));
    }
}
