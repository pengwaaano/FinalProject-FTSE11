package com.jacobgreenland.finalproject.team.local;

import android.content.Context;
import android.util.Log;

import com.jacobgreenland.finalproject.league.model.League;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Jacob on 17/06/16.
 */
public class LocalTeamSource {

    RealmConfiguration realmConfig;
    Realm realm;

    public LocalTeamSource(Context context)
    {
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();
    }

    public void addData(List<League> leagues)
    {
        realm.beginTransaction();
        Log.d("test", "loading in data");
        for(int i = 0; i < leagues.size(); i++)
        {
            League r = leagues.get(i);
            final League finalLeague = realm.copyToRealm(r);
        }
        realm.commitTransaction();
    }

    public List<League> getDataFromLocal(String genre)
    {
        Log.d("test", "local data loading");
        RealmResults<League> result2 = realm.where(League.class)
                .equalTo("type", genre)
                .findAll();
        //Log.d("test", result2.get(0).getTrackName());
        return result2;
    }
}
