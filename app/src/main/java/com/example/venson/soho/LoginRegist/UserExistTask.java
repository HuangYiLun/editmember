package com.example.venson.soho.LoginRegist;

import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import java.net.URL;

public class UserExistTask extends AsyncTask<Object, Integer, Boolean> {
    private final static String TAG = "UserExistTask";
    private final static String ACTION = "login";

    @Override
    protected Boolean doInBackground(Object... params) {

        String url = params[0].toString();
        String email = params[1].toString();
        String password = params[2].toString();
        Boolean result;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        try {
            result = Boolean.valueOf(getRemoteData(url, jsonObject.toString()));
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }


        return result;
    }

    private String getRemoteData(String url, String jsonOut) throws IOException {
        StringBuilder jsonIn = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        bw.write(jsonOut);
        Log.d(TAG, "jsonOut: " + jsonOut);
        bw.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                jsonIn.append(line);
            }
        } else {
            Log.d(TAG, "response code: " + responseCode);
        }
        connection.disconnect();
        Log.d(TAG, "jsonIn: " + jsonIn);
        return jsonIn.toString();
    }


}

