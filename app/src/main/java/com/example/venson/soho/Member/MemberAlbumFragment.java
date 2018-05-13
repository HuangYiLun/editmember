package com.example.venson.soho.Member;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.venson.soho.Home.CategoryTab.GetAllFragment;
import com.example.venson.soho.MainActivity;
import com.example.venson.soho.R;

import java.util.zip.Inflater;


public class MemberAlbumFragment extends Fragment {
    private RecyclerView rvAlbum;
    private TextView tvToolbar_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_album, container, false);
        MainActivity activity = (MainActivity) getActivity();

        return view;
    }



}
