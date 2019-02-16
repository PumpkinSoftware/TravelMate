package com.example.pumpkinsoftware.travelmate;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class OpzioniFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_opzioni, container, false);

        Button b_logout = (Button) inflate.findViewById(R.id.button_logout);
        b_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Logout effettuato", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                openLogin();
            }
        });
        return inflate;
    }

    public void openLogin(){
        Intent intent=new Intent(this.getContext(),LoginActivity.class);
        startActivity(intent);
    }
}

