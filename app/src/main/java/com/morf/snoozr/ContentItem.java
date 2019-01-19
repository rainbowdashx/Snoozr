package com.morf.snoozr;


import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ContentItem {

    String Text;
    String Duration;
    Calendar DateTime;
    String Cycles;

    public ContentItem(Calendar InCalender, Calendar StartTime, Context ctx){

        long millis = InCalender.getTime().getTime() - StartTime.getTime().getTime();
        int hours = Math.abs( (int)  millis/(1000 * 60 * 60));
        int mins = Math.abs( (int)(millis/(1000*60)) % 60);
        int cycles = Math.abs((int) (millis / (90 * 1000)) / 60);

        Text = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(InCalender.getTime());

        //Text = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(InCalender.getTime());

        Duration = ctx.getString(R.string.nap_for)+ " " + hours + ctx.getString(R.string.hours_h_short) +" : " + mins + ctx.getString(R.string.minutes_m_short);
        DateTime = InCalender;
        Cycles = String.valueOf(cycles) + " " +ctx.getString(R.string.cycles);

    }
}
