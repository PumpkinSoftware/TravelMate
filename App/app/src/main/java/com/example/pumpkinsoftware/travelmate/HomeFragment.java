package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ClientServerInteraction;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context context;
    private RequestQueue mRequestQueue;
    private String URL="https://debugtm.herokuapp.com/trip/lastTripsCreated?limit=50";;
    private ArrayList<Trip> trips;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        setHasOptionsMenu(true);

        final RecyclerView rvTrips = (RecyclerView) view.findViewById(R.id.recyclerview);
        /*ClientServerInteraction cs = new ClientServerInteraction(context);
        cs.getTripsFromServer("http://localhost:8095/trip/allTrips/", );*/

        // Initialize trips
        //ArrayList<Trip> trips = Trip.createTripsList(20);

        // Create adapter passing in the sample user data
        // TripsAdapter adapter = new TripsAdapter(trips);
        // Attach the adapter to the recyclerview to populate items
        // rvContacts.setAdapter(adapter);

        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));
        trips=new ArrayList<Trip>();

        mRequestQueue= Volley.newRequestQueue(context);
        new ClientServerInteraction(context,rvTrips).getTripsFromServer(URL,mRequestQueue,trips);

        //swipe da finire
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //temporaneo
                        rvTrips.setLayoutManager(new LinearLayoutManager(context));
                        trips=new ArrayList<Trip>();

                        mRequestQueue= Volley.newRequestQueue(context);
                        new ClientServerInteraction(context,rvTrips).getTripsFromServer(URL,mRequestQueue,trips);
                        swipe.setRefreshing(false);

                    }
                },3000);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sharing_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                context.startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;

            default:
                return false;
        }
    }
}

/*

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class CardScrollActivity extends Activity {

    private List<CardBuilder> mCards;
    private CardScrollView mCardScrollView;
    private ExampleCardScrollAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createCards();

        mCardScrollView = new CardScrollView(this);
        mAdapter = new ExampleCardScrollAdapter();
        mCardScrollView.setAdapter(mAdapter);
        mCardScrollView.activate();
        setupClickListener();
        setContentView(mCardScrollView);
    }

    private void setupClickListener() {
        mCardScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.TAP);
            }
        });
    }

    private void createCards() {
        mCards = new ArrayList<CardBuilder>();

        mCards.add(new CardBuilder(this, CardBuilder.Layout.TEXT)
                .setText("This card has a footer.")
                .setFootnote("I'm the footer!"));

        mCards.add(new CardBuilder(this, CardBuilder.Layout.CAPTION)
                .setText("This card has a puppy background image.")
                .setFootnote("How can you resist?")
                .addImage(R.mipmap.placeholder_image));

        mCards.add(new CardBuilder(this, CardBuilder.Layout.COLUMNS)
                .setText("This card has a mosaic of puppies.")
                .setFootnote("Aren't they precious?")
                .addImage(R.mipmap.placeholder_image);
                .addImage(R.mipmap.placeholder_image);
                .addImage(R.mipmap.placeholder_image));
    }

    private class ExampleCardScrollAdapter extends CardScrollAdapter {

        @Override
        public int getPosition(Object item) {
            return mCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return CardBuilder.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position){
            return mCards.get(position).getItemViewType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).getView(convertView, parent);
        }

        // Inserts a card into the adapter, without notifying.
        public void insertCardWithoutNotification(int position, CardBuilder card) {
            mCards.add(position, card);
        }
    }

    private void insertNewCard(int position, CardBuilder card) {
        // Insert new card in the adapter, but don't call
        // notifyDataSetChanged() yet. Instead, request proper animation
        // to inserted card from card scroller, which will notify the
        // adapter at the right time during the animation.
        mAdapter.insertCardWithoutNotification(position, card);
        mCardScrollView.animate(position, CardScrollView.Animation.INSERTION);
    }
}


*/