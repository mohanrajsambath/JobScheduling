package com.viewpagerbckservc;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * Created by Mohanraj.S on 05/01/17.
 */

public class DownloadService extends Service {

    private Intent intent;
    private LocalBroadcastManager broadcastManager;

    /*private final static String DOWNLOAD_LINK = "https://itunes.apple.com/search?term=classic";
    private final static String filePath = "/sdcard/MyFiles/downloadedfile.flac";*/

    private final static String DOWNLOAD_LINK = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";

    public final static String DOWNLOAD_ACTION = "com.viewpagerbckservc";
    public final static String PERCENTAGE_STAMP = "percent";
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    Random random ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        random = new Random();

        //init Intent
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new DownloadFileFromURLTask().execute(DOWNLOAD_LINK);
        return START_STICKY;
    }

    private class DownloadFileFromURLTask extends AsyncTask<String, String, String> {
        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... downloadUrl) {
            int count;
            try {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "file.3gp";
                URL url = new URL(downloadUrl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // getting file length
                int lenghtOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream(filePath);

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //put info through intent
            intent = new Intent(DOWNLOAD_ACTION);
            intent.putExtra(PERCENTAGE_STAMP, progress[0]);
            //sending intent through BroadcastManager
            broadcastManager.sendBroadcast(intent);
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        @Override
        protected void onPostExecute(String downloadUrl) {
            Toast.makeText(getApplicationContext(), "Download successful!", Toast.LENGTH_SHORT).show();
        }
    }

    public String CreateRandomAudioFileName(int string){

        StringBuilder stringBuilder = new StringBuilder( string );

        int i = 0 ;
        while(i < string ) {

            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();

    }
}