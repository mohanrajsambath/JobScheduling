package com.customloader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mohanraj.S on 04/01/17.
 */

public class StringLoader extends AsyncTaskLoader<List<String>> {

    public StringLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        //super.onStartLoading();
        forceLoad();

    }

    @Override
    public List<String> loadInBackground() {
        List<String> data = Arrays.asList(getContext().getResources().getStringArray(R.array.media_names));


        return data;
    }

    @Override
    public void deliverResult(List<String> data) {
        super.deliverResult(data);
    }
}
