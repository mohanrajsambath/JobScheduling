package com.jobscheduling;

import android.util.Log;
import android.widget.Toast;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by Mohanraj.S on 03/01/17.
 */

public class MyServices extends JobService{
  public static final String  TAG = "MyService Class";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG,"Job Started");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG,"Job Stop");
        return false;
    }
}
