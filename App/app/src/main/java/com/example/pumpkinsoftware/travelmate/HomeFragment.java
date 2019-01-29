package com.example.pumpkinsoftware.travelmate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pumpkinsoftware.travelmate.glide.GlideApp;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*Loading images with glide */
        /*ImageView img = view.findViewById(R.id.travel_image);

        GlideApp.with(view)
                .load(R.drawable.new_york)
                .placeholder(R.drawable.placeholder_image)
                .into(img);

        img = getView().findViewById(R.id.travel_image2);

        GlideApp.with(this)
                .load(R.drawable.amsterdam)
                .placeholder(R.drawable.placeholder_image)
                .into(img);

        img = getView().findViewById(R.id.travel_image3);

        GlideApp.with(this)
                .load(R.drawable.dubai)
                .placeholder(R.drawable.placeholder_image)
                .into(img);*/


        return view;
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
                .addImage(R.drawable.puppy_bg));

        mCards.add(new CardBuilder(this, CardBuilder.Layout.COLUMNS)
                .setText("This card has a mosaic of puppies.")
                .setFootnote("Aren't they precious?")
                .addImage(R.drawable.puppy_small_1);
                .addImage(R.drawable.puppy_small_2);
                .addImage(R.drawable.puppy_small_3));
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