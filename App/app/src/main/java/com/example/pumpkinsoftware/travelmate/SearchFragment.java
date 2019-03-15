package com.example.pumpkinsoftware.travelmate;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.pumpkinsoftware.travelmate.date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.min_max_filter.MinMaxFilter;
import com.example.pumpkinsoftware.travelmate.search_on_click_listener.SearchOnClickListener;

import java.util.Calendar;

import io.apptik.widget.MultiSlider;

public class SearchFragment extends Fragment {
    private RequestQueue mQueue;
    private int id_radio_vehicle = -1;
    private int id_radio_tag = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        final EditText min1 = (EditText) view.findViewById(R.id.budget_min_value);
        final EditText max1 = (EditText) view.findViewById(R.id.budget_max_value);
        final EditText min2 = (EditText) view.findViewById(R.id.group_min_value);
        final EditText max2 = (EditText) view.findViewById(R.id.group_max_value);
        final SearchView searchView = (SearchView) view.findViewById(R.id.search_bar);
        final TextInputEditText from = (TextInputEditText) view.findViewById(R.id.from_text);
        final TextInputEditText to = (TextInputEditText) view.findViewById(R.id.to_text);
        final EditText departure_date = (EditText) view.findViewById(R.id.departure);
        final EditText return_date = (EditText) view.findViewById(R.id.ret);


        //mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // If click on bg, focus is deleted
        view.findViewById(R.id.scroll_child).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), v.toString(), Toast.LENGTH_SHORT).show();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
        });

        // Set the searchable configuration for key-words
        Activity activity = getActivity();
        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

        /* It extends searchview clickable area */
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        // If click on swap button, values of editexts are swapped
        view.findViewById(R.id.swap1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    String tmp = from.getText().toString();
                    from.setText(to.getText());
                    to.setText(tmp);
                }
            }
        });

        /* Utilities for Date Picker Dialog */
        final Calendar calendar = Calendar.getInstance();
        EditTextDatePicker departure = new EditTextDatePicker(getContext(), departure_date, calendar);
        EditTextDatePicker ret = new EditTextDatePicker(getContext(), return_date, calendar, departure);
        departure.setOther(ret);

        /* Multi Slider */
        final MultiSlider b_multiSlider = (MultiSlider) view.findViewById(R.id.budget_range_slider);
        final MultiSlider g_multiSlider = (MultiSlider) view.findViewById(R.id.group_range_slider);

        /* Set text & filters for EditText linked to multi slider */
        int mi1 = b_multiSlider.getThumb(0).getValue();
        int ma1 = b_multiSlider.getThumb(1).getValue();
        min1.setText(String.valueOf(mi1));
        max1.setText(String.valueOf(ma1));
        min1.setFilters(new InputFilter[]{new MinMaxFilter(mi1, ma1)});
        max1.setFilters(new InputFilter[]{new MinMaxFilter(mi1, ma1)});

        int mi2 = g_multiSlider.getThumb(0).getValue();
        int ma2 = g_multiSlider.getThumb(1).getValue();
        min2.setText(String.valueOf(mi2));
        max2.setText(String.valueOf(ma2));
        min2.setFilters(new InputFilter[]{new MinMaxFilter(mi2, ma2)});
        max2.setFilters(new InputFilter[]{new MinMaxFilter(mi2, ma2)});

        /* Listener multi slider */
        b_multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider,
                                       MultiSlider.Thumb thumb,
                                       int thumbIndex,
                                       int value) {
                if (thumbIndex == 0) {
                    if (Integer.parseInt(min1.getText().toString()) != value)
                        min1.setText(String.valueOf(value));
                } else {
                    if (Integer.parseInt(max1.getText().toString()) != value)
                        max1.setText(String.valueOf(value));
                }
            }

            /*@Override
            public void onStartTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            }

            @Override
            public void onStopTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            } */
        });

        g_multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if (thumbIndex == 0) {
                    if (Integer.parseInt(min2.getText().toString()) != value)
                        min2.setText(String.valueOf(value));
                } else {
                    if (Integer.parseInt(max2.getText().toString()) != value)
                        max2.setText(String.valueOf(value));
                }
            }

            /*@Override
            public void onStartTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            }

            @Override
            public void onStopTrackingTouch(MultiSlider multiSlider, MultiSlider.Thumb thumb, int value) {

            }*/
        });

        // Listener for editText, when min is setted, relative thumb position is changed
        min1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int v = Integer.parseInt(s.toString());
                    MultiSlider.Thumb t = b_multiSlider.getThumb(0);
                    if (t.getValue() != v)
                        t.setValue(v);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Inserisci un numero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listener for editText, when max is setted, relative thumb position is changed
        max1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int v = Integer.parseInt(s.toString());
                    MultiSlider.Thumb t = b_multiSlider.getThumb(1);
                    if (t.getValue() != v)
                        t.setValue(v);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Inserisci un numero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Same for Group
        // Listener for editText, when min is setted, relative thumb position is changed
        min2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int v = Integer.parseInt(s.toString());
                    MultiSlider.Thumb t = g_multiSlider.getThumb(0);
                    if (t.getValue() != v)
                        t.setValue(v);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Inserisci un numero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listener for editText, when max is setted, relative thumb position is changed
        max2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int v = Integer.parseInt(s.toString());
                    MultiSlider.Thumb t = g_multiSlider.getThumb(1);
                    if (t.getValue() != v)
                        t.setValue(v);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Inserisci un numero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Radio vehicle
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.vehicle_radio);
        final RadioButton train = (RadioButton) view.findViewById(R.id.treno);
        final RadioButton auto = (RadioButton) view.findViewById(R.id.auto);

        // Method to deselect with a click a selected button
        View.OnClickListener rlis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if(id_radio_vehicle == id)   radioGroup.clearCheck();
                else                         id_radio_vehicle = id;
            }
        };

        train.setOnClickListener(rlis);
        auto.setOnClickListener(rlis);

        // Radio tag
        final RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.tag_radio);
        final RadioButton tag1 = (RadioButton) view.findViewById(R.id.tag1);
        final RadioButton tag2 = (RadioButton) view.findViewById(R.id.tag2);
        final RadioButton tag3 = (RadioButton) view.findViewById(R.id.tag3);
        final RadioButton tag4 = (RadioButton) view.findViewById(R.id.tag4);

        View.OnClickListener rlis2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if(id_radio_tag == id)   radioGroup2.clearCheck();
                else                     id_radio_tag = id;
            }
        };

        tag1.setOnClickListener(rlis2);
        tag2.setOnClickListener(rlis2);
        tag3.setOnClickListener(rlis2);
        tag4.setOnClickListener(rlis2);


        //Button search
        Button b_search = (Button) view.findViewById(R.id.search_button);
        b_search.setOnClickListener(new SearchOnClickListener(getContext(), getActivity().getSupportFragmentManager(),
                from, to, departure, ret, radioGroup, radioGroup2, min1, max1, min2, max2));//, mQueue));

        return view;
    }

}