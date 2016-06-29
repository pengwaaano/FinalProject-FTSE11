package com.jacobgreenland.finalproject.league.remote;

import android.util.Log;

import com.jacobgreenland.finalproject.league.League;
import com.jacobgreenland.finalproject.league.LeagueAPI;
import com.jacobgreenland.finalproject.league.LeagueContract;
import com.jacobgreenland.finalproject.league.LeagueRepository;

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

    public RemoteLeagueSource()
    {

    }
    public List<League> getLeagueList()
    {
        return leagues;
    }

    public void getLeagues(LeagueAPI _api, final boolean initialLoad, final LeagueContract.View mView, final LeagueRepository leagueRepository, String season)
    {
        _subscriptions.add(_api.getLeagues(season)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<List<League>>() {
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
                            mView.setAdapters(leagues, true);
                            mView.showDialog();
                        //}

                    }
                    @Override
                    public void onNext(List<League> newLeagues) {
                        Log.i("Retrofit", "onNext");

                        leagues = newLeagues;
                        //MainActivity.leagues = newLeagues;
                    }
                }));
    }
}
