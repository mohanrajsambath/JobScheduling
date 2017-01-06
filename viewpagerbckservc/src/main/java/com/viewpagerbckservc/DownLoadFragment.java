package com.viewpagerbckservc;

import android.support.v4.app.Fragment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mohanraj.S on 05/01/17.
 */

public class DownLoadFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView percentage;
    private View btnDownload;
    private Intent intent;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateProgressBar(intent);
            }
        };
        intent = new Intent(getActivity(), DownloadService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_down_load, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        percentage = (TextView) view.findViewById(R.id.percent);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        btnDownload = (View) view.findViewById(R.id.btn_start);

        btnDownload.setOnClickListener(onStartDownloadListener());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isServiceRunning(DownloadService.class)) {
            btnDownload.setVisibility(View.GONE);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((broadcastReceiver),
                new IntentFilter(DownloadService.DOWNLOAD_ACTION)
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    /**
     * Update the percentage of download process to TextView and Progress Bar
     * @param intent
     */
    private void updateProgressBar(Intent intent) {
        String progress = intent.getStringExtra(DownloadService.PERCENTAGE_STAMP);
        if (progress != null ) {

            //int xProgress = Math.abs(Integer.parseInt(progress));
            progressBar.setProgress(Math.abs(Integer.parseInt(progress)));
            percentage.setText(progress + "%");

            //stop download service when task completed
            if (progress.equals("100")) {
                getActivity().stopService(intent);
            }
        }
    }

    /**
     * Handling button Download event
     * @return
     */
    private View.OnClickListener onStartDownloadListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(intent);
                btnDownload.setVisibility(View.GONE);

                //init BroadcastManager instance when service start
                LocalBroadcastManager.getInstance(getActivity()).registerReceiver((broadcastReceiver),
                        new IntentFilter(DownloadService.DOWNLOAD_ACTION)
                );
            }
        };
    }

    /**
     * Check if Service is running
     * @param serviceClass
     * @return true if one or more service is running in background thread
     */
    private boolean isServiceRunning(Class serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}