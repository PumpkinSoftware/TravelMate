package com.example.pumpkinsoftware.travelmate.my_on_checked_change_listener;

import android.widget.CompoundButton;

public class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
    private String pets_value = "false";

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            pets_value = "true";
        else
            pets_value = "false";
    }

    public String getValue() { return pets_value; }
}
