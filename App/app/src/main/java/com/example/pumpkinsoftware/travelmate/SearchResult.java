package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SearchResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
       /* setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Risultati");*/
        toolbar.setTitle("Risultati");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //FINE


        Bundle p = getIntent().getExtras();
        String risultati =p.getString("result");
        TextView testo=(TextView) findViewById(R.id.result);
        testo.setText(risultati);
        SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

    }
}
