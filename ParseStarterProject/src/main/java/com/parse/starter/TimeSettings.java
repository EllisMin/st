package com.parse.starter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by taehongmin on 1/3/16.
 */
public class TimeSettings implements TimePickerDialog.OnTimeSetListener{

    Context context;

    public TimeSettings(Context context){
        this.context = context;
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        try {
            // Changing 24 hour to 12 hour format
            String _24HourTime = hourOfDay + ":" + minute;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
//            System.out.println(_24HourDt);
//            System.out.println(_12HourSDF.format(_24HourDt));

            Create.timeTextView.setText(_12HourSDF.format(_24HourDt));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }
}
