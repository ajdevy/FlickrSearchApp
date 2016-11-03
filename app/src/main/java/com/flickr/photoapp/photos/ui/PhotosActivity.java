package com.flickr.photoapp.photos.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.flickr.photoapp.R;
import com.flickr.photoapp.flickr.FlickrClient;
import com.flickr.photoapp.photos.Photo;
import com.flickr.photoapp.photos.PhotoSearchSuggestionProvider;
import com.flickr.photoapp.photos.PhotosContainer;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhotosActivity extends AppCompatActivity {

    private static final String TAG = PhotosActivity.class.getSimpleName();
    private FlickrClient flickrClient;
    private RecyclerView photoRecyclerView;
    private PhotoArrayAdapter photoAdapter;
    private MaterialSearchView searchView;
    private List<Photo> photos = new ArrayList<>();
    private SearchRecentSuggestions suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        photoRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);
        photoAdapter = new PhotoArrayAdapter(this, photos);
        photoRecyclerView.setAdapter(photoAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        photoRecyclerView.setLayoutManager(gridLayoutManager);

        flickrClient = new FlickrClient(this);

        setupSearch();

        handleSearchAction(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleSearchAction(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photos, menu);
        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView menuSearchView = null;
        if (item != null) {
            menuSearchView = (SearchView) item.getActionView();
        }
        if (menuSearchView != null) {
            menuSearchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }

        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void handleSearchAction(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchForPhotos(query);
        }
    }

    private void setupSearch() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL);

        suggestions = new SearchRecentSuggestions(this, PhotoSearchSuggestionProvider.AUTHORITY,
                PhotoSearchSuggestionProvider.MODE);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchForPhotos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchForPhotos(String query) {
        suggestions.saveRecentQuery(query, null);
        loadPhotos(query);
    }

    public void loadPhotos(String text) {
        flickrClient.getPhotos(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayPhotos,
                        error -> Log.e(TAG, "got an error while loading photos", error));
    }

    private void displayPhotos(PhotosContainer photosContainer) {
        if (photoAdapter != null && photoRecyclerView != null) {
            photoAdapter.clear();
            photoRecyclerView.scrollToPosition(0);
            if (photosContainer != null && photosContainer.getPhotos() != null && photosContainer.getPhotos().getPhoto() != null) {
                final List<Photo> photos = photosContainer.getPhotos().getPhoto();
                photoAdapter.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }
}