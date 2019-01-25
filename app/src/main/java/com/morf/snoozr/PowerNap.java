package com.morf.snoozr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;

public class PowerNap extends AppCompatActivity {


    private MediaPlayer mMediaPlayer;
    private int SelectedResourceID = R.raw.pinknoise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_nap);


        Spinner spinner = findViewById(R.id.powernap_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.powernap_array, R.layout.powernap_choice_item);

        adapter.setDropDownViewResource(R.layout.snoozr_spinner_dropdown);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TypedArray mediaArray = getResources().obtainTypedArray(R.array.powernap_media);
                SelectedResourceID = mediaArray.getResourceId(position, R.raw.pinknoise);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }


    @Override
    protected void onStop() {
        super.onStop();

        // StopMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        StopMediaPlayer();

    }

    public void NapWakemeUp(View view) {

        Calendar currentTime = Calendar.getInstance();
        Calendar napTime = (Calendar) currentTime.clone();

        NumberPicker MinutePicker = findViewById(R.id.MinutePicker);
        NumberPicker HourPicker = findViewById(R.id.HourPicker);

        int Minutes = MinutePicker.getValue();
        int Hours = HourPicker.getValue();

        napTime.add(Calendar.MINUTE, Minutes);
        napTime.add(Calendar.HOUR, Hours);

        final ContentItem item = new ContentItem(napTime, currentTime, getApplicationContext());

        AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.SnoozrAlertDialogStyle);
        adb.setTitle(getString(R.string.set_alarm_for) + item.Text);
        adb.setIcon(R.drawable.ic_baseline_alarm_add_24px);

        adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, item.Text);
                i.putExtra(AlarmClock.EXTRA_HOUR, item.DateTime.get(Calendar.HOUR_OF_DAY));
                i.putExtra(AlarmClock.EXTRA_MINUTES, item.DateTime.get(Calendar.MINUTE));
                i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                startActivity(i);


            }
        });


        adb.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        adb.show();
    }

    public void PlayPinkNoise(View view) {

        StopMediaPlayer();

        mMediaPlayer = MediaPlayer.create(this, SelectedResourceID);
        mMediaPlayer.start();
    }

    public void StopPinkNoise(View view) {
        StopMediaPlayer();
    }

    public void TogglePinkNoise(View view) {

        if (mMediaPlayer != null) {

            StopPinkNoise(view);
            ImageButton btn = (ImageButton) view;
            //view.setBackgroundResource(android.R.drawable.ic_media_play);
            btn.setImageResource(android.R.drawable.ic_media_play);

        } else {
            PlayPinkNoise(view);
            ImageButton btn = (ImageButton) view;
            btn.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    protected void StopMediaPlayer() {

        if (mMediaPlayer != null) {

            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }

            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


}
