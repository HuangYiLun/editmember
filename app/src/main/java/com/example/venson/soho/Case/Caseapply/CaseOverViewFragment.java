package com.example.venson.soho.Case.Caseapply;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.venson.soho.R;


public class CaseOverViewFragment extends Fragment {
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.case_overview_fragment, container, false);
        Button btApplicationCase = view.findViewById(R.id.btApplicationCase);
        Button btPostCase = view.findViewById(R.id.btPostCase);

        btApplicationCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ApplicationCaseActivity.class);
                startActivity(intent);
            }
        });

        btPostCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostCaseActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.case_overview_fragment,container,false);
//        toolbar = view.findViewById(R.id.toolbar_Overview);
//
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
//        ViewPager viewPager = view.findViewById(R.id.viewPager_caseOverView);
//
//
//        viewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
//        tabLayout.setupWithViewPager(viewPager);
//        return view;
//    }
//
//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        List<Page> pageList;
//
//        public MyPagerAdapter(FragmentManager fragmentManager) {
//            super(fragmentManager);
//            pageList = new ArrayList<>();
//            pageList.add(new Page(new AppCaseFragment(), "申請案件"));
//            pageList.add(new Page(new PostFragment(), "發案案件"));
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
//    }

}//end

