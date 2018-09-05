package com.morf.snoozr;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class PowerNap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_nap);
    }

    public void NapWakemeUp(View view) {

        Calendar currentTime = Calendar.getInstance();
        Calendar napTime = (Calendar) currentTime.clone();
        napTime.add(Calendar.MINUTE, 20);

        final ContentItem item = new ContentItem(napTime,currentTime);

        AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.SnoozrAlertDialogStyle);
        adb.setTitle("Set Alarm for: " + item.Text);
        adb.setIcon(R.drawable.ic_baseline_alarm_add_24px);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, item.Text);
                i.putExtra(AlarmClock.EXTRA_HOUR, item.DateTime.get(Calendar.HOUR_OF_DAY));
                i.putExtra(AlarmClock.EXTRA_MINUTES, item.DateTime.get(Calendar.MINUTE));
                i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                startActivity(i);



            }
        });


        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        adb.show();
    }
}
