package com.parse.starter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by Ellis on 1/3/16.
 */
public class PickerDialogs extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateSettings dateSettings = new DateSettings(getActivity());


        // To get a current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;


        // Initialize date picker object
        dialog = new DatePickerDialog(getActivity(), dateSettings, year, month, date);

        return dialog;
    }


}
