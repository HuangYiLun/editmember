package com.example.venson.soho.Case.Caseapply;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.venson.soho.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricky on 2018/4/30.
 */

public class AppFragment extends Fragment {
    private List<Case> caseList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.caseapply_application_layout, container, false);

        ListView listView = view.findViewById(R.id.listViewApplication);

        caseList= getCaseList();
        listView.setAdapter(new CaseAdapter(getActivity(),caseList));
        return view;
    }

    private class CaseAdapter extends BaseAdapter {
        Context context;
        List<Case> caseList;

        public CaseAdapter(Context context, List<Case> caseList) {
            this.context = context;
            this.caseList = caseList;
        }

        @Override
        public int getCount() {
            return caseList.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            if (view == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                view = layoutInflater.inflate(R.layout.applicationlistview_item,viewGroup, false);
            }

            Case caseItem = caseList.get(position);

            TextView tvCaseName =
                    view.findViewById(R.id.applicationCaseName);
            tvCaseName.setText(String.valueOf(caseItem.getCasename()));

            TextView tvCaseContent =
                    view.findViewById(R.id.applicationCaseContent);
            tvCaseContent.setText(caseItem.getCasecontent());

            return view;
        }
        @Override
        public Object getItem(int position) {
            return caseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return caseList.get(position).getId();
        }
    }

    private List<Case> getCaseList() {
        List<Case> caseList = new ArrayList<>();
        caseList.add(new Case(1,"ios","swift"));
        return caseList;
    }


}//end