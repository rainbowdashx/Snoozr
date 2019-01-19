package com.morf.snoozr;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.plattysoft.leonids.ParticleSystem;

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

/*
                FlingAnimation anim = new FlingAnimation( findViewById(R.id.imgSheep), DynamicAnimation.TRANSLATION_X)
                    .setStartVelocity(200);
                anim.start();*/

            /*    ImageView imageView = (ImageView) findViewById(R.id.imgSheep);
                imageView.setVisibility(View.VISIBLE);

                imageView.animate().translationY(-500)
                        .setInterpolator(new AccelerateInterpolator())
                        .setDuration(1500)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {


                                ImageView imageView = (ImageView) findViewById(R.id.imgSheep);

                                                        imageView.animate().translationY(0)
                                        .setInterpolator(new AccelerateInterpolator())
                                        .setInterpolator(new BounceInterpolator())
                                        .setDuration(1500);
                            }
                        });
*/

                new ParticleSystem(SnoozrMain.this, 50, R.drawable.sheep_falling_animation, 3000)
                        .setSpeedModuleAndAngleRange(0.2f, 0.5f,220,250)
                        .setScaleRange(0.15f, 0.2f)
                        .setAcceleration(0.0005f, 90)
                        .oneShot(view, 1);

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
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.SnoozrTimepickerDialogStyle,
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.SnoozrTimepickerDialogStyle,
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
