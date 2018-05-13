package com.example.venson.soho;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.venson.soho.Message.ChatWebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Common {

    private static final String TAG = "Common";

    // check if the device connect to the network
    public static String URL = "http://10.0.2.2:8080/Test_Project_User/";

    public final static String SERVER_URI = "ws://10.0.2.2:8080/Test_Project_User/ChatServlet/";
    public final static String PREF_FILE = "preference";

    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static List<String> friendlist = new ArrayList<>();

    public static List<String> getFriendList() {
        return friendlist;
    }

    public static void setFriendList(List<String> friendList) {
        Common.friendlist = friendList;
    }

    public static ChatWebSocketClient chatWebSocketClient;

    // 建立WebSocket連線
    public static void connectServer(String userID, Context context) {
        if (chatWebSocketClient == null) {
            URI uri = null;
            try {
                uri = new URI(SERVER_URI + userID);
            } catch (URISyntaxException e) {
                Log.e(TAG, e.toString());
            }
            chatWebSocketClient = new ChatWebSocketClient(uri, context);
            chatWebSocketClient.connect();
        }
    }


    // 中斷WebSocket連線
    public static void disconnectServer() {
        if (chatWebSocketClient != null) {
            chatWebSocketClient.close();
            chatWebSocketClient = null;
        }
        friendlist.clear();
    }


    public static Bitmap downSize(Bitmap srcBitmap, int newSize) {
        if (newSize <= 50) {
            // 如果欲縮小的尺寸過小，就直接定為128
            newSize = 128;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        String text = "source image size = " + srcWidth + "x" + srcHeight;
        Log.d(TAG, text);
        int longer = Math.max(srcWidth, srcHeight);

        if (longer > newSize) {
            double scale = longer / (double) newSize;
            int dstWidth = (int) (srcWidth / scale);
            int dstHeight = (int) (srcHeight / scale);
            srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, false);
            System.gc();
            text = "\nscale = " + scale + "\nscaled image size = " +
                    srcBitmap.getWidth() + "x" + srcBitmap.getHeight();
            Log.d(TAG, text);
        }
        return srcBitmap;
    }

}
