package com.flickr.photoapp.inject;

import com.flickr.photoapp.photos.ui.PhotosActivity;

import javax.inject.Singleton;

import dagger.Component;
import livecoding.ask.fm.askfmlivecoding.MainActivity;
import livecoding.ask.fm.askfmlivecoding.MainFragment;
import livecoding.ask.fm.askfmlivecoding.time.TimeFragment;

@Singleton
@Component(modules = {AppModule.class})
public interface AppInjectionComponent {

    void inject(PhotosActivity timeFragment);
}