package com.ishnn.labtools.ui.calculator.FunctionFragment.ProteinTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.ButterKnife;

public class ProteinThirdBtnFragment extends Fragment {
    SharedPreferences pref;

    public ProteinThirdBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator_content_protein_3rd, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 85, 151)));

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {

        } else {
            //영어버전
        }


        return view;
    }
}
