package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
        getSupportActionBar().setTitle("Risultati");
        toolbar.setTitle("Risultati");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        //FINE


        Bundle p = getIntent().getExtras();
        final String risultati =p.getString("result");
        final TextView testo=(TextView) findViewById(R.id.result);
        testo.setText(risultati);
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                        testo.setText(risultati);
                    }
                },3000);
            }
        });
    }
}
