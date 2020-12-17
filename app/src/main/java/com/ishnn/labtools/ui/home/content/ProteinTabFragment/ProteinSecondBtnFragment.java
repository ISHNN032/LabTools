package com.ishnn.labtools.ui.home.content.ProteinTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProteinSecondBtnFragment extends Fragment {
    @BindView(R.id.protein_2nd1_help)
    TextView protein_2nd_1_help;
    @BindView(R.id.protein_2nd2_help)
    TextView protein_2nd_2_help;
    SharedPreferences pref;

    public ProteinSecondBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator_content_protein_2nd, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            protein_2nd_1_help.setText(getString(R.string.protein_2nd1_help_kor));
            protein_2nd_2_help.setText(getString(R.string.protein_2nd2_help_kor));
        } else {
            //영어버전
        }


        return view;
    }
}
