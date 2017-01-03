package com.jobscheduling;

import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

public class MainActivity extends AppCompatActivity {
    private static final int JOB_ID = 100;
    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJobScheduler=JobScheduler.getInstance(this);
        construct_Job();
    }

    private void construct_Job(){
         JobInfo.Builder builer = new JobInfo.Builder(JOB_ID,new ComponentName(this,MyServices.class));
        builer.setPeriodic(2000)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        .setPersisted(true);
        mJobScheduler.schedule(builer.build());
    }
}
