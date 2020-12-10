package com.ishnn.labtools.ui.calculator.FunctionFragment.DilutionTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class DilutionSecondBtnFragment extends Fragment {

    @BindView(R.id.dilution_2nd_a)
    EditText dilution_ed_a;
    @BindView(R.id.dilution_2nd_b)
    EditText dilution_ed_b;
    @BindView(R.id.dilution_2nd_c)
    EditText dilution_ed_c;

    @BindView(R.id.dilution_2nd_density_unit)
    Spinner density_unit_dilution;
    @BindView(R.id.dilution_2nd_delete)
    Button del_btn;
    @BindView(R.id.dilution_2nd_cal)
    Button cal_btn;
    @BindView(R.id.sol_c)
    TextView dilution_sol_c;
    @BindView(R.id.sol_d)
    TextView dilution_sol_d;
    @BindView(R.id.sol_b)
    TextView dilution_sol_b;

    @BindView(R.id.sol_show)
    LinearLayout dilution_sol_show;
    @BindView(R.id.dilution_2ndbtn_title)
    TextView dilution_2ndbtn_title;
    @BindView(R.id.dilution_2nd_water)
    TextView dilution_2nd_water;
    @BindView(R.id.dilution_2nd_weight)
    TextView dilution_2nd_weight;
    @BindView(R.id.dilution_2nd_density)
    TextView dilution_2nd_density;

    @BindView(R.id.dilution_2ndbtn_amount)
    TextView dilution_2ndbtn_amount;
    @BindView(R.id.dilution_2ndbtn_sol_D)
    TextView dilution_2ndbtn_sol_D;
    @BindView(R.id.dilution_2ndbtn_sol_C)
    TextView dilution_2ndbtn_sol_C;
    @BindView(R.id.dilution_2ndbtn_sol_B)
    TextView dilution_2ndbtn_sol_B;
    @BindView(R.id.dilution_2ndbtn_sol_A)
    TextView dilution_2ndbtn_sol_A;
    @BindView(R.id.dilution_2ndbtn_sol)
    TextView dilution_2ndbtn_sol;

    static String v_unit;
    static double a, b, c, d,cal;
    SharedPreferences pref;

    public DilutionSecondBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_dilution_2nd, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 85, 151)));

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            dilution_2ndbtn_title.setText(getString(R.string.dilution_2ndbtn_title_kor));
            dilution_2nd_weight.setText(getString(R.string.dilution_2ndbtn_weight_kor));
            dilution_2nd_water.setText(getString(R.string.dilution_2ndbtn_concen_kor));
            dilution_2nd_density.setText(getString(R.string.dilution_2ndbtn_density_kor));
            dilution_2ndbtn_amount.setText(getString(R.string.dilution_2ndbtn_amount_kor));
            dilution_2ndbtn_sol_D.setText(getString(R.string.dilution_2ndbtn_sol_D_kor));
            dilution_2ndbtn_sol_C.setText(getString(R.string.dilution_2ndbtn_sol_C_kor));
            dilution_2ndbtn_sol_B.setText(getString(R.string.dilution_2ndbtn_sol_B_kor));
            dilution_2ndbtn_sol_A.setText(getString(R.string.dilution_2ndbtn_sol_A_kor));
            dilution_2ndbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.dilution_2ndbtn_alldel_kor));
            cal_btn.setText(getString(R.string.dilution_2ndbtn_cal_kor));
        } else {
            //영어버전
        }

        density_unit_dilution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                v_unit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dilution_ed_a.getText().toString().equals("") || dilution_ed_b.getText().toString().equals("") || dilution_ed_c.getText().toString().equals("")) {
                    makeText(getContext(), "Input value!!", Toast.LENGTH_SHORT).show();
                } else {
                    a = Double.parseDouble(dilution_ed_a.getText().toString());
                    b = Double.parseDouble(dilution_ed_b.getText().toString());
                    c = Double.parseDouble(dilution_ed_c.getText().toString());

                    d = dilutionFirstCal(a, b, c);
/*
                    if (v_unit.equals("uM")) {
                        w_unit = "uL";
                    } else if (v_unit.equals("mM")) {
                        w_unit = "mL";
                    }
  */
                    cal = c - d;
                    dilution_sol_d.setText(" "+ a + " ");
                    dilution_sol_c.setText(" "+ d + v_unit);
                    dilution_sol_b.setText(" "+ cal + v_unit);

                    dilution_sol_show.setVisibility(View.VISIBLE);
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dilution_ed_a.setText("");
                dilution_ed_b.setText("");
                dilution_ed_c.setText("");
                //dilution_ed_d.setText("");
                dilution_sol_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double dilutionFirstCal(double a, double b, double c) {
        if (a == 0) return 0.0;

        return  b * c / a;
    }
}