package com.example.venson.soho.Message;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.venson.soho.Common;
import com.example.venson.soho.R;
import com.google.gson.Gson;


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
;import static com.example.venson.soho.Common.chatWebSocketClient;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ChatActivity";
    private static final int REQ_TAKE_PICTURE = 0;
    private static final int REQ_PICK_IMAGE = 1;
    private static final int REQ_CROP_PICTURE = 2;
    private static final int REQ_PERMISSIONS_STORAGE = 101;
    private LocalBroadcastManager broadcastManager;
    private ScrollView scrollView;
    private ImageView ivCamera, ivPicture, ivSend;
    private LinearLayout linearLayout;
    private EditText etMessage;
    private String friend_em;

    private Uri contentUri, croppedImageUri;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);
        findviews();

        broadcastManager = LocalBroadcastManager.getInstance(this);
        registerChatReceiver();

        friend_em = getIntent().getStringExtra("friend_em");
        String messageType = getIntent().getStringExtra("messageType");
        if (messageType != null) {
            String messageContent = getIntent().getStringExtra("messageContent");
            switch (messageType) {
                case "text":
                    showMessage(friend_em, messageContent, true);
                    break;
                case "image":
                    byte[] image = Base64.decode(messageContent, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    showImage(friend_em, bitmap, true);
                    break;
                default:
                    break;
            }
        }
    }


    ////////設定聊天對象
    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions_Storage();
        ChatWebSocketClient.friendInChatEM = friend_em;
    }

    private void requestPermissions_Storage() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int result = ContextCompat.checkSelfPermission(this, permissions[0]);
        if (result != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQ_PERMISSIONS_STORAGE);
        }

    }

    private void findviews() {
        etMessage = findViewById(R.id.etMessage);
        linearLayout = findViewById(R.id.layout);
        scrollView = findViewById(R.id.chatScrollView);
        ivCamera = findViewById(R.id.ivCamera);
        ivPicture = findViewById(R.id.ivPicture);
        ivSend = findViewById(R.id.ivSend);
        ivCamera.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
        ivSend.setOnClickListener(this);
    }

    ///////按鈕設定
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivSend:
                String message = etMessage.getText().toString();
                if (message.trim().isEmpty()) {
                    Common.showToast(this, "message is empty");
                    return;
                }
                String sender_em = getUserEmail();
                showMessage(sender_em, message, false);
                etMessage.setText(null);

                ChatMessage chatMessage = new ChatMessage("chat", sender_em, friend_em, message, "text");
                String chatMessageJson = new Gson().toJson(chatMessage);
                chatWebSocketClient.send(chatMessageJson);
                Log.d(TAG, "output: " + chatMessageJson);
                break;


            case R.id.ivCamera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                file = new File(file, "picture.jpg");
                contentUri = FileProvider.getUriForFile(
                        this, getPackageName() + ".provider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                if (isIntentAvailable(this, intent)) {
                    startActivityForResult(intent, REQ_TAKE_PICTURE);
                } else {
                    Common.showToast(this, "No Camera App");
                }

                break;

            case R.id.ivPicture:
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_PICK_IMAGE);
                break;

            default:
                break;


        }

    }

    private boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    ///////訊息分左右邊
    private void showMessage(String sender_em, String message, boolean left) {

        View view;

        if (left) {
            view = View.inflate(this, R.layout.chat_message_left, null);
            TextView tvChatUserName = view.findViewById(R.id.tvChatUserName);
            tvChatUserName.setText(sender_em);
        } else {
            view = View.inflate(this, R.layout.chat_message_right, null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(android.icu.util.TimeZone.getTimeZone("Asia/Taipei"));
        Date dt = new Date();
        String dts = sdf.format(dt);
        TextView tvChatMessageT = view.findViewById(R.id.tvChatMessageT);
        tvChatMessageT.setText(dts);
        TextView tvMessage = view.findViewById(R.id.tvChatMessage);
        tvMessage.setText(message);
        linearLayout.addView(view);////?
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void showImage(String sender_em, Bitmap bitmap, boolean left) {

        View view;
        if (left) {
            view = View.inflate(this, R.layout.chat_image_left, null);
            TextView tvChatUserName = view.findViewById(R.id.tvChatUserName);
            tvChatUserName.setText(sender_em);
        } else {
            view = View.inflate(this, R.layout.chat_image_right, null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(android.icu.util.TimeZone.getTimeZone("Asia/Taipei"));
        Date dt = new Date();
        String dts = sdf.format(dt);
        TextView tvChatMessageT = view.findViewById(R.id.tvChatMessageT);
        tvChatMessageT.setText(dts);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        linearLayout.addView(view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void registerChatReceiver() {
        IntentFilter chatFilter = new IntentFilter("chat");
        ChatReceiver chatReceiver = new ChatReceiver();
        broadcastManager.registerReceiver(chatReceiver, chatFilter);

    }

    public String getUserEmail() {
        SharedPreferences preferences =
                getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        String userEmail = preferences.getString("email", "");
        Log.d(TAG, "userEmail = " + userEmail);
        return userEmail;
    }


    /////接收到訊息
    private class ChatReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            ChatMessage chatMessage = new Gson().fromJson(message, ChatMessage.class);

            String sender_em = chatMessage.getSender_em();
            String messageType = chatMessage.getMessageType();

            if (sender_em.equals(friend_em)) {
                switch (messageType) {

                    case "text":
                        showMessage(sender_em, chatMessage.getContent(), true);
                        break;
                    case "image":
                        byte[] image = Base64.decode(chatMessage.getContent(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        showImage(sender_em, bitmap, true);
                        break;
                    default:
                        break;
                }
            }
            Log.d(TAG, "receive message:" + message);

        }
    }

    ////以下純copy老師
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            int newSize = 400;
            switch (requestCode) {
                // 手機拍照App拍照完成後可以取得照片圖檔
                case REQ_TAKE_PICTURE:
                    Log.d(TAG, "REQ_TAKE_PICTURE: " + contentUri.toString());
                    crop(contentUri);
                    break;
                case REQ_PICK_IMAGE:
                    Uri uri = intent.getData();
                    crop(uri);
                    break;
                case REQ_CROP_PICTURE:
                    Log.d(TAG, "REQ_CROP_PICTURE: " + croppedImageUri.toString());
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(croppedImageUri));
                        Bitmap downsizedImage = Common.downSize(bitmap, newSize);
                        String sender_em = getUserEmail();
                        showImage(sender_em, downsizedImage, false);
                        // 將欲傳送的對話訊息轉成JSON後送出
                        String message = Base64.encodeToString(bitmapToPNG(downsizedImage), Base64.DEFAULT);
                        ChatMessage chatMessage = new ChatMessage("chat", sender_em, friend_em, message, "image");
                        String chatMessageJson = new Gson().toJson(chatMessage);
                        chatWebSocketClient.send(chatMessageJson);
                        Log.d(TAG, "output: " + chatMessageJson);
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
            }
        }
    }

    private void crop(Uri sourceImageUri) {
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        file = new File(file, "picture_cropped.jpg");
        croppedImageUri = Uri.fromFile(file);
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // the recipient of this Intent can read soruceImageUri's data
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // set image source Uri and type
            cropIntent.setDataAndType(sourceImageUri, "image/*");
            // send crop message
            cropIntent.putExtra("crop", "true");
            // aspect ratio of the cropped area, 0 means user define
            cropIntent.putExtra("aspectX", 0); // this sets the max width
            cropIntent.putExtra("aspectY", 0); // this sets the max height
            // output with and height, 0 keeps original size
            cropIntent.putExtra("outputX", 0);
            cropIntent.putExtra("outputY", 0);
            // whether keep original aspect ratio
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, croppedImageUri);
            // whether return data by the intent
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQ_CROP_PICTURE);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast.makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] bitmapToPNG(Bitmap srcBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 轉成PNG不會失真，所以quality參數值會被忽略
        srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ChatWebSocketClient.friendInChatEM = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ivCamera.setEnabled(true);
                    ivPicture.setEnabled(true);
                } else {
                    ivCamera.setEnabled(false);
                    ivPicture.setEnabled(false);
                }
                break;
            default:
                break;
        }
    }


}
