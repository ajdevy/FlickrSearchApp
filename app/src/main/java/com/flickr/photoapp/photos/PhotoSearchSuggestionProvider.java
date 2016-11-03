package com.flickr.photoapp.photos;

import android.content.SearchRecentSuggestionsProvider;

public class PhotoSearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.PhotoSearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public PhotoSearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}