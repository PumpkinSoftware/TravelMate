package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class FirebaseDynamicLinkHandler extends AppCompatActivity {

    private Context contesto;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_link);
        contesto = this;

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                            // Get deep link from result (may be null if no link is found)
                            Uri deepLink = null;
                            if (pendingDynamicLinkData != null) {
                                deepLink = pendingDynamicLinkData.getLink();
                                //System.out.println("This dynamic link " + deepLink);
                                String tripId = deepLink.toString().substring(deepLink.toString().lastIndexOf("lookThisTrip?id=") + 16);

                                //TODO
                                //Qui hai il tripId e devi andare ad aprire la card!


                            } else {
                                Toast.makeText(contesto, "Viaggio non disponibile", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(contesto, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(contesto, "Viaggio non disponibile", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(contesto, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
        else{
            Intent intent = new Intent(contesto, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
