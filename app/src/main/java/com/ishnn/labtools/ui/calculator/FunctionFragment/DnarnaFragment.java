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

import com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment.DnarnaFifthBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment.DnarnaFirstBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment.DnarnaFourthBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment.DnarnaSecondBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment.DnarnaSixthBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment.DnarnaThirdBtnFragment;
import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DnarnaFragment extends Fragment {

    @BindView(R.id.dnarna_1st)
    LinearLayout first;
    @BindView(R.id.dnarna_2nd)
    LinearLayout second;
    @BindView(R.id.dnarna_3rd)
    LinearLayout third;
    @BindView(R.id.dnarna_4th)
    LinearLayout fourth;
    @BindView(R.id.dnarna_5th)
    LinearLayout fifth;
    @BindView(R.id.dnarna_6th)
    LinearLayout sixth;

    @BindView(R.id.dnarna_1st_text)
    TextView dnarna_1st_text;
    @BindView(R.id.dnarna_2nd_text)
    TextView dnarna_2nd_text;
    @BindView(R.id.dnarna_3rd_text)
    TextView dnarna_3rd_text;
    @BindView(R.id.dnarna_4th_text)
    TextView dnarna_4th_text;
    @BindView(R.id.dnarna_5th_text)
    TextView dnarna_5th_text;
    @BindView(R.id.dnarna_6th_text)
    TextView dnarna_6th_text;

    SharedPreferences pref;

    public DnarnaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_dnarna, container, false);

        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            //first.setText(getString(R.string.unit_converter_fragment_1stbtn_text_kor));
            //second.setText(getString(R.string.unit_converter_fragment_2ndbtn_text_kor));
            } else{
            //영어버전
        }

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { replaceFragment(new DnarnaFirstBtnFragment());
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DnarnaSecondBtnFragment());
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DnarnaThirdBtnFragment());
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DnarnaFourthBtnFragment());
            }
        });
        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DnarnaFifthBtnFragment());
            }
        });
        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DnarnaSixthBtnFragment());
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.calculator_content, fragment).addToBackStack(null).commit();
    }
}
