package com.example.venson.soho.Message;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Locale;

public class ChatWebSocketClient extends WebSocketClient {
    private static final String TAG = "ChatWebSocketClient";
    private Gson gson;
    private Context context;
    public static String friendInChatID;

    public ChatWebSocketClient(URI serverURI, Context context) {
        super(serverURI, new Draft_17());
        this.context = context;
        gson = new Gson();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        String text = String.format(Locale.getDefault(),
                "onOpen:Http status code = %d; status message = %s",
                handshakedata.getHttpStatus(),
                handshakedata.getHttpStatusMessage());
        Log.d(TAG, "onOpen:" + text);
    }

    @Override
    public void onMessage(String message) {

        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String type = jsonObject.get("type").getAsString();

        if (type.equals("chat")) {
            ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
            String text = "sender_ID:" + chatMessage.getSender_ID() +
                    "\nfriendInChat: " + friendInChatID;
            Log.d(TAG, text);
            if (friendInChatID == null || !friendInChatID.equals(chatMessage.getSender_ID())) {
                showNotification(chatMessage);
                return;
            }
        }
        Log.d(TAG, "onMessage:" + message);
        sendMessageBroadcast(type, message);

    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        int length = bytes.array().length;
        String message = new String(bytes.array());
        Log.d(TAG, "onMessage(ByteBuffer): length = " + length);
        onMessage(message);
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
        String text = String.format(Locale.getDefault(),
                "code = %d, reason = %s, remote = %b",
                code, reason, remote);
        Log.d(TAG, "onClose: " + text);
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "onError: exception = " + ex.toString());
    }

    private void sendMessageBroadcast(String messageType, String message) {
        Intent intent = new Intent(messageType);
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void showNotification(ChatMessage chatMessage) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("friend_ID", chatMessage.getSender_ID());
        bundle.putString("messageType", chatMessage.getMessageType());
        bundle.putString("messageContent", chatMessage.getContent());
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setContentTitle("message from" + chatMessage.getSender_ID())
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, notification);
        }

    }


}
