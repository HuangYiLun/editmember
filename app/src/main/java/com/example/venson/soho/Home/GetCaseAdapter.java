package com.example.venson.soho.Home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.venson.soho.CaseTag;
import com.example.venson.soho.Common;
import com.example.venson.soho.MyCase;
import com.example.venson.soho.MyTask;
import com.example.venson.soho.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GetCaseAdapter extends RecyclerView.Adapter<GetCaseAdapter.MyViewHolder> {
    private static final String TAG = "HomeCaseFragment";
    private MyTask caseTagTask;
    private LayoutInflater layoutInflater;
    static List<MyCase> myCases = new ArrayList<>();
    Context context;

        GetCaseAdapter(List<MyCase> myCases, Context context) {
            layoutInflater = LayoutInflater.from(context);
            this.myCases = myCases;
            this.context = context;
        }

    class MyViewHolder extends RecyclerView.ViewHolder {
                    TextView case_content,case_pay_min,case_date,case_skill,case_location,case_name_id,case_pay_max;
            MyViewHolder(View itemView) {
                super(itemView);
                case_name_id = itemView.findViewById(R.id.case_name_id);
                case_content = itemView.findViewById(R.id.case_content);
                case_pay_min = itemView.findViewById(R.id.case_pay_min);
                case_pay_max = itemView.findViewById(R.id.case_pay_max);
                case_date = itemView.findViewById(R.id.case_date);
                case_skill = itemView.findViewById(R.id.case_skill);
                case_location = itemView.findViewById(R.id.case_location);
            }
        }

    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.case_item_cardview, parent, false);
            return new GetCaseAdapter.MyViewHolder(itemView);
        }

    @Override
        public void onBindViewHolder(GetCaseAdapter.MyViewHolder myViewHolder, int position) {
            final MyCase myCase = myCases.get(position);
            List<CaseTag> caseTags = new ArrayList<>();
            if (Common.networkConnected((Activity) context)) {
                String url = Common.URL + "/SohoServlet";
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "caseTags");
                jsonObject.addProperty("caseId", myCase.getCaseId());
                String jsonOut = jsonObject.toString();
                caseTagTask = new MyTask(url, jsonOut);
                try {
                    String jsonIn = caseTagTask.execute().get();
                    Log.d(TAG, jsonIn);
                    Type listType = new TypeToken<List<CaseTag>>() {}.getType();
                    caseTags = new Gson().fromJson(jsonIn, listType);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                if (caseTags == null || caseTags.isEmpty()) {
                    myViewHolder.case_skill.setText("");
                } else {
                    String text="";
                    for(int i = 0; i<caseTags.size(); i++) {
                        text += caseTags.get(i).getName();
                    }
                    myViewHolder.case_skill.setText(text + " ");
                }
            } else {
                Common.showToast(context, R.string.msg_NoNetwork);
            }

            Date date = myCase.getCaseRecruitStart();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = dateFormat.format(date);
            myViewHolder.case_name_id.setText(myCase.getCaseName());
            myViewHolder.case_content.setText(myCase.getCaseDes());
            myViewHolder.case_pay_min.setText(String.valueOf(myCase.getCasePayMin()));
            myViewHolder.case_pay_max.setText(String.valueOf(myCase.getCasePayMax()));
            myViewHolder.case_date.setText(dateStr);
            myViewHolder.case_location.setText(myCase.getCaseLocation());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new CaseDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("myCase", myCase);
                    fragment.setArguments(bundle);
                    FragmentActivity fragmentActivity = (FragmentActivity) context;
                    FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return myCases.size();
        }
}
