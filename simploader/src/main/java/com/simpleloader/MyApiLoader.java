package com.simpleloader;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.simpleloader.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mohanraj.S on 03/01/17.
 */

public class MyApiLoader extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    TextView tvJsonResult;
    NestedScrollView nested_scrl_home;
    private String TAG = MyApiLoader.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myloader);
        tvJsonResult = (TextView) findViewById(R.id.tv_json_result);
        nested_scrl_home = (NestedScrollView) findViewById(R.id.nested_scrl_home);
        //getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>)this).forceLoad();
        configIntialApi();
        if (nested_scrl_home != null) {

            nested_scrl_home.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY > oldScrollY) {
                        Log.i(TAG, "Scroll DOWN");
                        //configApi();
                    }
                    if (scrollY < oldScrollY) {
                        Log.i(TAG, "Scroll UP");
                        //configApi();
                    }

                    if (scrollY == 0) {
                        Log.i(TAG, "TOP SCROLL");
                        configRetartApi();
                    }

                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        Log.i(TAG, "BOTTOM SCROLL");
                        configRetartApi();
                    }
                }
            });
        }

    }

    private void configIntialApi() {
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        //getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>)this).forceLoad();
    }

    private void configRetartApi() {
        tvJsonResult.setText("Loading Again....");
        //getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        getSupportLoaderManager().restartLoader(0, null, this).forceLoad();
        //getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<String>)this).forceLoad();
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new FetchData(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        tvJsonResult.setText(data);


    }

    @Override
    public void onLoaderReset(Loader<String> loader) {


    }

    private static class FetchData extends AsyncTaskLoader<String> {

        public FetchData(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = null;
            String line;
            try {
                URL url = new URL("https://itunes.apple.com/search?term=classic");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) buffer.append(line);

                if (buffer.length() == 0) return null;
                jsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("MainActivity", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }

            return jsonStr;
        }

        @Override
        public void deliverResult(String data) {
            super.deliverResult(data);
        }
    }
}
