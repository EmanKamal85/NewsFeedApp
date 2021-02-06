package com.example.newsfeedapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Story>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    String GAURDIAN_REQUEST_URL = "https://content.guardianapis.com/search";
    ListView mainListView;
    TextView emptyStateTextView;
    ProgressBar progress;
    StoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkConnectivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(LOG_TAG, "onResume: Activity Resumed");
        checkConnectivity();
    }

    @NonNull
    @Override
    public Loader<List<Story>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(GAURDIAN_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("from-date", "2020-08-12");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("q", "music");
        uriBuilder.appendQueryParameter("api-key", "d82c008f-13e4-4d73-bf15-cd3bd4244b68");
        Log.i("Created URI", uriBuilder.toString());
        return new NewsFeedLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Story>> loader, List<Story> storiesList) {

        progress = findViewById(R.id.progress_circle);
        progress.setVisibility(View.GONE);

        emptyStateTextView = findViewById(R.id.empty_state);
        emptyStateTextView.setText("No stories available");

        if (storiesList != null && !storiesList.isEmpty()) {
            ListView mainListView = findViewById(R.id.list);
            adapter = new StoryAdapter(this, storiesList);
            mainListView.setAdapter(adapter);

            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Story currentStory = adapter.getItem(position);
                    Intent storyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentStory.getWebUrl()));
                    startActivity(storyIntent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Story>> loader) {
        adapter.clear();
    }


    public void checkConnectivity() {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        emptyStateTextView = (TextView) findViewById(R.id.empty_state);
        progress = (ProgressBar) findViewById(R.id.progress_circle);


        if (isConnected) {
            mainListView = (ListView) findViewById(R.id.list);
            mainListView.setEmptyView(emptyStateTextView);
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        } else {
            progress.setVisibility(View.GONE);
            emptyStateTextView.setText("No internet connection");
        }

    }

}
