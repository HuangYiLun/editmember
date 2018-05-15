package com.example.venson.soho.Message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.venson.soho.Common;
import com.example.venson.soho.LoginRegist.CommonTask;
import com.example.venson.soho.LoginRegist.MemberGetImageTask;
import com.example.venson.soho.MainActivity;
import com.example.venson.soho.Member.User;
import com.example.venson.soho.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ricky on 2018/4/21.
 */

public class MessageFragment extends Fragment {
    private static String TAG = "MessageFragment";
    private RecyclerView rvFriends;
    private LocalBroadcastManager localBroadcastManager;
    private CommonTask findUserTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();


        View view = inflater.inflate(R.layout.message_fragment, container, false);

        localBroadcastManager = LocalBroadcastManager.getInstance(activity);
        registerFriendStateReceiver();
        rvFriends = view.findViewById(R.id.recyclerView);
        rvFriends.setLayoutManager(new LinearLayoutManager(activity));
        rvFriends.setAdapter(new FriendAdapter(activity));
        String ID = getUserID();
        Common.connectServer(ID, activity);


        return view;
    }

    private void registerFriendStateReceiver() {
        MainActivity activity = (MainActivity) getActivity();
        IntentFilter openFilter = new IntentFilter("open");
        IntentFilter closeFilter = new IntentFilter("close");
        FriendStateReceiver friendStateReceiver = new FriendStateReceiver(activity);
        localBroadcastManager.registerReceiver(friendStateReceiver, openFilter);
        localBroadcastManager.registerReceiver(friendStateReceiver, closeFilter);
    }


    private class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
        MainActivity activity;

        FriendAdapter(MainActivity activity) {
            this.activity = activity;
        }

        class FriendViewHolder extends RecyclerView.ViewHolder {
            TextView tvUserName;
            ImageView ivUserProfile;

            FriendViewHolder(View itemView) {
                super(itemView);
                tvUserName = itemView.findViewById(R.id.tvChatUserListName);
                ivUserProfile = itemView.findViewById(R.id.chatUserListProfile);

            }
        }

        @Override
        public int getItemCount() {
            return Common.getFriendList().size();
        }

        @Override
        public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            View itemView = layoutInflater.inflate(R.layout.chat_userlist_item, parent, false);
            return new FriendViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FriendViewHolder holder, int position) {
            final String friend_ID = Common.getFriendList().get(position);
            int friendID = Integer.valueOf(friend_ID);
            Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();

            String url = Common.URL + "/Login_RegistServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "findUserById");
            jsonObject.addProperty("userId", friendID);
            String jsonOut = jsonObject.toString();
            findUserTask = new CommonTask(url, jsonOut);
            User user = null;
            try {
                String result = findUserTask.execute().get();
                user = gson.fromJson(result, User.class);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            String picPath =user.getUserPicPath();
            Bitmap bitmap = null;

            MemberGetImageTask memberGetImageTask = new MemberGetImageTask(url,picPath);
            try {
                bitmap = memberGetImageTask.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                holder.ivUserProfile.setImageBitmap(bitmap);
            } else {
                holder.ivUserProfile.setImageResource(R.drawable.dog);
            }



            holder.tvUserName.setText(user.getUserName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("friend_ID", friend_ID);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });

        }


    }

    ////////////////加入朋友列表
    private class FriendStateReceiver extends BroadcastReceiver {
        MainActivity activity;

        FriendStateReceiver(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            StateMessage stateMessage = new Gson().fromJson(message, StateMessage.class);
            String type = stateMessage.getType();
            String friend_ID = stateMessage.getUserID();
            Log.d(TAG, "FRIEND_ID" + friend_ID);
            String userID = getUserID();
            Log.d(TAG, "userID" + userID);
            switch (type) {
                case "open":
                    List<String> friendlist = new ArrayList<>(stateMessage.getUserIDs());
                    Log.d(TAG, "getfriendlist");
                    friendlist.remove(userID);
                    Log.d(TAG, "remove userID");
                    Common.setFriendList(friendlist);
                    Log.d(TAG, "setfriendlist");

                    rvFriends.getAdapter().notifyDataSetChanged();
//                    if (friend_em.equals(userEM)) {
//
//                        List<String> friendList = new ArrayList<>(stateMessage.getUserEmails());
//                        Common.setFriendList(friendList);
//
//                        friendList.remove(userEM);
//                    } else {
//
//                        if (!Common.getFriendList().contains(friend_em)) {
//                            Common.getFriendList().add(friend_em);
//                        }
//                    }
                    break;


                case "close":
                    Common.getFriendList().remove(friend_ID);
                    rvFriends.getAdapter().notifyDataSetChanged();
                    Common.showToast(activity, friend_ID + " is offline");
                    break;
            }
            Log.d(TAG, "message: " + message);
            Log.d(TAG, "friendList: " + Common.getFriendList());
        }
    }

    public String getUserID() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        int userID = sharedPreferences.getInt("user_id", -1);
        Log.d(TAG, "userID:" + String.valueOf(userID));
        return String.valueOf(userID);
    }
}