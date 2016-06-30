package com.jacobgreenland.finalproject.league;


import com.jacobgreenland.finalproject.Constants;
import com.jacobgreenland.finalproject.league.model.League;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Jacob on 10/06/16.
 */
public interface LeagueAPI {

    @Headers
    (
        {
            "X-Auth-Token:962576f9524d43c39e266f486e158b7b"
        }
    )
    @GET(Constants.LEAGUE_URL)
    Observable<List<League>> getLeagues(@Query("season") String season);

    @GET(Constants.LEAGUE_URL + "{leagueID}/leagueTable")
    Observable<LeagueTable> getLeagueTable(@Path("leagueID") String leagueID);
}
