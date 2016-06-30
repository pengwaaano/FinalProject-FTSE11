package com.jacobgreenland.finalproject.team;

import android.content.Context;

import com.jacobgreenland.finalproject.team.local.LocalTeamSource;
import com.jacobgreenland.finalproject.team.remote.RemoteTeamSource;


/**
 * Created by Jacob on 17/06/16.
 */
public class TeamRepository {

    private LocalTeamSource localSource;

    private RemoteTeamSource remoteSource;

    public TeamRepository(Context c)
    {
        this.localSource = new LocalTeamSource(c);
        this.remoteSource = new RemoteTeamSource();
    }

    public RemoteTeamSource getRemoteSource()
    {
        return this.remoteSource;
    }
    public void setRemoteSource(RemoteTeamSource remote)
    {
        this.remoteSource = remote;
    }

    public LocalTeamSource getLocalSource()
    {
        return this.localSource;
    }
    public void setLocalSource(LocalTeamSource local)
    {
        this.localSource = local;
    }
}
