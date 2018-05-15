package com.example.venson.soho.Member;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.venson.soho.Category;
import com.example.venson.soho.Common;
import com.example.venson.soho.LoginRegist.CommonTask;
import com.example.venson.soho.LoginRegist.MemberGetImageTask;
import com.example.venson.soho.MainActivity;
import com.example.venson.soho.R;
import com.example.venson.soho.UserCapacity;
import com.example.venson.soho.UserExp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class MemberFragment extends Fragment {
    private String TAG = "MemberFragment";
    private Button btGallery, btTrack, btEvaluation;
    private ImageButton btEdit;
    private TextView etName, etCompany, etWorkExperience, etExpertise, etLive, etPhone, etLine;
    private CommonTask findCompanyTask, findUserCapacityTask;
    private ImageView ivMemberView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.person_layout, container, false);
        findviews(view);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        String ID = String.valueOf(sharedPreferences.getInt("user_id", -1));
        MainActivity activity = (MainActivity) getActivity();
        Common.connectServer(ID, activity);


        btGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MemberAlbumFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditMemberFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        fillProfile();
    }

    private void fillProfile() {
        SharedPreferences pref = getActivity().getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        int userId = pref.getInt("user_id", -1);
        Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();

        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/Login_RegistServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "findUserById");
            jsonObject.addProperty("userId", userId);
            String jsonOut = jsonObject.toString();
            findUserCapacityTask = new CommonTask(url, jsonOut);
            User user = null;
            try {
                String result = findUserCapacityTask.execute().get();
                user = gson.fromJson(result, User.class);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            //取得公司名稱
            UserCompany userCompany = getUserCompanyName(userId);
            String companyName = "";

            if (userCompany.getName() != null) {
                companyName = userCompany.getName();
            } else {
                companyName = "尚未登記公司";
            }
            Log.d(TAG, companyName);

            //取得工作類別
            String capacity = "";
            if (findUserCapacitiesById(userId) != null) {
                capacity = findUserCapacitiesById(userId);
            } else {
                capacity = "尚未選擇工作類別";
            }
            Log.d(TAG, capacity);

            //取得工作經歷
            String exp = "";
            if (getUserExp(userId) != null) {
                exp = getUserExp(userId);
            } else {
                exp = "尚未新增工作經歷";
            }

            //取得手機號碼
            String phoneNumber = "";
            if (findUserPhoneNumber(userId) != null) {
                phoneNumber = findUserPhoneNumber(userId);
            } else {
                phoneNumber = "尚未新增工作經歷";
            }

            //   5/14  抓圖片
            String picPath = pref.getString("userPicPath", "");
            Bitmap bitmap = null;

            MemberGetImageTask memberGetImageTask = new MemberGetImageTask(url, picPath);
            try {
                bitmap = memberGetImageTask.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                ivMemberView.setImageBitmap(bitmap);
            } else {
                ivMemberView.setImageResource(R.drawable.dog);
            }

            etName.setText(user.getUserName());
            etLine.setText(user.getUserLINE());
            etExpertise.setText(capacity);
            etCompany.setText(companyName);
            etLive.setText(user.getUserlive());
            etWorkExperience.setText(exp);
            etPhone.setText(phoneNumber);


        } else {
            Common.showToast(getActivity(), "沒有連線");

        }

    }


    private void findviews(View view) {
        btTrack = view.findViewById(R.id.btMemberTrack);
        btEvaluation = view.findViewById(R.id.btMemberEvaluation);
        etName = view.findViewById(R.id.etMemberName);
        etWorkExperience = view.findViewById(R.id.etMemberWorkExperience);
        etExpertise = view.findViewById(R.id.etMemberExpertise);
        etLine = view.findViewById(R.id.etMemberLine);
        etPhone = view.findViewById(R.id.etMemberPhone);
        etLive = view.findViewById(R.id.etMemberLive);
        btGallery = view.findViewById(R.id.btMemberGallery);
        btEdit = view.findViewById(R.id.btMemberEdit);
        etCompany = view.findViewById(R.id.etMemberCompany);
        ivMemberView = view.findViewById(R.id.ivMemberUser);

    }

    private UserCompany getUserCompanyName(int userId) {

        Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
        String url = Common.URL + "/Login_RegistServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getCompanyById");
        jsonObject.addProperty("userId", userId);
        String jsonOut = jsonObject.toString();
        findCompanyTask = new CommonTask(url, jsonOut);
        UserCompany userCompany = null;
        try {
            String result = findCompanyTask.execute().get();
            userCompany = gson.fromJson(result, UserCompany.class);
            Log.d(TAG, result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return userCompany;

    }

    private String findUserCapacitiesById(int userId) {
        String userCapacityName = "";
        Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
        String url = Common.URL + "/Login_RegistServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "findCapacitiesByUId");
        jsonObject.addProperty("userId", userId);
        String jsonOut = jsonObject.toString();
        findCompanyTask = new CommonTask(url, jsonOut);
        List<UserCapacity> capacities = new ArrayList<>();

        Type listType = new TypeToken<ArrayList<UserCapacity>>() {
        }.getType();
        try {
            String result = findCompanyTask.execute().get();

            capacities = gson.fromJson(result, listType);
            Log.d(TAG, "capacities" + String.valueOf(capacities));

            Log.d(TAG, "result" + result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        for (UserCapacity userCapacity : capacities) {

            userCapacityName = userCapacity.getC().getName();
        }
        Log.d(TAG, "name" + userCapacityName);
        return userCapacityName;

    }

    private String getUserExp(int userId) {

        Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
        String url = Common.URL + "/Login_RegistServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "findExpsByUId");
        jsonObject.addProperty("userId", userId);
        String jsonOut = jsonObject.toString();
        findCompanyTask = new CommonTask(url, jsonOut);
        List<UserExp> userExps = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<UserExp>>() {
        }.getType();

        try {
            String result = findCompanyTask.execute().get();
            userExps = gson.fromJson(result, listType);
            Log.d(TAG, result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        String exp = "";

        for (UserExp userExp : userExps) {
            exp = exp + userExp.getExpDes();

        }
        Log.d(TAG, "exp " + exp);

        return exp;

    }

    private String findUserPhoneNumber(int userId) {
        String phoneNumber = "";
        Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
        String url = Common.URL + "/Login_RegistServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "findUserPhone");
        jsonObject.addProperty("userId", userId);
        String jsonOut = jsonObject.toString();
        CommonTask findPhoneTask = new CommonTask(url, jsonOut);

        try {
            phoneNumber = findPhoneTask.execute().get();

            Log.d(TAG, "result" + phoneNumber);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return phoneNumber;

    }


}
