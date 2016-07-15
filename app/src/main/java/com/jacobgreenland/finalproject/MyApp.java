package com.jacobgreenland.finalproject;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.jacobgreenland.finalproject.injection.components.APIComponents;
import com.jacobgreenland.finalproject.injection.components.DaggerAPIComponents;
import com.jacobgreenland.finalproject.injection.components.DaggerNetComponent;
import com.jacobgreenland.finalproject.injection.components.NetComponent;
import com.jacobgreenland.finalproject.injection.modules.APIModule;
import com.jacobgreenland.finalproject.injection.modules.AppModule;
import com.jacobgreenland.finalproject.injection.modules.NetModule;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Jacob on 14/06/16.
 */
public class MyApp extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "YdBCtP1FNAWDeF1qxLwLwqRrK";
    private static final String TWITTER_SECRET = "uIWjeN37ZUHkZYlRZJgs7f1OMgE8kCZwhYa2k3lHC1th57x5jD";


    private NetComponent mNetComponent;
    private APIComponents mApiComponents;
    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(Constants.BASE_URL))
                .appModule(new AppModule(this))
                .build();

        mApiComponents = DaggerAPIComponents.builder()
                .netComponent(mNetComponent)
                .aPIModule(new APIModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public APIComponents getApiComponent() {
        return mApiComponents;
    }

}
