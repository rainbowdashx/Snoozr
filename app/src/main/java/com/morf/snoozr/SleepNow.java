package com.morf.snoozr;

import android.content.Intent;
import android.provider.AlarmClock;
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


        //CALCULATE TIMES
        Calendar currentTime = Calendar.getInstance();
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
            public void onItemClick(ContentItem item) {

                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, item.Text);
                i.putExtra(AlarmClock.EXTRA_HOUR, item.DateTime.get(Calendar.HOUR_OF_DAY));
                i.putExtra(AlarmClock.EXTRA_MINUTES, item.DateTime.get(Calendar.MINUTE));
                i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                startActivity(i);

                finish();

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
