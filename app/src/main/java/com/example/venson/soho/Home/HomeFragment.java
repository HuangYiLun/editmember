package com.example.venson.soho.Home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.venson.soho.Common;
import com.example.venson.soho.Home.Case_Member_Tab.HomeCaseFragment;
import com.example.venson.soho.Home.Case_Member_Tab.HomeMemberFragment;
import com.example.venson.soho.Home.CategoryTab.DesignFragment;
import com.example.venson.soho.Home.CategoryTab.GetAllFragment;
import com.example.venson.soho.Home.CategoryTab.MediaFragment;
import com.example.venson.soho.Home.CategoryTab.NetWorkFragment;
import com.example.venson.soho.Home.CategoryTab.SalesFragment;
import com.example.venson.soho.Home.CategoryTab.ServiceFragment;
import com.example.venson.soho.Home.CategoryTab.TranslationFragment;
import com.example.venson.soho.MemberClass;
import com.example.venson.soho.MyCase;
import com.example.venson.soho.MyTask;
import com.example.venson.soho.R;
import com.example.venson.soho.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeCaseFragment";
    private Toolbar toolbar;
    private TabLayout cag_tablayout,case_mamber_tabLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyTask caseGetAllTask,memberGetAllTask;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.home_layout, container, false);
        findView(view);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initContent();
        getCag_tablayout();
        getCase_mamber_tabLayout();
        return view;
    }
    
    // work category
    public TabLayout getCag_tablayout() {
        cag_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            Fragment fragment;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
//                        fragment = new GetAllFragment();
//                        switchFragment(fragment);
                        showCases("getAllCase",0);
//                        showMember("getAllMember",0);
                        return;
                    case 1:
//                        fragment = new TranslationFragment();
//                        switchFragment(fragment);
                        showCases("getAllCase",1);
//                        showMember("getAllMember",1);
                        return;
                    case 2:
//                        fragment = new DesignFragment();
//                        switchFragment(fragment);
                        showCases("getAllCase",2);
//                        showMember("getAllMember",2);
                        return;
                    case 3:
//                        fragment = new SalesFragment();
//                        switchFragment(fragment);
                        showCases("getAllCase",3);
//                        showMember("getAllMember",3);
                        return;
                    case 4:
//                        fragment = new MediaFragment();
//                        switchFragment(fragment);
                        showCases("getAllCase", 4);
//                        showMember("getAllMember",4);
                        return;
                    case 5:
//                        fragment = new NetWorkFragment();
//                        switchFragment(fragment);
                        showCases("getAllCase",5);
//                        showMember("getAllMember",5);
                        return;
                    case 6:
//                        fragment = new ServiceFragment();
//                        switchFragment(fragment);
                        showCases("getAllCase", 6);
//                        showMember("getAll",6);
                        return;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return cag_tablayout;
    }
    // member and case category
    public TabLayout getCase_mamber_tabLayout() {
        case_mamber_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            Fragment fragment;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomeCaseFragment();
                        showCases("getAllCase",0);
                        switchFragment(fragment);
                        return;
                    case 1:
                        fragment = new HomeMemberFragment();
                        showMember("getAllMember",0);
                        switchFragment(fragment);
                        return;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return case_mamber_tabLayout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_item, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.toolbar_search:
                return true;

            case R.id.toolbar_item_add:
//                Fragment fragment = new CaseInsertFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                return true;

            case R.id.toolbar_item_filter:
                return true;

            default:
                break;
        }
        return false;
    }

    private void showCases(String action, int categroy) {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/SohoServlet";
            List<MyCase> myCases = new ArrayList<>();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", action);
            jsonObject.addProperty("category", categroy);
            String jsonOut = jsonObject.toString();
            caseGetAllTask = new MyTask(url, jsonOut);
            try {
                String jsonIn = caseGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List<MyCase>>() { }.getType();
                myCases = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (myCases == null) {
                Common.showToast(getActivity(), R.string.msg_NoCases);
                recyclerView.setAdapter(new GetCaseAdapter(myCases, getActivity()));
            } else {
                recyclerView.setAdapter(new GetCaseAdapter(myCases, getActivity()));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    private void showMember(String action, int category) {
        if (Common.networkConnected(getActivity())) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String url = Common.URL + "/SohoServlet";
            List<User> users = new ArrayList<>();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", action);
            jsonObject.addProperty("category", category);
            String jsonOut = jsonObject.toString();
            memberGetAllTask = new MyTask(url, jsonOut);
            try {
                String jsonIn = memberGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<List<User>>() { }.getType();
                users = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (users.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoMember);
                recyclerView.setAdapter(new GetMemberAdapter(users, getActivity()));
            } else {
                recyclerView.setAdapter(new GetMemberAdapter(users, getActivity()));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }


//    private class GetCaseAdapter extends RecyclerView.Adapter<GetCaseAdapter.MyViewHolder> {
//        private LayoutInflater layoutInflater;
//
//
//        GetCaseAdapter(List<MyCase> myCases, Context context) {
//            layoutInflater = LayoutInflater.from(context);
//            HomeFragment.myCases = myCases;
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            TextView case_content,case_pay_min,case_date,case_skill,case_location,case_name_id,case_pay_max;
//            MyViewHolder(View itemView) {
//                super(itemView);
//                case_name_id = itemView.findViewById(R.id.case_name_id);
//                case_content = itemView.findViewById(R.id.case_content);
//                case_pay_min = itemView.findViewById(R.id.case_pay_min);
//                case_pay_max = itemView.findViewById(R.id.case_pay_max);
//                case_date = itemView.findViewById(R.id.case_date);
//                case_skill = itemView.findViewById(R.id.case_skill);
//                case_location = itemView.findViewById(R.id.case_location);
//            }
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = layoutInflater.inflate(R.layout.case_item_cardview, parent, false);
//            return new GetCaseAdapter.MyViewHolder(itemView);
//        }
//
//        @Override
//        public void onBindViewHolder(GetCaseAdapter.MyViewHolder myViewHolder, int position) {
//            final MyCase myCase = myCases.get(position);
//            List<CaseTag> caseTags = new ArrayList<>();
//            if (Common.networkConnected(getActivity())) {
//                String url = Common.URL + "/SohoServlet";
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("action", "caseTags");
//                jsonObject.addProperty("caseId", myCase.getCaseId());
//                String jsonOut = jsonObject.toString();
//                caseTagTask = new MyTask(url, jsonOut);
//                try {
//                    String jsonIn = caseTagTask.execute().get();
//                    Log.d(TAG, jsonIn);
//                    Type listType = new TypeToken<List<CaseTag>>() {}.getType();
//                    caseTags = new Gson().fromJson(jsonIn, listType);
//                } catch (Exception e) {
//                    Log.e(TAG, e.toString());
//                }
//                if (caseTags == null || caseTags.isEmpty()) {
////                    Common.showToast(getActivity(), R.string.msg_NoCases);
//                    myViewHolder.case_skill.setText("");
//                } else {
//                    String text="";
//                    for(int i = 0; i<caseTags.size(); i++) {
//                        text += caseTags.get(i).getName();
//                    }
//                    myViewHolder.case_skill.setText(text + " ");
//                }
//            } else {
//                Common.showToast(getActivity(), R.string.msg_NoNetwork);
//            }
//
//            Date date = myCase.getCaseRecruitStart();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String dateStr = dateFormat.format(date);
//            myViewHolder.case_name_id.setText(myCase.getCaseName());
//            myViewHolder.case_content.setText(myCase.getCaseDes());
//            myViewHolder.case_pay_min.setText(String.valueOf(myCase.getCasePayMin()));
//            myViewHolder.case_pay_max.setText(String.valueOf(myCase.getCasePayMax()));
//            myViewHolder.case_date.setText(dateStr);
//            myViewHolder.case_location.setText(myCase.getCaseLocation());
//            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Fragment fragment = new CaseDetailFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("myCase", myCase);
//                    fragment.setArguments(bundle);
//                    FragmentManager fragmentManager = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.content, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
//            });
//        }
//        @Override
//        public int getItemCount() {
//            return myCases.size();
//        }
//
//    }

    private void findView(View view) {
        toolbar = view.findViewById(R.id.tool_bar);
        cag_tablayout = view.findViewById(R.id.cag_tablayout);
        case_mamber_tabLayout = view.findViewById(R.id.case_mamber_tabLayout);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.HomerecyclerView);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.case_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initContent() {
//        Fragment fragment = new GetAllFragment();
        showCases("getAllCase",0);
//        switchFragment(fragment);
    }

} //end
