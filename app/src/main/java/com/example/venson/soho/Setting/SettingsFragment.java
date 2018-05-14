package com.example.venson.soho.Setting;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.venson.soho.BottomNavigation;
import com.example.venson.soho.Common;
import com.example.venson.soho.LoginFragment;
import com.example.venson.soho.MainActivity;
import com.example.venson.soho.Message.ChatActivity;
import com.example.venson.soho.Message.ChatMessage;
import com.example.venson.soho.Message.MessageFragment;
import com.example.venson.soho.Message.StateMessage;
import com.example.venson.soho.R;
import com.google.gson.Gson;

/**
 * Created by ricky on 2018/4/21.
 */

public class SettingsFragment extends Fragment {
    private String TAG = "SettingsFragment";
    private Button btSetLogout, btSetLogin;
    private Switch swApplicationNO, swMessageNo;
    private LocalBroadcastManager localBroadcastManager;
    private Gson gson;
    private Context context;
    public static String friendInChatID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        btSetLogout = view.findViewById(R.id.btSetLogout);
        btSetLogin = view.findViewById(R.id.btSetLogin);
        swApplicationNO = view.findViewById(R.id.swApplicationNo);
        swMessageNo = view.findViewById(R.id.swMessageNo);

        MainActivity activity = (MainActivity) getActivity();
        localBroadcastManager = LocalBroadcastManager.getInstance(activity);


        btSetLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences(Common.PREF_FILE,
                        Context.MODE_PRIVATE);
                pref.edit().putBoolean("login", false).putString("email", "")
                        .putString("password", "").apply();
                Common.disconnectServer();
                Fragment fragment = new BottomNavigation();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        btSetLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
            }
        });

        swMessageNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                chatStateReceiver();
            }
        });


        swApplicationNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


        return view;
    }


    private void chatStateReceiver() {
        MainActivity activity = (MainActivity) getActivity();
        IntentFilter chatFilter = new IntentFilter("chat");
        ChatStateReceiver chatStateReceiver = new ChatStateReceiver(activity);
        localBroadcastManager.registerReceiver(chatStateReceiver, chatFilter);

    }


    private class ChatStateReceiver extends BroadcastReceiver {
        MainActivity activity;

        public ChatStateReceiver(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra("message");
            StateMessage stateMessage = gson.fromJson(message, StateMessage.class);
            String type = stateMessage.getType();
            if (type.equals("chat")) {
                ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
                String text = "sender_ID:" + chatMessage.getSender_ID() +
                        "\nfriendInChat: " + friendInChatID;
                Log.d(TAG, text);
                if (friendInChatID == null || !friendInChatID.equals(chatMessage.getSender_ID())) {
                    showNotification(chatMessage);

                }
            }

        }
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
