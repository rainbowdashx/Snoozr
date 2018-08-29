package com.morf.snoozr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class BedTimes extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(StartTimeMs);

        List<ContentItem> DataSet = new LinkedList<>();

        for (int i = 1; i < 10; i++) {
            Calendar tmp = (Calendar) currentTime.clone();
            tmp.add(Calendar.MINUTE, i * 90);
            ContentItem newItem = new ContentItem(tmp, currentTime);

            DataSet.add(0,newItem);
        }


        mAdapter = new ListAdapter(DataSet,null,true);

        mRecyclerView.setAdapter(mAdapter);
    }
}
