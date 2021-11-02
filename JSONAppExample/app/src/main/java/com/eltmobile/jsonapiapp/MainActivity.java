package com.eltmobile.jsonapiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    /**
     * Simple app on how to use JSON in android
     * by Godswill Okon
     * feel free to use it for private and commercial purposes 
      */

    TextView myText;
    RecyclerView recyclerView;
    private ProgressBar mProgressBar;
    FloatingActionButton floatButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        myText = (TextView) findViewById(R.id.hitTextView);
        mProgressBar.setVisibility(View.INVISIBLE);
        floatButton = (FloatingActionButton) findViewById(R.id.floatbutton);

        //getting intent from search activity
        intent = getIntent();
        String intentString = intent.getStringExtra(getString(R.string.searchIntent));

        if(intentString == null || intentString.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Use the search icon for searching",
                    Toast.LENGTH_LONG).show();
        }else{
            try {
                myText.setVisibility(View.INVISIBLE);
                URL url = ApiClass.buildURL(intentString);
                new AsyncClass().execute(url);
            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }
        }

        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this,
        LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(booksLayoutManager);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myText.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    //running URL string in the worker thread
    class AsyncClass extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try{
                result = ApiClass.getJson(searchURL);
           } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String stringResult) {
            if(stringResult != null){
            mProgressBar.setVisibility(View.INVISIBLE);
            ArrayList<BookModelClass> bookModelClasses = ApiClass.getBooksFromJSON(stringResult);
            BooksAdapter booksAdapter = new BooksAdapter(bookModelClasses);
            recyclerView.setAdapter(booksAdapter);
            int myInt  =  booksAdapter.getItemCount();
            Toast.makeText(getApplicationContext(), String.valueOf(myInt + " related books found"),
            Toast.LENGTH_LONG).show();
        }else{
                Toast.makeText(getApplicationContext(), "check your connection and try again",
                 Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.INVISIBLE);
           }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}