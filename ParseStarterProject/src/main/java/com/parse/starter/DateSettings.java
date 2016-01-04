package com.parse.starter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by Ellis on 1/3/16.
 */
public class DateSettings implements DatePickerDialog.OnDateSetListener {

    Context context;
    public DateSettings(Context context) {
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Create.dateTextView.setText(String.valueOf(monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
//        Toast.makeText(context, "Selected date" + monthOfYear + " / " + dayOfMonth + " / " + year, Toast.LENGTH_LONG).show();

    }
}
