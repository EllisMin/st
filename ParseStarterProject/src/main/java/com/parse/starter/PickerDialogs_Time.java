package com.parse.starter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Ellis on 1/3/16.
 */
public class PickerDialogs_Time extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateSettings dateSettings = new DateSettings(getActivity());

        // To get time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog;
        TimeSettings timeSettings = new TimeSettings(getActivity());

        dialog = new TimePickerDialog(getActivity(), timeSettings, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));

        return dialog;
    }

}
