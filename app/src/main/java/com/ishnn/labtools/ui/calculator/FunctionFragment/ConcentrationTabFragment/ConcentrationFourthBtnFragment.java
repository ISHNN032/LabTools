package com.ishnn.labtools.ui.calculator.FunctionFragment.ConcentrationTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */

public class ConcentrationFourthBtnFragment extends Fragment {

    @BindView(R.id.concentration_4th_a)
    EditText dilution_ed_a;
    @BindView(R.id.concentration_4th_b)
    EditText dilution_ed_b;

    @BindView(R.id.concentration_4th_delete)
    Button del_btn;
    @BindView(R.id.concentration_4th_cal)
    Button cal_btn;
    @BindView(R.id.sol_a)
    TextView dilution_sol_a;
    @BindView(R.id.sol_b)
    TextView dilution_sol_b;

    @BindView(R.id.sol_show)
    LinearLayout dilution_sol_show;
    @BindView(R.id.concentration_4thbtn_title)
    TextView concentration_4thbtn_title;

    @BindView(R.id.concentration_4th_water)
    TextView concentration_4th_water;
    @BindView(R.id.concentration_4th_weight)
    TextView concentration_4th_weight;

    @BindView(R.id.concentration_4thbtn_sol_B)
    TextView concentration_4thbtn_sol_B;
    @BindView(R.id.concentration_4thbtn_sol_A)
    TextView concentration_4thbtn_sol_A;
    @BindView(R.id.concentration_4thbtn_sol)
    TextView concentration_4thbtn_sol;

    static String v_unit;
    static double a, b, c;
    SharedPreferences pref;

    public ConcentrationFourthBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_concentration_4th, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 85, 151)));

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            concentration_4thbtn_title.setText(getString(R.string.concentration_4thbtn_title_kor));
            concentration_4th_water.setText(getString(R.string.concentration_4thbtn_water_kor));
            concentration_4th_weight.setText(getString(R.string.concentration_4thbtn_weight_kor));
            concentration_4thbtn_sol_B.setText(getString(R.string.concentration_4thbtn_sol_B_kor));
            concentration_4thbtn_sol_A.setText(getString(R.string.concentration_4thbtn_sol_A_kor));
            concentration_4thbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.concentration_4thbtn_alldel_kor));
            cal_btn.setText(getString(R.string.concentration_4thbtn_cal_kor));
        } else {
            //영어버전
        }

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dilution_ed_a.getText().toString().equals("") || dilution_ed_b.getText().toString().equals("")) {
                    makeText(getContext(), "Input value!!", Toast.LENGTH_SHORT).show();
                } else {
                    a = Double.parseDouble(dilution_ed_a.getText().toString());
                    b = Double.parseDouble(dilution_ed_b.getText().toString());
                    c = dilutionFirstCal(a, b);

                    dilution_sol_a.setText(" "+ a + " ");
                    dilution_sol_b.setText(" "+ c + " ppm ");

                    dilution_sol_show.setVisibility(View.VISIBLE);
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilution_ed_a.setText("");
                //dilution_ed_d.setText("");
                dilution_sol_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double dilutionFirstCal(double a, double b) {
        return  a * b* 1000;
    }
}
