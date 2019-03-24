package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String FREE_QUERY = "com.example.pumpkinsoftware.travelmate.FREE_QUERY";
    public static final String FILTERED_QUERY = "com.example.pumpkinsoftware.travelmate.FILTERED_QUERY";
    public static final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                Fragment selectedFragment = null;
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_viaggi:
                            selectedFragment = new ViaggiFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_opzioni:
                            selectedFragment = new OpzioniFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    public void runThis(View v) {
        Intent intent = new Intent(this, CreationTrip.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check if Intent comes from TravelDetailsActivity
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK) recreate();
        }
        // Else intent comes from EditUserActivity
        else    super.onActivityResult(requestCode, resultCode, data);

    }

}