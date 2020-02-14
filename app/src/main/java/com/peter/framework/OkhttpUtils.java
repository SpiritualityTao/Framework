package com.peter.framework;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class OkhttpUtils {

    private static final String TAG = "OkhttpUtils";

    //step 1
    private final OkHttpClient client = new OkHttpClient();

    public void okhttpSyncGet() throws IOException {
        //step 2
        Request request = new Request.Builder()
                .url("http://publicproject.com/helloworld.txt")
                .build();
        //step 3
        Response response = client.newCall(request).execute();

        if(!response.isSuccessful())
            throw new IOException("Unexpected code" + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            Log.d(TAG, responseHeaders.name(i) + ":" + responseHeaders.value(i));
        }

        Log.d(TAG, response.body().string());
    }

    public void okhttpAsyGet() throws IOException {
        //step 2
        Request request = new Request.Builder()
                .url("http://publicproject.com/helloworld.txt")
                .build();
        //step 3
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(!response.isSuccessful())
                    throw new IOException("Unexpected code" + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.d(TAG, responseHeaders.name(i) + ":" + responseHeaders.value(i));
                }

                Log.d(TAG, response.body().string());
            }
        });
    }
}
