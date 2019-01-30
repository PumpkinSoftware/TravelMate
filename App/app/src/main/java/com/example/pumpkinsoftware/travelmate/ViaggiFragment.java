package com.example.pumpkinsoftware.travelmate;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViaggiFragment extends Fragment implements View.OnClickListener {
    public  FloatingActionButton fabBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View inflate = inflater.inflate(R.layout.fragment_viaggi, container, false);
        //copiato dal tutorial prima che funzionasse da fare tentativi se serve
        fabBtn = (FloatingActionButton) inflater.inflate(R.layout.fragment_viaggi, container, false).findViewById(R.id.floatbutton);
        fabBtn.setOnClickListener(this);
        //fine
        return inflate;

    }
    //necessario per implements del tutorial
    public void onClick(View v) {
        Intent intent = new Intent(ViaggiFragment.this.getActivity(), CreationTrip.class);
        startActivity(intent);
    };
    //il metodo run this è dichiarato nel main, l'activity genitore

}