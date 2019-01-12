package com.example.pumpkinsoftware.travelmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.pumpkinsoftware.travelmate.R;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button=(Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        /* Bg Video */
        mVideoView = (VideoView) findViewById(R.id.login_bg_video);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.login_bg_video);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });


        /* Hide logo on input */
        final ImageView logo = (ImageView) findViewById(R.id.imageView);

        EditText edit_text = (EditText) findViewById(R.id.editText);
        edit_text.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    logo.setVisibility(View.GONE);
                else
                    logo.setVisibility(View.VISIBLE);
            }
        });

        edit_text = (EditText) findViewById(R.id.editText2);
        edit_text.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    logo.setVisibility(View.GONE);
                else
                    logo.setVisibility(View.VISIBLE);
            }
        });



    }

    public void openMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // to restart the video after coming from other activity like Sing up
        mVideoView = (VideoView) findViewById(R.id.login_bg_video);
        mVideoView.start();
    }
}
