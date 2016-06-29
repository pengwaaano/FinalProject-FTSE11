package com.jacobgreenland.finalproject.injection.components;


import android.content.SharedPreferences;

import com.jacobgreenland.finalproject.injection.modules.AppModule;
import com.jacobgreenland.finalproject.injection.modules.NetModule;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.RestAdapter;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed
    RestAdapter retrofit();
    OkHttpClient okHttpClient();
    SharedPreferences sharedPreferences();
}