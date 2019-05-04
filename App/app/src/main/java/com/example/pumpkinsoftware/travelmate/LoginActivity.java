package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.pumpkinsoftware.travelmate.muted_video_view.MutedVideoView;
import com.example.pumpkinsoftware.travelmate.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    private TextInputEditText user, pass;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private VideoView videoView;
    private MutedVideoView mVideoView;
    private boolean so_prev_oreo = true; // I Don't need call lib func, I use it only for muting video on older version than Oreo
    private boolean so_prev_lol; // Useful for transitions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.mail2);
        pass = findViewById(R.id.password2);
        context = (Context) this;

        mAuth = FirebaseAuth.getInstance();

        // If click on bg, focus is deleted
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

        /* Login Button */
        loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

        /* Forgot Pass Button */
        Button button = (Button) findViewById(R.id.forgot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });

        /* Bg Video */
        // only for Oreo and newer versions
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //postponeEnterTransition();

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

            /*videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {

                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        // Here the video starts
                        videoView.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                startPostponedEnterTransition();
                            }

                        }, 1100); // 1000ms delay

                        return true;
                    }
                    return false;
                }
            });*/
        }

        // only older versions than Oreo
        else{
            /*so_prev_lol = false;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                postponeEnterTransition();
            else {
                so_prev_lol = true;
                supportPostponeEnterTransition();
            }*/

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

            /*mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {

                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        // Here the video starts
                        if(so_prev_lol)
                            supportStartPostponedEnterTransition();
                        else
                            startPostponedEnterTransition();
                        return true;
                    }
                    return false;
                }
            });*/
        }

    }

    private void login(){
        loginButton.setEnabled(false);
        String username = user.getText().toString();
        String password = pass.getText().toString();

        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Inserire tutti i campi", Toast.LENGTH_SHORT).show();
            loginButton.setEnabled(true);
        }
        else
            mAuth.signInWithEmailAndPassword(Utils.deleteSpaceAtStringEnd(username), password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    openMain();
                                } else {
                                    sendEmail();
                                    FirebaseAuth.getInstance().signOut();
                                    new AlertDialog.Builder(context)
                                            .setTitle("Attivazione richiesta")
                                            .setMessage("Ti è stata inviata una nuova mail per attivare il tuo account")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    loginButton.setEnabled(true);
                                                }
                                            }).show();
                                }

                            } else {
                                try {
                                    loginButton.setEnabled(true);
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthInvalidUserException invalidEmail) {
                                    Toast.makeText(context, "Email errata", Toast.LENGTH_SHORT).show();
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                                    Toast.makeText(context, "Password errata", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(context, "Si è verificato un problema, riprovare", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        private void sendEmail() {
                            mAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });
                        }
                    });

    }

    private void resetPass() {
        String emailAddress = user.getText().toString();

        if(emailAddress.isEmpty())
            Toast.makeText(context, "Inserire email", Toast.LENGTH_SHORT).show();

        else {
            mAuth.sendPasswordResetEmail(Utils.deleteSpaceAtStringEnd(emailAddress))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Email di recupero")
                                        .setMessage("Ti è stata inviata una mail per recuperare il tuo account")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        }).show();
                            }
                        }
                    });
        }
    }


    private void openMain(){
        // Send intent to finish prev activity
        Intent intent2 = new Intent(StartActivity.FINISH);
        sendBroadcast(intent2);
        Intent intent = new Intent(context,MainActivity.class);
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
    private void clearFocus() {
        View view = this.getCurrentFocus();
        if (view != null) view.clearFocus();
    }

}

