package com.jacobgreenland.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.jacobgreenland.finalproject.league.LeagueAPI;
import com.jacobgreenland.finalproject.league.model.League;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
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


    RealmConfiguration realmConfig;
    public static Realm realm;

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);


        realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Realm.getDefaultInstance().deleteAll();
        realm.commitTransaction();

        ((MyApp) getApplication()).getApiComponent().inject(this);

        //_api = Services._createItemAPI();
        if(realm.isEmpty())
            pattern();
        else {
            MainActivity.leagues = loadFromRealm();
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

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
                        //for(League l : leagues) {
                        addToRealm(MainActivity.leagues);
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

    private void addToRealm(final List<League> leagues)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(League l : leagues) {
                    final League finalLeague = realm.copyToRealmOrUpdate(l);
                }
            }
        });
    }
    private List<League> loadFromRealm()
    {
        RealmResults<League> result2 = realm.where(League.class)
                .findAll();
        return result2;
    }
    private void hidePDialog()
    {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
