package com.example.venson.soho.Case;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.venson.soho.Home.CategoryTab.GetAllFragment;
import com.example.venson.soho.R;

/**
 * Created by ricky on 2018/4/21.
 */

public class CaseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.case_fragment, container, false);

        return view;
    }
}//end