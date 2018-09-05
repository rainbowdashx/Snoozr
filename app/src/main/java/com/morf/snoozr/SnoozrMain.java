package com.morf.snoozr;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import java.util.Calendar;

public class SnoozrMain extends AppCompatActivity {

    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snoozr_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_snoozr_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SetAlarm(View view) {

        Calendar currentTime = Calendar.getInstance();

        Intent i = new Intent(this, SleepNow.class);
        i.putExtra("StartTime", currentTime.getTimeInMillis());
        startActivity(i);

    }


    public void SelectWakeUp(View view) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);


        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        WakeUpTimeSelected(hourOfDay,minute);
                    }
                }, mHour, mMinute, true);

        timePickerDialog.show();

    }

    public void WakeUpTimeSelected(int hourOfDay, int minute){
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
        currentTime.set(Calendar.MINUTE,minute);

        Intent i = new Intent(this, BedTimes.class);
        i.putExtra("StartTime", currentTime.getTimeInMillis());
        startActivity(i);
    }


    public void SelectBedTime(View view) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        BedTimeSelected(hourOfDay,minute);
                    }
                }, mHour, mMinute, true);

        timePickerDialog.show();
    }

    public void BedTimeSelected(int hourOfDay, int minute){
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
        currentTime.set(Calendar.MINUTE,minute);

        Intent i = new Intent(this, SleepNow.class);
        i.putExtra("StartTime", currentTime.getTimeInMillis());
        startActivity(i);
    }

    public void PowerNap(View view) {

        Intent i = new Intent(this, PowerNap.class);

        startActivity(i);
    }
}
