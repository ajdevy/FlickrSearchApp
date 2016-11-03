package com.flickr.photoapp.inject;

import android.app.Application;

import com.flickr.photoapp.flickr.FlickrClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppInjectionModule {

    private Application mApplication;

    public AppInjectionModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    FlickrClient providesTimeService(Application app) {
        return new FlickrClient(app);
    }
}