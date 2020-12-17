package com.ishnn.labtools.ui.home.content;

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

import com.ishnn.labtools.ui.home.content.ConcentrationTabFragment.ConcentrationFifthBtnFragment;
import com.ishnn.labtools.ui.home.content.ConcentrationTabFragment.ConcentrationFirstBtnFragment;
import com.ishnn.labtools.ui.home.content.ConcentrationTabFragment.ConcentrationFourthBtnFragment;
import com.ishnn.labtools.ui.home.content.ConcentrationTabFragment.ConcentrationSecondBtnFragment;
import com.ishnn.labtools.ui.home.content.ConcentrationTabFragment.ConcentrationThirdBtnFragment;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConcentrationFragment extends Fragment {
    @BindView(R.id.concentration_1st)
    LinearLayout first;
    @BindView(R.id.concentration_2nd)
    LinearLayout second;
    @BindView(R.id.concentration_3rd)
    LinearLayout third;
    @BindView(R.id.concentration_4th)
    LinearLayout fourth;
    @BindView(R.id.concentration_5th)
    LinearLayout fifth;

    @BindView(R.id.concentration_1st_text)
    TextView concentration_1st_text;
    @BindView(R.id.concentration_2nd_text)
    TextView concentration_2nd_text;
    @BindView(R.id.concentration_3rd_text)
    TextView concentration_3rd_text;
    @BindView(R.id.concentration_4th_text)
    TextView concentration_4th_text;
    @BindView(R.id.concentration_5th_text)
    TextView concentration_5th_text;

    SharedPreferences pref;

    public ConcentrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_content_concentration, container, false);

        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            //second.setText(getString(R.string.unit_converter_fragment_2ndbtn_text_kor));
            } else{
            //영어버전
        }

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ConcentrationFirstBtnFragment());
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ConcentrationSecondBtnFragment());
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ConcentrationThirdBtnFragment());
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ConcentrationFourthBtnFragment());
            }
        });
        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ConcentrationFifthBtnFragment());
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }
}
