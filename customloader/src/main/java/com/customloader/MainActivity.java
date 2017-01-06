package com.customloader;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    LoaderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter= new LoaderAdapter(this);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        mAdapter.swapData(Arrays.asList(getResources().getStringArray(R.array.media_names)));
    }

    private LoaderManager.LoaderCallbacks<List<String>> loaderCallBack = new LoaderManager.LoaderCallbacks<List <String>>(){

        @Override
        public Loader<List<String>> onCreateLoader(int id, Bundle args) {
            return new StringLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
            mAdapter.swapData(data);

        }

        @Override
        public void onLoaderReset(Loader<List<String>> loader) {
           // mAdapter.swapData(Collection.<String>);
        }
    };
}
