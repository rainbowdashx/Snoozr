package com.morf.snoozr;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class BedTimes extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String mDateText;
    private Calendar mCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_times);


        mRecyclerView = findViewById(R.id.ListViewBedTimes);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent In = getIntent();

        Long StartTimeMs = In.getLongExtra("StartTime", 0);


        //CALCULATE TIMES
        mCurrentTime = Calendar.getInstance();
        //Set time to intent passed value
        mCurrentTime.setTimeInMillis(StartTimeMs);

        //keep Actual time for displaying extra 15 minutes of sleep
        Calendar ActualTime = (Calendar) mCurrentTime.clone();
        mCurrentTime.add(Calendar.MINUTE, -15);


        List<ContentItem> DataSet = new LinkedList<>();

        for (int i = 1; i < 10; i++) {
            Calendar tmp = (Calendar) mCurrentTime.clone();
            tmp.add(Calendar.MINUTE, i * -90);


            ContentItem newItem = new ContentItem(tmp, ActualTime);

            DataSet.add(0, newItem);

        }
        mAdapter = new ListAdapter(DataSet, null, true);

        mRecyclerView.setAdapter(mAdapter);


        Button btnWakeup = findViewById(R.id.btnSetAlarmWakeUp);
        mDateText = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(mCurrentTime.getTime());
        String ButtonText = "Set Alarm at: " + mDateText;
        btnWakeup.setText(ButtonText);

    }

    public void SetAlarmWakeup(View view) {

        AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.SnoozrAlertDialogStyle);
        adb.setTitle("Set Alarm for: " + mDateText);
        adb.setIcon(R.drawable.ic_baseline_alarm_add_24px);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, mDateText);
                i.putExtra(AlarmClock.EXTRA_HOUR, mCurrentTime.get(Calendar.HOUR_OF_DAY));
                i.putExtra(AlarmClock.EXTRA_MINUTES, mCurrentTime.get(Calendar.MINUTE));
                i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                startActivity(i);

                finish();

            }
        });


        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        adb.show();


    }
}
