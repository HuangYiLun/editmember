package com.example.venson.soho.Case;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.venson.soho.Home.CategoryTab.GetAllFragment;
import com.example.venson.soho.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 2018/4/21.
 */

public class CaseOverViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.case_overview_fragment, container, false);
//
//        final Toolbar toolbar = view.findViewById(R.id.tool_bar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//        ViewPager viewPager = view.findViewById(R.id.viewPager);
//        viewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
//
//        TabLayout tabLayout = view.findViewById(R.id.tablayout);
//        tabLayout.setupWithViewPager(viewPager);
//        return view;
//    }
//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        List<Page> pageList;
//
//        public MyPagerAdapter(FragmentManager fragmentManager) {
//            super(fragmentManager);
//            pageList = new ArrayList<>();
//            pageList.add(new Page(new ApplyCaseFragment(), "申請案件"));
//            pageList.add(new Page(new PostCaseFragment(), "發案案件"));
//
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return pageList.get(position).getFragment();
//        }
//
//        @Override
//        public int getCount() {
//            return pageList.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return pageList.get(position).getTitle();
//        }
//
    return  null;}



}
