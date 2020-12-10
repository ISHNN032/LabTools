package com.ishnn.labtools.ui.calculator.FunctionFragment.CellCultureFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CellThirdBtnFragment extends Fragment {
    @BindView(R.id.cell_3rdbtn_help)
    TextView cell_3rdbtn_help;


    SharedPreferences pref;

    public CellThirdBtnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator_content_cell_3rd, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            cell_3rdbtn_help.setText(getString(R.string.cell_3rdbtn_help_kor));
        } else {
            //영어버전
        }

        // Inflate the layout for this fragment
        return view;
    }

}
