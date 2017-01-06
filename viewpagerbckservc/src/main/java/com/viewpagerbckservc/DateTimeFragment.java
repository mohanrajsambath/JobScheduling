package com.viewpagerbckservc;

/**
 * Created by Mohanraj.S on 05/01/17.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DateTimeFragment extends Fragment {

    private TextView date;
    private TextView time;
    private Intent intent;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateDataToUI(intent);
            }
        };
        intent = new Intent(getActivity(), DateTimeService.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().startService(intent);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((broadcastReceiver),
                new IntentFilter(DateTimeService.DATE_TIME_ACTION)
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().stopService(intent);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date_time, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date = (TextView) view.findViewById(R.id.txtvw_date);
        time = (TextView) view.findViewById(R.id.txtvw_time);
    }

    /**
     * Update date and time to textviews
     */
    private void updateDataToUI(Intent intent) {
        date.setText(intent.getStringExtra(DateTimeService.DATE_STAMP));
        time.setText(intent.getStringExtra(DateTimeService.TIME_STAMP));
    }
}