package com.jacobgreenland.finalproject.team;


import com.jacobgreenland.finalproject.team.model.Team;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Jacob on 10/06/16.
 */
public interface TeamAPI {

    @Headers
    (
        {
            "X-Auth-Token:962576f9524d43c39e266f486e158b7b"
        }
    )
    @GET("{team}")
    Observable<Team> getTeam(@Path("team") String team);
}
