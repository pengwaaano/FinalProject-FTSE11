package com.jacobgreenland.finalproject.league;

import android.content.Context;

import com.jacobgreenland.finalproject.league.local.LocalLeagueSource;
import com.jacobgreenland.finalproject.league.remote.RemoteLeagueSource;


/**
 * Created by Jacob on 17/06/16.
 */
public class LeagueRepository {

    private LocalLeagueSource localSource;

    private RemoteLeagueSource remoteSource;

    public LeagueRepository(Context c)
    {
        this.localSource = new LocalLeagueSource(c);
        this.remoteSource = new RemoteLeagueSource();
    }

    public RemoteLeagueSource getRemoteSource()
    {
        return this.remoteSource;
    }
    public void setRemoteSource(RemoteLeagueSource remote)
    {
        this.remoteSource = remote;
    }

    public LocalLeagueSource getLocalSource()
    {
        return this.localSource;
    }
    public void setLocalSource(LocalLeagueSource local)
    {
        this.localSource = local;
    }
}
