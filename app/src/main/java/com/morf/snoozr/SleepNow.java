package com.morf.snoozr;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SleepNow extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_now);
        mRecyclerView = findViewById(R.id.ListViewSleepNow);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent In = getIntent();

        Long StartTimeMs = In.getLongExtra("StartTime", 0);



        //CALCULATE TIMES
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(StartTimeMs);
        currentTime.add(Calendar.MINUTE, 15);


        List<ContentItem> DataSet = new LinkedList<>();

        for (int i = 1; i < 10; i++) {
            Calendar tmp = (Calendar) currentTime.clone();
            tmp.add(Calendar.MINUTE, i * 90);
            ContentItem newItem = new ContentItem(tmp, currentTime);
            
            DataSet.add(newItem);
        }


        mAdapter = new ListAdapter(DataSet, new OnItemClickListener() {
            @Override
            public void onItemClick(final ContentItem item) {

                AlertDialog.Builder adb = new AlertDialog.Builder(SleepNow.this, R.style.SnoozrAlertDialogStyle);
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

                        finish();

                    }
                });


                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                adb.show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
