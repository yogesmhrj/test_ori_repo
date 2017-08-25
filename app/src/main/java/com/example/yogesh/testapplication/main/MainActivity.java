package com.example.yogesh.testapplication.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.example.yogesh.testapplication.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            //this.webView.loadUrl("file:///android_asset/web/test.html");

            //first get the html data from the html file in the asset
            String htmlData = getHTMLDataBuffer("file:///android_asset/web/test.html");
            this.webView.loadDataWithBaseURL("file:///android_asset/web/", htmlData, "text/html", "UTF-8", null);

        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "onStart: "+e.getMessage());
        }


    }

    /**
     * Asset path to the file.
     *
     * @param path
     * @return
     */
    public String readAsset(String path) {
        String contents = "";
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = getAssets().open(path);
            reader = new BufferedReader(new InputStreamReader(is));
            contents = reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents += '\n' + line;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return contents;
    }

    private String getHTMLDataBuffer(String url) {
        InputStream htmlStream;
        try {

            String tempPath = url.replace("file:///android_asset/", "");
            htmlStream = getApplicationContext().getAssets().open(tempPath);

            Reader is = null;
            try {

                is = new BufferedReader(new InputStreamReader(htmlStream, "UTF8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // read string from reader
            final char[] buffer = new char[1024];
            StringBuilder out = new StringBuilder();
            int read;
            do {
                read = is.read(buffer, 0, buffer.length);
                if (read>0) {
                    out.append(buffer, 0, read);
                }
            } while (read>=0);

            return out.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
