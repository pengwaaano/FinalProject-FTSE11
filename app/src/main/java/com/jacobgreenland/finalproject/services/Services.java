package com.jacobgreenland.finalproject.services;

import com.jacobgreenland.finalproject.Constants;
import com.jacobgreenland.finalproject.team.TeamAPI;

import retrofit.RestAdapter;

/**
 * Created by Jacob on 09/06/16.
 */
public class Services {

    // -----------------------------------------------------------------------------------
    public static TeamAPI _createTeamAPI() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(TeamAPI.class);
    }
}