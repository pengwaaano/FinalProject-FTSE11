package com.jacobgreenland.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.jacobgreenland.finalproject.league.model.League;
import com.jacobgreenland.finalproject.league.LeagueAPI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 29/06/16.
 */
public class SplashScreen extends AppCompatActivity
{
    @Inject
    LeagueAPI _api;

    //private IItemAPI _api;
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private ProgressDialog pDialog;

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        ((MyApp) getApplication()).getApiComponent().inject(this);

        //_api = Services._createItemAPI();
        pattern();

        pb = (ProgressBar) findViewById(R.id.splashProgress);
    }

    public void pattern() {
        // load in Women Category
        _subscriptions.add(_api.getLeagues(Constants.CURRENT_SEASON)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(15000, TimeUnit.MILLISECONDS)
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
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        //}

                    }
                    @Override
                    public void onNext(List<League> newLeagues) {
                        Log.i("Retrofit", "onNext");

                        MainActivity.leagues = newLeagues;
                    }
                }));

    }
    private void hidePDialog()
    {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
