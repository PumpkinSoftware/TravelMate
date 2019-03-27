package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.content_fragment_travels.Tab1;
import com.example.pumpkinsoftware.travelmate.pager_adapter.SlidePagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.List;

public class ViaggiFragment extends Fragment {
    public FloatingActionButton fabBtn;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.fragment_viaggi, container, false);
         context = getContext();

        fabBtn = (FloatingActionButton) view.findViewById(R.id.floatbutton);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CreationTrip.class);
                startActivityForResult(intent, Tab1.REQUEST_CODE_UPDATE);
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter mAdapter = new SlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout = view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                int position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS

                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }

    public ViewPager getViewPager() { return viewPager; }

    // Included to allow fragments to receive onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}