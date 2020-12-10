package com.ishnn.labtools.ui.calculator.FunctionFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ishnn.labtools.ui.calculator.FunctionFragment.CellCultureFragment.CellFirstBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.CellCultureFragment.CellFourthBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.CellCultureFragment.CellSecondBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.CellCultureFragment.CellThirdBtnFragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cell_CultureFragment extends Fragment {

    @BindView(R.id.cell_1st)
    LinearLayout first;
    @BindView(R.id.cell_2nd)
    LinearLayout second;
    @BindView(R.id.cell_3rd)
    LinearLayout third;
    @BindView(R.id.cell_4th)
    LinearLayout fourth;
    @BindView(R.id.cell_1st_text)
    TextView cell_1st_text;
    @BindView(R.id.cell_2nd_text)
    TextView cell_2nd_text;
    @BindView(R.id.cell_3rd_text)
    TextView cell_3rd_text;
    @BindView(R.id.cell_4th_text)
    TextView cell_4th_text;
    public Cell_CultureFragment() { }

    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_cell__culture, container, false);

        ButterKnife.bind(this, view);

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            cell_1st_text.setText(getString(R.string.cellculture_fragment_1stbtn_text_kor));
            cell_2nd_text.setText(getString(R.string.cellculture_fragment_2ndbtn_text_kor));
            cell_3rd_text.setText(getString(R.string.cellculture_fragment_3rdbtn_text_kor));
            cell_4th_text.setText(getString(R.string.cellculture_fragment_4thbtn_text_kor));
        } else{
            //영어버전
        }

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CellFirstBtnFragment());
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CellSecondBtnFragment());
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CellThirdBtnFragment());
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CellFourthBtnFragment());
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }

}
