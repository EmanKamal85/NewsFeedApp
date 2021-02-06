package com.example.newsfeedapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedLoader extends AsyncTaskLoader<List<Story>> {
    private final String[] strings;

    public NewsFeedLoader(@NonNull Context context, String... strings) {
        super(context);
        this.strings = strings;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Story> loadInBackground() {
        List<Story> storyList = new ArrayList<>();
        if (strings.length < 1 || strings[0] == null) {
            return null;
        } else {

            try {
                storyList = QueryUtils.fetchData(strings[0]);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return storyList;
    }
}
