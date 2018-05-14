package com.example.venson.soho.Case.Caseapply;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.venson.soho.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 2018/4/30.
 */

public class AppCaseFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.caseapply_applicationcase_layout, container, false);

        ViewPager leftViewPager = view.findViewById(R.id.viewPager_caseOverViewLeft);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        leftViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(leftViewPager);
        return view;
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<Page> pageList;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            pageList = new ArrayList<>();
            pageList.add(new Page(new AppFragment(), "申請中"));
            pageList.add(new Page(new ProcessingFragment(), "進行中"));
            pageList.add(new Page(new EndFragment(), "已結案"));
        }

        @Override
        public Fragment getItem(int position) {
            return pageList.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageList.get(position).getTitle();
        }
    }
}//end