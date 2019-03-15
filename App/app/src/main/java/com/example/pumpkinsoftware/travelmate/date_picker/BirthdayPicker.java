package com.example.pumpkinsoftware.travelmate.date_picker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class BirthdayPicker implements OnTouchListener, DatePickerDialog.OnDateSetListener {
    private EditText edit_text;
    // Day, month and year choosen by user
    private int day;
    private int month;
    private int year;
    Calendar calendar;
    // Today
    private int today_day;
    private int today_month;
    private int today_year;
    private Context context;

    private boolean date_set = false;

    public BirthdayPicker(Context c, EditText editTextViewID, Calendar cal) {
        /*Activity act = (Activity) context;
        edit_text = (EditText) act.findViewById(editTextViewID);*/
        edit_text = editTextViewID;
        edit_text.setOnTouchListener(this);
        context = c;
        calendar = cal;
        today_day = calendar.get(Calendar.DAY_OF_MONTH);
        today_month = calendar.get(Calendar.MONTH);
        today_year = calendar.get(Calendar.YEAR);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date_set = true;
        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;
        updateDisplay();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            DatePickerDialog d = new DatePickerDialog(context, this, today_year, today_month, today_day);
            d.getDatePicker().setMaxDate(new Date().getTime());
            d.show();
        }
        return false;
    }

    // updates the date in the EditText
    private void updateDisplay() {
        // Month is 0 based so add 1
        edit_text.setText(new StringBuilder()
                .append(day).append("/").append(month + 1).append("/").append(year));
    }

    public boolean hasDateSet() {
        return date_set;
    }

    public int getSetDay() {
        if (date_set) return day;
        return -1;
    }

    public int getSetMonth() {
        if (date_set) return month + 1;
        return -1;
    }

    public int getSetYear() {
        if (date_set) return year;
        return -1;
    }

    public long getSetDateInMillis() {

        if (date_set) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            /*int diff = ;
            c.add(Calendar.YEAR, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DAY_OF_MONTH, 1)*/
            return c.getTimeInMillis();
        }

        return -1;
    }

}