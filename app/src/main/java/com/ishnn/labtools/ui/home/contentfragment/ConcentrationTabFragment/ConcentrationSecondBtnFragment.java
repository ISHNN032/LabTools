package com.ishnn.labtools.ui.home.contentfragment.ConcentrationTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
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

import androidx.fragment.app.Fragment;

import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

public class ConcentrationSecondBtnFragment extends Fragment {

    @BindView(R.id.concentration_2nd_a)
    EditText dilution_ed_a;
    @BindView(R.id.concentration_2nd_b)
    EditText dilution_ed_b;
    @BindView(R.id.concentration_2nd_c)
    EditText dilution_ed_c;

    @BindView(R.id.concentration_2nd_water_unit)
    Spinner water_unit_dilution;
    @BindView(R.id.concentration_2nd_delete)
    Button del_btn;
    @BindView(R.id.concentration_2nd_cal)
    Button cal_btn;
    @BindView(R.id.sol_c)
    TextView dilution_sol_c;
    @BindView(R.id.sol_d)
    TextView dilution_sol_d;
    @BindView(R.id.sol_show)
    LinearLayout dilution_sol_show;

    @BindView(R.id.concentration_2ndbtn_title)
    TextView concentration_2ndbtn_title;
    @BindView(R.id.concentration_2nd_weight)
    TextView concentration_2ndbtn_weight;
    @BindView(R.id.concentration_2nd_water)
    TextView concentration_2ndbtn_water;
    @BindView(R.id.concentration_2nd_density)
    TextView concentration_2ndbtn_density;
    @BindView(R.id.concentration_2ndbtn_amount)
    TextView concentration_2ndbtn_amount;
    @BindView(R.id.concentration_2ndbtn_sol_D)
    TextView concentration_2ndbtn_sol_D;
    @BindView(R.id.concentration_2ndbtn_sol_C)
    TextView concentration_2ndbtn_sol_C;

    @BindView(R.id.concentration_2ndbtn_sol)
    TextView concentration_2ndbtn_sol;

    static String w_unit;
    static double a, b, c, d;
    SharedPreferences pref;

    public ConcentrationSecondBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_concentration_2nd, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            concentration_2ndbtn_title.setText(getString(R.string.concentration_2ndbtn_title_kor));
            concentration_2ndbtn_weight.setText(getString(R.string.concentration_2ndbtn_weight_kor));
            concentration_2ndbtn_water.setText(getString(R.string.concentration_2ndbtn_concen_kor));
            concentration_2ndbtn_density.setText(getString(R.string.concentration_2ndbtn_density_kor));
            concentration_2ndbtn_amount.setText(getString(R.string.concentration_2ndbtn_amount_kor));
            concentration_2ndbtn_sol_D.setText(getString(R.string.concentration_2ndbtn_sol_D_kor));
            concentration_2ndbtn_sol_C.setText(getString(R.string.concentration_2ndbtn_sol_C_kor));
            concentration_2ndbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.concentration_2ndbtn_alldel_kor));
            cal_btn.setText(getString(R.string.concentration_2ndbtn_cal_kor));
        } else {
            //영어버전
        }
        water_unit_dilution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                w_unit = parent.getItemAtPosition(position).toString();
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

                    if (w_unit.equals("uM")) {
                        d = d / 1000000;
                    } else if (w_unit.equals("mM")) {
                        d = d / 1000;
                    }
                    dilution_sol_d.setText(" ( ) "+ w_unit + " ");
                    dilution_sol_c.setText(" "+ d + "% ");

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
        if (c == 0) return 0.0;

        return a* b * 0.1 / c;
    }
}