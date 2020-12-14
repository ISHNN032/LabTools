package com.ishnn.labtools.ui.home.contentfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ishnn.labtools.ui.home.contentfragment.UnitConverterTabFragment.CentSpeedBtnFragment;
import com.ishnn.labtools.ui.home.contentfragment.UnitConverterTabFragment.UnitConverterFirstBtnFragment;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitConverterFragment extends Fragment {

    @BindView(R.id.unit_1st)
    LinearLayout first;
    @BindView(R.id.unit_2nd)
    LinearLayout second;
    @BindView(R.id.unit_1st_text)
    TextView unit_1st_text;
    @BindView(R.id.unit_2nd_text)
    TextView unit_2nd_text;

    SharedPreferences pref;

    public UnitConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_unit_converter, container, false);

        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            unit_1st_text.setText(getString(R.string.unit_converter_fragment_1stbtn_text_kor));
            unit_2nd_text.setText(getString(R.string.unit_converter_fragment_2ndbtn_text_kor));
            } else{
            //영어버전
        }

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new UnitConverterFirstBtnFragment());
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    replaceFragment(new CentSpeedBtnFragment());
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
