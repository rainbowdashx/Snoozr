package com.morf.snoozr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.provider.AlarmClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class PowerNap extends AppCompatActivity {


    private List<MediaPlayer> MediaPlayerPool = new ArrayList<>();


    private List<Integer> SelectedResourceIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_nap);
        android.support.v7.widget.GridLayout Grid = (android.support.v7.widget.GridLayout) findViewById(R.id.SoundCueGrid);

        TypedArray mediaArray = getResources().obtainTypedArray(R.array.powernap_media);
        TypedArray iconsArray = getResources().obtainTypedArray(R.array.powernap_icons);

        for (int i = 0; i < mediaArray.length(); i++) {


            ToggleButton newButton = (ToggleButton) getLayoutInflater().inflate(R.layout.soundcue_togglebutton, null);


            mediaArray.getResourceId(i, R.raw.pinknoise);

            int IconId = iconsArray.getResourceId(i, R.drawable.dove);
            newButton.setBackgroundDrawable(ContextCompat.getDrawable(this, IconId));


            int finalIdx = i;
            newButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton btn, boolean checked) {

                    if (checked) {
                        AddSoundCue(finalIdx);
                        btn.setBackgroundTintList(ContextCompat.getColorStateList(btn.getContext(), R.color.colorAccent));
                    } else {
                        AddSoundCue(finalIdx);
                        btn.setBackgroundTintList(ContextCompat.getColorStateList(btn.getContext(), R.color.colorBlackBackground));
                    }

                }
            });

            Grid.addView(newButton);
        }

        mediaArray.recycle();
        iconsArray.recycle();
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


    public void StopPinkNoise() {
        StopMediaPlayer();
    }

    public void TogglePinkNoise(View view) {

        if (MediaPlayerPool.size() > 0) {

            StopPinkNoise();
            ImageButton btn = (ImageButton) view;
            //view.setBackgroundResource(android.R.drawable.ic_media_play);
            btn.setImageResource(android.R.drawable.ic_media_play);

        } else {

            PlaySoundCues();
            ImageButton btn = (ImageButton) view;
            btn.setImageResource(android.R.drawable.ic_media_pause);
        }

        if (MediaPlayerPool.size() == 0) {
            ImageButton btn = (ImageButton) view;
            btn.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    public void PlaySoundCue(int CueID) {

        MediaPlayer LocalPlayer = MediaPlayer.create(this, CueID);
        LocalPlayer.start();
        MediaPlayerPool.add(LocalPlayer);
    }

    public void PlaySoundCues() {

        StopMediaPlayer();

        for (int Item : SelectedResourceIDs) {
            PlaySoundCue(Item);
        }
    }

    protected void StopMediaPlayer() {


        for (MediaPlayer Item : MediaPlayerPool) {
            if (Item != null) {

                if (Item.isPlaying()) {
                    Item.stop();
                }

                Item.reset();
                Item.release();
            }
        }

        MediaPlayerPool.clear();
    }


    public void AddSoundCue(int idxCue) {

        if (idxCue >= 0) {
            TypedArray mediaArray = getResources().obtainTypedArray(R.array.powernap_media);
            int LocalResourceID = mediaArray.getResourceId(idxCue, R.raw.pinknoise);
            if (SelectedResourceIDs.contains(LocalResourceID)) {

                SelectedResourceIDs.remove(Integer.valueOf(LocalResourceID));

            } else {
                SelectedResourceIDs.add(LocalResourceID);
            }

            mediaArray.recycle();
        }

        if (MediaPlayerPool.size() > 0) {
            PlaySoundCues();
        }
    }
}
