package com.example.venson.soho.Member;

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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.venson.soho.Common;
import com.example.venson.soho.LoginRegist.CommonTask;
import com.example.venson.soho.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import static android.content.Context.MODE_PRIVATE;


public class MemberFragment extends Fragment {
    private String TAG = "MemberFragment";
    private Button btGallery, btTrack, btEvaluation, btEdit;
    private TextView etName, etCompany, etWorkExperience, etExpertise, etLive, etPhone, etLine;
    private RadioGroup rgGender;
    private CommonTask findUserTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.person_layout, container, false);
        findviews(view);
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
            findUserTask = new CommonTask(url, jsonOut);
            User user = null;
            try {
                String result = findUserTask.execute().get();
                user = gson.fromJson(result, User.class);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            etName.setText(user.getUserName());
            etLine.setText("Line "+user.getUserLINE());
            etExpertise.setText("專長 "+user.getUserSelfDes());


        } else {
            Common.showToast(getActivity(), "沒有連線");

        }

    }

    private void findviews(View view) {
        btTrack = view.findViewById(R.id.btTrack);
        btEvaluation = view.findViewById(R.id.btEvaluation);
        etName = view.findViewById(R.id.eTName);
        etWorkExperience = view.findViewById(R.id.etWorkExperience);
        etExpertise = view.findViewById(R.id.etExpertise);
        etLine = view.findViewById(R.id.etLine);
        etPhone = view.findViewById(R.id.etPhone);
        etLive = view.findViewById(R.id.etLive);
        rgGender = view.findViewById(R.id.rgGender);
        btGallery = view.findViewById(R.id.btGallery);
        btEdit = view.findViewById(R.id.btEdit);
    }

}
