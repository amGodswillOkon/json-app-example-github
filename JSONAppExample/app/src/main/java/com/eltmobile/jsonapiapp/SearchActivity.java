package com.eltmobile.jsonapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        button   =  (Button) findViewById(R.id.searchButton);
        editText =  (EditText) findViewById(R.id.searchEditView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkText = editText.getText().toString();
                if(checkText.isEmpty() || checkText.equals(" ")) {
                    Toast.makeText(getApplicationContext(), "Input Text, it seems to be empty",
                            Toast.LENGTH_LONG).show();
                }else {
                    inputText();
                }
            }
        });
    }

   public void inputText(){
       String intentString = editText.getText().toString();
       Intent textIntent = new Intent(getApplicationContext(), MainActivity.class);
       textIntent.putExtra("searchIntent", intentString);
       startActivity(textIntent);
       finish();
    }
}