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

import com.ishnn.labtools.ui.calculator.FunctionFragment.MakeTabFragment.MakeFirstBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.MakeTabFragment.MakeFourthBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.MakeTabFragment.MakeSecondBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.MakeTabFragment.MakeThirdBtnFragment;
import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakeFragment extends Fragment {

    @BindView(R.id.make_1st)
    LinearLayout first;
    @BindView(R.id.make_2nd)
    LinearLayout second;
    @BindView(R.id.make_3rd)
    LinearLayout third;
    @BindView(R.id.make_4th)
    LinearLayout fourth;

    @BindView(R.id.make_1st_text)
    TextView make_1st_text;
    @BindView(R.id.make_2nd_text)
    TextView make_2nd_text;
    @BindView(R.id.make_3rd_text)
    TextView make_3rd_text;
    @BindView(R.id.make_4th_text)
    TextView make_4th_text;
    SharedPreferences pref;

    public MakeFragment() {    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_make, container, false);

        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            make_1st_text.setText(getString(R.string.make_fragment_1stbtn_text_kor));
            make_2nd_text.setText(getString(R.string.make_fragment_2ndbtn_text_kor));
            make_3rd_text.setText(getString(R.string.make_fragment_3rdbtn_text_kor));
            make_4th_text.setText(getString(R.string.make_fragment_4thbtn_text_kor));
        } else{
            //영어버전
        }

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MakeFirstBtnFragment());
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MakeSecondBtnFragment());
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MakeThirdBtnFragment());
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MakeFourthBtnFragment());
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
