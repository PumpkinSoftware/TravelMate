package com.example.pumpkinsoftware.travelmate.min_max_filter;

import android.text.InputFilter;
import android.text.Spanned;

public class MinMaxFilter implements InputFilter {

    private int min, max;

    public MinMaxFilter(int minValue, int maxValue) {
        min = minValue;
        max = maxValue;
    }

    public MinMaxFilter(String minValue, String maxValue) {
        min = Integer.parseInt(minValue);
        max = Integer.parseInt(maxValue);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
