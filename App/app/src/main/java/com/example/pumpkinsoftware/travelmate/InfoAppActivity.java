package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InfoAppActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_twitter);
        context = (Context) this;

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                finish();
            }
        });

        final WebView view = (WebView)findViewById(R.id.twitterFeed);
        view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        String text = "<html><body>";
        text += "<a class=\"twitter-timeline\" href=\"https://twitter.com/_TravelMate_?ref_src=twsrc%5Etfw\">Tweets by _TravelMate_</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n";
        text += "</body></html>";
        view.loadDataWithBaseURL("https://twitter.com", text, "text/html", "utf-8", "");



    }

}
