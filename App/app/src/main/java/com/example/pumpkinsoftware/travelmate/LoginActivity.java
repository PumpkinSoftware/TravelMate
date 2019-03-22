package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.pumpkinsoftware.travelmate.muted_video_view.MutedVideoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private TextView buttonReg;
    private VideoView videoView;
    private MutedVideoView mVideoView;
    private boolean so_prev_oreo = true; // I Don't need call lib func, I use it only for muting video on older version than Oreo
    Context contesto;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contesto = this;

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        /* Login Button */
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login();
                performTransition(0);

            }
        });

        /* Registration Button */
        buttonReg = (TextView) findViewById(R.id.create);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performTransition(1);
            }
        });

        /* Bg Video */
        // only for Oreo and newer versions
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            so_prev_oreo = false;
            videoView = (VideoView) findViewById(R.id.login_bg_video);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login_bg_video);
            videoView.setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE);
            videoView.setVideoURI(uri);
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                }
            });
        }

        // only older versions than Oreo
        else{
            mVideoView = (MutedVideoView) findViewById(R.id.login_bg_mvideo);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login_bg_video);
            mVideoView.setVideoURI(uri);
            mVideoView.start();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                }
            });
        }

        /* Hide logo on input */
        final ImageView logo = (ImageView) findViewById(R.id.logo);


        // If click on bg, focus is deleted, so logo is restored
        findViewById(R.id.login_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
        });

        /* Hide or Show logo on hide or show keyboard */
        final View contentView = findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();

                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) {
                    // keyboard is opened
                    ImageView imageView = (ImageView)findViewById(R.id.logo);
                    Animation fadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                    imageView.startAnimation(fadeOutAnimation);
                    logo.setVisibility(View.GONE);
                } else {
                    // keyboard is closed
                    logo.setVisibility(View.VISIBLE);
                    ImageView imageView = (ImageView)findViewById(R.id.logo);
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                    imageView.startAnimation(fadeInAnimation);
                }
            }
        });

        /*Login with Firebase */
        /*List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());*/

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            //if(currentUser.isEmailVerified()) MODIFICATO PER TEST CON VECCHI ACCOUNT
                openMain();
        }
    }
    // [END on_start_check_user]

    /*public void login(){
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        if(username.isEmpty() || password.isEmpty()) openMain(); // CAMBIO PER I TEST
            //Toast.makeText(contesto, "Inserire tutti i campi", Toast.LENGTH_SHORT).show();
        else
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            openMain();
                        }
                        else {
                            Toast.makeText(contesto, "Nome utente o password errati", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    public void openMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to restart the video after coming from other activity like Sing up
        if(so_prev_oreo) {
            mVideoView = (MutedVideoView) findViewById(R.id.login_bg_mvideo);
            mVideoView.start();
        }

        else {
            videoView = (VideoView) findViewById(R.id.login_bg_video);
            videoView.start();
        }

        clearFocus();
    }

    // Elimina il focus dagli elementi correnti, utile per ripristinare la visualizzazione del logo
    public void clearFocus() {
        View view = this.getCurrentFocus();
        if (view != null) view.clearFocus();
    }

    private void performTransition(int type)
    {
       if (isDestroyed())  return;

        Intent intent;
        if(type == 0)   intent = new Intent(this, LogActivity.class);
        else            intent = new Intent(this, RegistrationActivity.class);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            //videoView.stopPlayback();
            if(so_prev_oreo) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create((View) mVideoView, "bg_video"));
                startActivity(intent, options.toBundle());
            }

            else {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create((View) videoView, "bg_video"));
                startActivity(intent, options.toBundle());
            }
        }

        else*/
            startActivity(intent);

    }

}
