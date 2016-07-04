package com.jacobgreenland.finalproject.league.local;

import android.content.Context;
import android.util.Log;

import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.league.LeagueContract;
import com.jacobgreenland.finalproject.league.model.LeagueTable;
import com.jacobgreenland.finalproject.team.model.Team;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Jacob on 17/06/16.
 */
public class LocalLeagueSource {

    RealmConfiguration realmConfig;
    Realm realm;

    public LocalLeagueSource(Context context)
    {
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();
    }

    public void addLeagueTableData(LeagueTable leagueTable)
    {
        realm.beginTransaction();
        Log.d("test", "loading in data");
            final LeagueTable finalLeague = realm.copyToRealmOrUpdate(leagueTable);
        realm.commitTransaction();
    }

    public void addLeagueTeamData(final List<Team> teams)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // remove single match
                RealmResults<Team> results = realm.where(Team.class).findAll();
                results.deleteAllFromRealm();


                Log.d("test", "loading in data");
                for(Team t : teams) {
                    final Team team = realm.copyToRealmOrUpdate(t);
                }
            }
        });
    }

    public void getTeamDataFromLocal(LeagueContract.View mView)
    {

        RealmResults<Team> results = realm.where(Team.class).findAll();

        MainActivity.loadedLeagueTeams = results;

        mView.setLeagueAdapters();
    }

    public void getDataFromLocal(String leagueName, LeagueContract.View mView)
    {

        Log.d("test", leagueName);
        RealmResults<LeagueTable> result2 = realm.where(LeagueTable.class)
                .contains("leagueCaption", leagueName)
                .findAll();
        //Log.d("test", result2.get(0).getTrackName());
        MainActivity.chosenLeagueObject = result2.get(0);
        mView.setAdapters(result2.get(0),false);
    }
    public boolean isRealmEmpty(String league)
    {
        RealmResults<LeagueTable> result2 = realm.where(LeagueTable.class)
                .contains("leagueCaption", league)
                .findAll();

        if(result2.size() == 0)
            return true;
        else
            return false;
    }
    public boolean isTeamRealmEmpty()
    {
        RealmResults<Team> result2 = realm.where(Team.class)
                .findAll();

        if(result2.size() == 0)
            return true;
        else
            return false;
    }
}
