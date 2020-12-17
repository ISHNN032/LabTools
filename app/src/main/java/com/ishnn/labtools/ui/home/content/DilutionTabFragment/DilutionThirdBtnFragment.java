package com.ishnn.labtools.ui.home.content.DilutionTabFragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

public class DilutionThirdBtnFragment extends Fragment {

    @BindView(R.id.dilution_3rd_a)
    EditText dilution_ed_a;
    @BindView(R.id.dilution_3rd_b)
    EditText dilution_ed_b;
    @BindView(R.id.dilution_3rd_c)
    EditText dilution_ed_c;

    @BindView(R.id.dilution_3rd_density_unit)
    Spinner water_unit_dilution;
    @BindView(R.id.dilution_3rd_delete)
    Button del_btn;
    @BindView(R.id.dilution_3rd_cal)
    Button cal_btn;
    @BindView(R.id.sol_c)
    TextView dilution_sol_c;
    @BindView(R.id.sol_d)
    TextView dilution_sol_d;
    @BindView(R.id.sol_show)
    LinearLayout dilution_sol_show;

    @BindView(R.id.dilution_3rdbtn_title)
    TextView dilution_3rdbtn_title;
    @BindView(R.id.dilution_3rd_weight)
    TextView dilution_3rdbtn_weight;
    @BindView(R.id.dilution_3rd_water)
    TextView dilution_3rdbtn_water;
    @BindView(R.id.dilution_3rd_density)
    TextView dilution_3rdbtn_density;
    @BindView(R.id.dilution_3rdbtn_amount)
    TextView dilution_3rdbtn_amount;
    @BindView(R.id.dilution_3rdbtn_sol_D)
    TextView dilution_3rdbtn_sol_D;
    @BindView(R.id.dilution_3rdbtn_sol_C)
    TextView dilution_3rdbtn_sol_C;

    @BindView(R.id.dilution_3rdbtn_sol)
    TextView dilution_3rdbtn_sol;

    static String w_unit;
    static double a, b, c, d;
    SharedPreferences pref;

    public DilutionThirdBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_dilution_3rd, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            dilution_3rdbtn_title.setText(getString(R.string.dilution_3rdbtn_title_kor));
            dilution_3rdbtn_weight.setText(getString(R.string.dilution_3rdbtn_weight_kor));
            dilution_3rdbtn_water.setText(getString(R.string.dilution_3rdbtn_concen_kor));
            dilution_3rdbtn_density.setText(getString(R.string.dilution_3rdbtn_density_kor));
            dilution_3rdbtn_amount.setText(getString(R.string.dilution_3rdbtn_amount_kor));
            dilution_3rdbtn_sol_D.setText(getString(R.string.dilution_3rdbtn_sol_D_kor));
            dilution_3rdbtn_sol_C.setText(getString(R.string.dilution_3rdbtn_sol_C_kor));
            dilution_3rdbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.dilution_3rdbtn_alldel_kor));
            cal_btn.setText(getString(R.string.dilution_3rdbtn_cal_kor));
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
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                View view = requireActivity().getCurrentFocus();
                if (view == null) {
                    view = new View(requireActivity());
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


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