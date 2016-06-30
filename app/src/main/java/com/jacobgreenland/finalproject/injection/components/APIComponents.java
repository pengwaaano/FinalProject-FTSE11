package com.jacobgreenland.finalproject.injection.components;


import com.jacobgreenland.finalproject.MainActivity;
import com.jacobgreenland.finalproject.SplashScreen;
import com.jacobgreenland.finalproject.injection.modules.APIModule;
import com.jacobgreenland.finalproject.injection.scope.UserScope;

import dagger.Component;


/**
 * Created by kalpesh on 20/01/2016.
 */

    @UserScope
    @Component(dependencies =NetComponent.class, modules = APIModule.class)
    public interface APIComponents {

    void inject(MainActivity activity);
    void inject(SplashScreen activity);
}
