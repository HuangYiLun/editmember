package com.example.venson.soho;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.venson.soho.Home.HomeFragment;
import com.example.venson.soho.LoginRegist.CommonTask;

import com.example.venson.soho.Member.MemberFragment;
import com.example.venson.soho.Member.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.concurrent.ExecutionException;

public class RegistFragment extends Fragment {
    private final static String TAG = "RegistFragment";
    private EditText etEmail, etPassword, etConfirmPassword, etName;
    private Button btRegister;
    private  int checkUserId =0;
    private int userId;
    boolean isInputValid = true;
    private RadioGroup rgSex;
    private boolean gender = true;
    private CommonTask emailCheckTask, registerTask;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_layout, container, false);
        etEmail = view.findViewById(R.id.etEmail);
        etEmail.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassWord);
        etName = view.findViewById(R.id.etName);
        btRegister = view.findViewById(R.id.btRegister);
        rgSex = view.findViewById(R.id.rgSex);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbMen) {
                    gender = true;
                    Log.d(TAG, "gender=1");
                } else if (checkedId == R.id.rbWomen) {
                    gender = false;
                    Log.d(TAG, "gender=0");
                }

            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = etEmail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    String url = Common.URL + "/Login_RegistServlet";
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "login");
                    jsonObject.addProperty("email", email);
                    jsonObject.addProperty("password", password);
                    String jsonOut = jsonObject.toString();
                    emailCheckTask = new CommonTask(url, jsonOut);
                    Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
                    emailCheckTask = new CommonTask(url, jsonOut);
                    User user =null;
                    checkUserId=0;
                    try {
                         String result = emailCheckTask.execute().get();
                         user = gson.fromJson(result, User.class);
                         checkUserId = user.getUserId();
                        Log.d(TAG,"checkuserexist"+String.valueOf(checkUserId));
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }

                    if (checkUserId>0) {
                        etEmail.setError("email or password is exist");
                        etPassword.setError("email or password is exist");
                    } else {

                        isInputValid = true;
                    }
                }

            }
        });


        //////註冊按鈕
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String name = etName.getText().toString().trim();


                if (!isVaildEmailFormat(email)) {
                    etEmail.setError("invaild email address");
                    isInputValid = false;
                } else {
                    isInputValid = true;
                }
                if (checkUserId>0) {
                    isInputValid = false;
                } else {
                    isInputValid = true;
                }

                if (email.isEmpty()) {

                    isInputValid = false;
                }
                if (password.isEmpty()) {

                    isInputValid = false;
                }
                if (!confirmPassword.equals(password)) {

                    isInputValid = false;
                }
                if (name.isEmpty()) {

                    isInputValid = false;
                }

                if (isInputValid) {
                    String url = Common.URL + "Login_RegistServlet";
                    User user = new User(email, password, name, gender);
                    Gson gson = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "register");
                    jsonObject.addProperty("user", gson.toJson(user));
                    String jsonOut = jsonObject.toString();
                    int count = 0;
                    try {
                        registerTask = new CommonTask(url, jsonOut);
                        count = Integer.valueOf(registerTask.execute().get());
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (count == 0) {
                        Common.showToast(getActivity(), "fail create");
                        Log.d(TAG, "fail create");
                    } else {
                        /////取得id
                        JsonObject json = new JsonObject();
                        json.addProperty("action", "login");
                        jsonObject.addProperty("email", email);
                        jsonObject.addProperty("password", password);
                        String jsOut = jsonObject.toString();
                        emailCheckTask = new CommonTask(url, jsOut);
                        Gson gs = new GsonBuilder().setDateFormat("yyy_MM_dd").create();
                        emailCheckTask = new CommonTask(url, jsonOut);
                        User getUser =null;

                        try {
                            String result = emailCheckTask.execute().get();
                            getUser = gson.fromJson(result, User.class);

                            userId = getUser.getUserId();
                            Log.d(TAG,String.valueOf(userId));
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        ///存id
                        SharedPreferences preferences = getActivity().getSharedPreferences(
                                Common.PREF_FILE, Context.MODE_PRIVATE);
                        preferences.edit().putBoolean("login", true)
                                .putString("email", email)
                                .putString("password", password)
                                .putInt("user_id",userId).apply();
                        Fragment fragment = new MemberFragment();
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content, fragment).commit();
                    }
                }
            }
        });


        return view;
    }

    private boolean isVaildEmailFormat(String email) {

        if (email == null) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches();
    }


}
