package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.muted_video_view.MutedVideoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private Context context;
    private EditText mail;
    private EditText pass;
    private FirebaseAuth mAuth;
    private VideoView videoView;
    private MutedVideoView mVideoView;
    private boolean so_prev_oreo = true; // I Don't need call lib func, I use it only for muting video on older version than Oreo
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.password);
        context = (Context) this;

        mAuth = FirebaseAuth.getInstance();

        // If click on bg, focus is deleted
        findViewById(R.id.reg_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
        });

        /* Reg Button */
        Button button = (Button) findViewById(R.id.buttonReg);
        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registration();
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

    }

    public void registration(){
        String email = mail.getText().toString();
        String password = pass.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Inserire tutti i campi", Toast.LENGTH_SHORT).show();
        }else if(!validate(email)){
            Toast.makeText(context, "Email non valida", Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8) {
            Toast.makeText(context, "Password troppo breve", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(context,AccountRegisterActivity.class);
            intent.putExtra("mail",email);
            intent.putExtra("pass",password);
            startActivity(intent);
        }
           /* mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Mail di verifica inviata", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });


                                // TODO Add user to db
                                /*RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                                final newUser server =  new newUser(context);
                                server.addUser("https://debugtm.herokuapp.com/user/newUser, mRequestQueue, new ServerCallback() {
                                            @Override
                                            public void onSuccess(JSONObject response) {
                                                openMain();
                                            }
                                        }
                                );*/

                                //finish();

                          /*  }
                            else {
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Registrazione fallita", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/
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

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}

