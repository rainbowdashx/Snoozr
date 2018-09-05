package com.morf.snoozr;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ContentItem {

    String Text;
    String Duration;
    Calendar DateTime;
    String Cycles;

    public ContentItem(Calendar InCalender,Calendar StartTime){

        long millis = InCalender.getTime().getTime() - StartTime.getTime().getTime();
        int hours = Math.abs( (int)  millis/(1000 * 60 * 60));
        int mins = Math.abs( (int)(millis/(1000*60)) % 60);
        int cycles = Math.abs((int) (millis / (90 * 1000)) / 60);

        Text = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(InCalender.getTime());
        Duration = "Nap for " + hours + "h : " + mins + "m";
        DateTime = InCalender;
        Cycles = String.valueOf(cycles) + " Cycles";

    }
}
