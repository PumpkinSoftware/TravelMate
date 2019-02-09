package com.example.pumpkinsoftware.travelmate;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SearchableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_search_results);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
            Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        }

        else {
            String message = intent.getStringExtra(MainActivity.FILTERED_QUERY);
        }


    }

}