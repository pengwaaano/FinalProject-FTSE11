package com.jacobgreenland.finalproject.injection.modules;


import com.jacobgreenland.finalproject.injection.scope.UserScope;
import com.jacobgreenland.finalproject.league.LeagueAPI;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class APIModule {

    @UserScope
    @Provides
    public LeagueAPI providesItemsInterface(RestAdapter retrofit)
    {
        return retrofit.create(LeagueAPI.class);
    }
}
