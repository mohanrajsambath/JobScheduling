package com.viewpagerbckservc;

/**
 * Created by Mohanraj.S on 05/01/17.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DateTimeService extends Service {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private Intent intent;
    private final Handler handler = new Handler();
    private LocalBroadcastManager broadcastManager;

    public final static String TIME_STAMP = "Time";
    public final static String DATE_STAMP = "Date";
    public final static String DATE_TIME_ACTION = "info.devexchanges.dateservice";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendUpdatesToUI);
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            getDateTimes();
            handler.postDelayed(this, 1000); // 1 seconds
        }
    };

    public void getDateTimes() {
        calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = "Current Date: " + dateFormat.format(calendar.getTime());
        String time = "Current Time: " + hours + ":" + minutes + ":" + seconds;

        //put info through intent
        intent = new Intent(DATE_TIME_ACTION);
        intent.putExtra(TIME_STAMP, time);
        intent.putExtra(DATE_STAMP, formattedDate);

        broadcastManager.sendBroadcast(intent);
    }
}