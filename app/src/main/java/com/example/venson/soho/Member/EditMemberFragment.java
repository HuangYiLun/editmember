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
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.venson.soho.Common;
import com.example.venson.soho.LoginRegist.CommonTask;
import com.example.venson.soho.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class EditMemberFragment extends Fragment{
        private String TAG ="EditMemberFragment";
        private Button btSumbitClick;
        private EditText etName, etCompany,etWorkExperience,etExpertise,etLive,etPhone,etLine;
        private RadioGroup rgGender;
        private CommonTask updateTask,findUserTask;
        private boolean gender;
        private ImageView ivUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.edit_person_layout, container, false);
        findviews(view);



        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbMen){
                    gender = true;
                    Log.d(TAG,"gender=1");
                }else if (checkedId == R.id.rbWomen){
                    gender = false;
                    Log.d(TAG,"gender=0");
                }
            }
        });

        btSumbitClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getActivity().getSharedPreferences(Common.PREF_FILE,MODE_PRIVATE);
                int userId = pref.getInt("user_id",-1);
                Log.d(TAG, String.valueOf(userId));
                String name = etName.getText().toString().trim();
                String  line = etLine.getText().toString().trim();
                String  expertise = etExpertise.getText().toString().trim();
                User user = new User(userId,name,line,expertise,gender);
                Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "update");
                jsonObject.addProperty("user", gson.toJson(user));
                String jsonOut = jsonObject.toString();
                String url = Common.URL + "/Login_RegistServlet";
                updateTask = new CommonTask(url,jsonOut);
                try {
                    String result = updateTask.execute().get();
                    Log.d(TAG,"update result:"+result);
                } catch (Exception e) {
                    Log.e(TAG,e.toString());
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

        etName = view.findViewById(R.id.eTName);
        etWorkExperience = view.findViewById(R.id.etWorkExperience);
        etExpertise = view.findViewById(R.id.etExpertise);
        etLine = view.findViewById(R.id.etLine);
        etPhone = view.findViewById(R.id.etPhone);
        etLive = view.findViewById(R.id.etLive);
        rgGender = view.findViewById(R.id.rgGender);
        btSumbitClick = view.findViewById(R.id.btComplete);
        ivUser = view.findViewById(R.id.iVUser);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillProfile();
    }

    private void fillProfile() {
        SharedPreferences pref = getActivity().getSharedPreferences(Common.PREF_FILE,MODE_PRIVATE);
        int userId = pref.getInt("user_id",-1);
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

                etName.setText(user.getUserName());
                etLine.setText(user.getUserLINE());
                etExpertise.setText(user.getUserSelfDes());




            }
         else {
            Common.showToast(getActivity(),"沒有連線");

        }

    }
}
