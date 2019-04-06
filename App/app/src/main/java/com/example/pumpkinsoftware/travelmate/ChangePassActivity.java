package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
    private String oldPass, newPass, confirmNewPass;
    private Button confirm;

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
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            Toast.makeText(this, "Errore: riprovare", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        /*final EditText oldPassView = findViewById(R.id.oldPass2);
        final EditText newPassView = findViewById(R.id.newPass2);
        final EditText confirmNewPassView = findViewById(R.id.confirmNewPass2);*/

        final TextInputEditText oldPassView = findViewById(R.id.oldPass2);
        final TextInputEditText newPassView = findViewById(R.id.newPass2);
        final TextInputEditText confirmNewPassView = findViewById(R.id.confirmNewPass2);

        confirm = findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = oldPassView.getText().toString();
                newPass = newPassView.getText().toString();
                confirmNewPass = confirmNewPassView.getText().toString();
                changePass();
            }
        });

    }

    private void changePass() {
        confirm.setEnabled(false);

        if(newPass.isEmpty() || newPass.length() < 8) {
            Toast.makeText(context, "La nuova password deve contenere almeno 8 caratteri", Toast.LENGTH_SHORT).show();
            confirm.setEnabled(true);
            return;
        }
        else if(!newPass.equals(confirmNewPass)) {
            Toast.makeText(context, "Le due password non coincidono", Toast.LENGTH_SHORT).show();
            confirm.setEnabled(true);
            return;
        }
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
