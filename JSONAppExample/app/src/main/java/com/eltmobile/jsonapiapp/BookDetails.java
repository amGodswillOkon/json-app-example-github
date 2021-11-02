package com.eltmobile.jsonapiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ImageView;

import com.eltmobile.jsonapiapp.databinding.ActivityBookDetailsBinding;

/*
* A simple data binding, binding the book details activity*/

public class BookDetails extends AppCompatActivity {
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        BookModelClass bookModelClass = getIntent().getParcelableExtra("BookModelClass");
        ActivityBookDetailsBinding activityBookDetailsBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_book_details);
        activityBookDetailsBinding.setBookModelClass(bookModelClass);
   }
}

