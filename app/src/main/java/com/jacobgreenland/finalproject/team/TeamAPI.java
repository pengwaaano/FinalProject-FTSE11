package com.jacobgreenland.finalproject.team;


import com.jacobgreenland.finalproject.fixture.model.FixtureParent;
import com.jacobgreenland.finalproject.player.model.TeamPlayers;
import com.jacobgreenland.finalproject.team.model.Team;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Jacob on 10/06/16.
 */
public interface TeamAPI
{
    @Headers("X-Auth-Token:2fc8580d18984ebc9dba834e2ab056c8")
    @GET("/{team}")
    Observable<Team> getTeam(@Path("team") String team);

    @Headers("X-Auth-Token:2fc8580d18984ebc9dba834e2ab056c8")
    @GET("/{team}")
    Observable<Team> getTeamsOfLeague(@Path("team") String team);

    @Headers("X-Auth-Token:2fc8580d18984ebc9dba834e2ab056c8")
    @GET("/{fixture}")
    Observable<FixtureParent> getFixtures(@Path("fixture") String fixture);

    @Headers("X-Auth-Token:2fc8580d18984ebc9dba834e2ab056c8")
    @GET("/{player}")
    Observable<TeamPlayers> getPlayers(@Path("player") String player);
}
