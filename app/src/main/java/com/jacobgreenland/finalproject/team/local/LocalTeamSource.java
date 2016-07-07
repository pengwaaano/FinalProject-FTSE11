package com.jacobgreenland.finalproject.team.local;

import android.content.Context;
import android.util.Log;

import com.jacobgreenland.finalproject.fixture.model.FixtureParent;
import com.jacobgreenland.finalproject.player.model.TeamPlayers;
import com.jacobgreenland.finalproject.team.TeamContract;
import com.jacobgreenland.finalproject.team.model.Team;

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

    public void addFixtureData(final FixtureParent fixtureP)
    {
        realm.executeTransaction(new Realm.Transaction()
        {
            @Override
            public void execute(Realm realm)
            {
                // remove single match
                Log.d("test", "loading in data");
                    final FixtureParent finalFixture = realm.copyToRealmOrUpdate(fixtureP);
            }
        });
    }

    public void getFixtureDataFromLocal(Team t, TeamContract.View mView)
    {
        Log.d("test", "local data loading");
        RealmResults<FixtureParent> result2 = realm.where(FixtureParent.class)
                .findAll();

        for(FixtureParent f : result2)
        {
            if(f.getTeam().getName().equals(t.getName()))
            {
                mView.setFixtureAdapter(f.getFixtures());
            }
        }
        //Log.d("test", result2.get(0).getTrackName());
        //return result2;
    }

    public boolean isFixtureRealmEmpty(Team t)
    {
        RealmResults<FixtureParent> result2 = realm.where(FixtureParent.class)
                .findAll();
        if(result2.size() == 0)
            return true;

        for(FixtureParent fp : result2)
        {
            if(fp.getTeam().getName().equals(t.getName()))
            {
                return false;
            }
        }
        return true;
    }

    public void addPlayerData(final TeamPlayers players)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // remove single match
                Log.d("test", "loading in data");
                final TeamPlayers finalPlayers = realm.copyToRealmOrUpdate(players);
            }
        });

    }

    public void getPlayerDataFromLocal(Team t, TeamContract.View mView)
    {
        Log.d("test", "local data loading");
        RealmResults<TeamPlayers> result2 = realm.where(TeamPlayers.class)
                .findAll();

        for(TeamPlayers tp : result2)
        {
            if(tp.getTeam().getName().equals(t.getName()))
            {
                mView.setPlayerAdapter(tp.getPlayers());
            }
        }
        //Log.d("test", result2.get(0).getTrackName());
        //return result2;
    }

    public boolean isPlayerRealmEmpty(Team t)
    {

        //Log.d("test", "check if realm is empty for players");
        RealmResults<TeamPlayers> result2 = realm.where(TeamPlayers.class)
                .findAll();
        if(result2.size() == 0)
            return true;

        for(TeamPlayers tp : result2)
        {
            Log.d("test", tp.getTeam().getName() + " : " + t.getName());
            if(tp.getTeam().getName().equals(t.getName()))
            {
                return false;
            }
        }
        return true;
    }
}
