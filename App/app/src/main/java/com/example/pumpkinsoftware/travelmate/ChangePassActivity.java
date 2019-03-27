package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {
    private Context context;
    private FirebaseUser user;
    private String oldPass;
    private String newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        context = (Context) this;

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            Toast.makeText(this, "Errore: riprovare", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        final TextView oldPassView = findViewById(R.id.oldPass2);
        final TextView newPassView = findViewById(R.id.newPass2);

        final Button confirm = findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = oldPassView.getText().toString();
                newPass = newPassView.getText().toString();
                changePass();
            }
        });

    }

    private void changePass() {
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Password aggiornata", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(context, "Errore nell'aggiornamento della password, riprovare", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(context, "Errore: autenticazione fallita", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
