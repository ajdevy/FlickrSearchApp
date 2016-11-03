package com.flickr.photoapp.photos;

import android.util.Log;

public class PhotoUrlGenerator {

    private static final String TAG = PhotoUrlGenerator.class.getName();

    //"http://farm {farm}. static.flickr.com/{server}/{id}_{secret}.jpg";
    private static final String URL_TEMPLATE = "http://farm%s.static.flickr.com/%s/%s_%s.jpg";

    public static String generate(Photo photo) {
        Log.d(TAG, "generate " + photo + " " + String.format(URL_TEMPLATE, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret()));
        return String.format(URL_TEMPLATE, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());
    }
}