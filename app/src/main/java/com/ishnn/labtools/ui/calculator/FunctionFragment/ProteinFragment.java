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

import com.ishnn.labtools.ui.calculator.FunctionFragment.ProteinTabFragment.ProteinFirstBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.ProteinTabFragment.ProteinSecondBtnFragment;
import com.ishnn.labtools.ui.calculator.FunctionFragment.ProteinTabFragment.ProteinThirdBtnFragment;
import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProteinFragment extends Fragment {

    @BindView(R.id.protein_1st)
    LinearLayout first;
    @BindView(R.id.protein_2nd)
    LinearLayout second;
    @BindView(R.id.protein_3rd)
    LinearLayout third;
    @BindView(R.id.protein_1st_text)
    TextView protein_1st_text;
    @BindView(R.id.protein_2nd_text)
    TextView protein_2nd_text;
    @BindView(R.id.protein_3rd_text)
    TextView protein_3rd_text;

    SharedPreferences pref;

    public ProteinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_protein, container, false);

        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            protein_1st_text.setText(getString(R.string.protein_fragment_1stbtn_text_kor));
            protein_2nd_text.setText(getString(R.string.protein_fragment_2ndbtn_text_kor));
            protein_3rd_text.setText(getString(R.string.protein_fragment_3rdbtn_text_kor));
        } else{
            //영어버전
        }

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProteinFirstBtnFragment());
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProteinSecondBtnFragment());
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProteinThirdBtnFragment());
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
