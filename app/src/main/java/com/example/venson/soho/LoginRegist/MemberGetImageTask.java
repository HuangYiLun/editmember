package com.example.venson.soho.LoginRegist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.venson.soho.R;

public class MemberGetImageTask extends AsyncTask<Object, Integer, Bitmap> {
    private final static String TAG = "SpotGetImageTask";
    private String url,path;
//    private int imageSize;
    // WeakReference物件不會阻止參照到的實體被回收
    private WeakReference<ImageView> imageViewWeakReference;//recyclerview會重複利用

    public MemberGetImageTask(String url, String path) {
        this(url, path, null);
    }

   public MemberGetImageTask(String url, String path, ImageView imageView) {
        this.url = url;
        this.path = path;

        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getImgByPath");
        jsonObject.addProperty("path", path);
//        jsonObject.addProperty("imageSize", imageSize);
        return getRemoteImage(url, jsonObject.toString());
    }

    @Override// 跟ui有關

    protected void onPostExecute(Bitmap bitmap) {
        ImageView imageView = imageViewWeakReference.get();
        if (isCancelled() || imageView == null) {
            return;
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.dog);
        }
    }

    private Bitmap getRemoteImage(String url, String jsonOut) {
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true); // allow inputs
            connection.setDoOutput(true); // allow outputs
            connection.setUseCaches(false); // do not use a cached copy
            connection.setRequestMethod("POST");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bw.write(jsonOut);
            Log.d(TAG, "output: " + jsonOut);
            bw.close();


            //圖 轉成json要花比較多時間
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                bitmap = BitmapFactory.decodeStream(
                        new BufferedInputStream(connection.getInputStream()));
            } else {
                Log.d(TAG, "response code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return bitmap;
    }
}