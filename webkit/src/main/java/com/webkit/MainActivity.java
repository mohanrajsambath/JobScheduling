package com.webkit;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView wV_odoo;
    final Context c = this;
    //String myUrl="https://www.facebook.com";
    String myUrl="https://raj001.odoo.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wV_odoo=(WebView)findViewById(R.id.wV_odoo);
        wV_odoo.getSettings().setJavaScriptEnabled(true);
        loadUrl(myUrl);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            customInputDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadUrl(String url) {
        wV_odoo.getSettings().setJavaScriptEnabled(true);
        wV_odoo.getSettings().setBuiltInZoomControls(true);
        wV_odoo.getSettings().setUseWideViewPort(true);
        wV_odoo.getSettings().setLoadWithOverviewMode(true);
        wV_odoo.getSettings().setDomStorageEnabled(true);
        wV_odoo.setVisibility(View.VISIBLE);
        wV_odoo.loadUrl(url);
        wV_odoo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    void customInputDialog(){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        Toast.makeText(MainActivity.this, "New Url!", Toast.LENGTH_SHORT).show();
                        String newUrl=userInputDialogEditText.getText().toString().trim();
                        if(newUrl.contains("https")) {
                            System.out.println("New Url---=>"+newUrl);
                            loadUrl(newUrl);
                        }else{
                            userInputDialogEditText.setText("");
                            userInputDialogEditText.setError("Enter Valid link");

                        }
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }
}
