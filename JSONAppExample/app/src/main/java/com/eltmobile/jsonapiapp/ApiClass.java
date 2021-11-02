package com.eltmobile.jsonapiapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiClass {
    private ApiClass() {
    }

    /*
    * static class responsible for managing basic URL connectiom and JSON file*/
    public static final String BASIC_URL = "https://www.googleapis.com/books/v1/volumes";
    public static final String URI_KEY = "q";
    public static final String KEY = "key";
    public static final String API_KEY = "TODO ...input your API KEY HERE";

    public static URL buildURL(String URLString) {

        Uri uri = Uri.parse(BASIC_URL).buildUpon()
                .appendQueryParameter(URI_KEY, URLString)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        URL url = null;
        try {
           url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getJson(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();

            if (hasData) {

                return scanner.next();

            } else {

                return null;
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
            return null;
        } finally {
            connection.disconnect();
        }
    }

    /*
    * fetching and returning JSON data*/
    public static ArrayList<BookModelClass> getBooksFromJSON(String json){

        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subTitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHEDDATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";
        final String DESCRIPTION = "description";
        final String IMAGELINKS  = "imageLinks";
        final String THUMBNAIL   =  "thumbnail";

        ArrayList<BookModelClass> bookModelClasses = new ArrayList<BookModelClass>();
            try {
                JSONObject JSONBooks = new JSONObject(json);
                JSONArray arrayBooks = JSONBooks.getJSONArray(ITEMS);
                int arrayItems = arrayBooks.length();
                for(int i = 0; i<arrayItems; i++ ) {

                    JSONObject getSingleBooks = arrayBooks.getJSONObject(i);
                    JSONObject getleVolumeInfo = getSingleBooks.getJSONObject(VOLUMEINFO);
                    JSONObject getImageLinks  = getleVolumeInfo.getJSONObject(IMAGELINKS);
                    int authors = getleVolumeInfo.getJSONArray(AUTHORS).length();
                    String[] mAuthorNum = new String[authors];

                    for (int j = 0; j<authors; j++) {
                        mAuthorNum[j] = getleVolumeInfo.getJSONArray(AUTHORS).get(j).toString();

                    }
                    BookModelClass bookModelClass = new BookModelClass(getSingleBooks.getString(ID), getleVolumeInfo.getString(TITLE),
                            (getleVolumeInfo.isNull(SUBTITLE)?"":getleVolumeInfo.getString(SUBTITLE)),
                            mAuthorNum, getleVolumeInfo.getString(PUBLISHER),
                            getleVolumeInfo.getString(PUBLISHEDDATE),
                            getleVolumeInfo.getString(DESCRIPTION), getImageLinks.getString(THUMBNAIL));
                            bookModelClasses.add(bookModelClass);
               }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return bookModelClasses;
    }
}