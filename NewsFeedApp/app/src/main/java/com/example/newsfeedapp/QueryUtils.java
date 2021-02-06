package com.example.newsfeedapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.newsfeedapp.MainActivity.LOG_TAG;

public class QueryUtils {

    public static List<Story> fetchData(String requestUrl) throws IOException, JSONException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);
        String jsonResponse = makeHttpRequest(url);
        Log.i(LOG_TAG, "Fetching data: ");
        return extractResultFromJson(jsonResponse);

    }

    public static List<Story> extractResultFromJson(String jsonResponse) throws JSONException {
        List<Story> stories = new ArrayList<>();
        JSONObject root = new JSONObject(jsonResponse);
        JSONObject response = root.getJSONObject("response");
        JSONArray results = response.getJSONArray("results");
        String webPublicationDate = " ";
        String authorName = " ";
        for (int i = 0; i < results.length(); i++) {
            JSONObject resultsObject = results.getJSONObject(i);
            String sectionName = resultsObject.getString("sectionName");
            String webTitle = resultsObject.getString("webTitle");
            String webUrl = resultsObject.getString("webUrl");
            if (resultsObject.has("tags")) {
                JSONArray tags = resultsObject.getJSONArray("tags");
                for (int j = 0; j < tags.length(); j++) {
                    String firstName = tags.getJSONObject(j).getString("firstName");
                    String lastName = tags.getJSONObject(j).getString("lastName");
                    authorName = firstName + " " + lastName;
                }
            }
            if (resultsObject.has("webPublicationDate")) {
                webPublicationDate = resultsObject.getString("webPublicationDate");
            }
//
            stories.add(new Story(sectionName, webTitle, webUrl, webPublicationDate, authorName));

        }

        return stories;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            return url;
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
    }

    private static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonResponse = " ";
        if (url == null) {
            return jsonResponse;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
