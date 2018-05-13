package com.example.venson.soho.Member;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.venson.soho.Common;
import com.example.venson.soho.LoginRegist.CommonTask;
import com.example.venson.soho.R;
import com.example.venson.soho.UserCapacity;
import com.example.venson.soho.UserExp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class EditMemberFragment extends Fragment {
    private String TAG = "EditMemberFragment";
    private ImageButton btSumbitClick;
    private EditText etName, etCompany, etWorkExperience, etExpertise, etLive, etPhone, etLine;
    private CommonTask updateTask, findUserTask, findCompanyTask, findUserCapacityTask;
    private boolean gender;
    private ImageView ivUser;
    private int userCompanyId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.person_layout_edit, container, false);
        findviews(view);


        btSumbitClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getActivity().getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
                int userId = pref.getInt("user_id", -1);
                Log.d(TAG, String.valueOf(userId));
                String name = etName.getText().toString().trim();
                String line = etLine.getText().toString().trim();
                String expertise = etExpertise.getText().toString().trim();
                String live = etLive.getText().toString().trim();
                String company = etCompany.getText().toString().trim();
                //更新公司名稱
                UpdateUserCompanyName(company);

                User user = new User(userId, name, line, expertise, gender, live);
                Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "update");
                jsonObject.addProperty("user", gson.toJson(user));
                String jsonOut = jsonObject.toString();
                Log.d(TAG, "update:" + jsonOut);
                String url = Common.URL + "/Login_RegistServlet";
                updateTask = new CommonTask(url, jsonOut);
                try {
                    String result = updateTask.execute().get();
                    Log.d(TAG, "update result:" + result);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }


                Fragment fragment = new MemberFragment();
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

    private void findviews(View view) {

        etName = view.findViewById(R.id.etEditName);
        etCompany = view.findViewById(R.id.etEditCompany);
        etWorkExperience = view.findViewById(R.id.etEditWorkExperience);
        etExpertise = view.findViewById(R.id.etEditExpertise);
        etLine = view.findViewById(R.id.etEditLine);
        etPhone = view.findViewById(R.id.etEditPhone);
        etLive = view.findViewById(R.id.etEditLive);
        btSumbitClick = view.findViewById(R.id.btEditComplete);
        ivUser = view.findViewById(R.id.ivEditUser);
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
            findUserTask = new CommonTask(url, jsonOut);
            User user = null;
            try {
                String result = findUserTask.execute().get();
                user = gson.fromJson(result, User.class);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            UserCompany userCompany = getUserCompanyName(userId);
            String companyName = userCompany.getName();
            Log.d(TAG, companyName);

            String capacity = findUserCapacitiesById(userId);
            Log.d(TAG, capacity);

            String exp = getUserExp(userId);

            etName.setText(user.getUserName());
            etLine.setText(user.getUserLINE());
            etExpertise.setText(capacity);
            etLive.setText(user.getUserlive());
            etWorkExperience.setText(exp);
            etCompany.setText(companyName);


        } else {
            Common.showToast(getActivity(), "沒有連線");

        }

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
        userCompanyId=userCompany.getUserCompanyId();

        return userCompany;

    }

    private void UpdateUserCompanyName(String companyName) {
        int companyId = 3;
        UserCompany userCompany = new UserCompany();
        userCompany.setCompanyId(companyId);
        userCompany.setUserCompanyId(userCompanyId);
        SharedPreferences pref = getActivity().getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        int userId = pref.getInt("user_id", -1);
        userCompany.setUserId(userId);

        Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
        JsonObject jsonOut = new JsonObject();
        jsonOut.addProperty("action", "updateCompany");
        jsonOut.addProperty("userCompany", gson.toJson(userCompany));
        String out = jsonOut.toString();
        Log.e(TAG, "update:" + jsonOut);
        String url = Common.URL + "/Login_RegistServlet";
        updateTask = new CommonTask(url, out);
        try {
            String result = updateTask.execute().get();
            Log.d(TAG, "updatecompany :" + result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }


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

            userCapacityName = userCapacityName + String.valueOf(userCapacity.getC());
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
}
