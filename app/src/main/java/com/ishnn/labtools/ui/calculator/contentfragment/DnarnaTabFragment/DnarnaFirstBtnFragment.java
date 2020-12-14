package com.ishnn.labtools.ui.calculator.contentfragment.DnarnaTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

public class DnarnaFirstBtnFragment extends Fragment {

    @BindView(R.id.concentration_1st_a)
    EditText concentration_ed_a;
    @BindView(R.id.concentration_1st_b)
    EditText concentration_ed_b;
    @BindView(R.id.concentration_1st_c)
    EditText concentration_ed_c;
    @BindView(R.id.concentration_1st_delete)
    Button del_btn;
    @BindView(R.id.concentration_1st_cal)
    Button cal_btn;
    @BindView(R.id.sol_c)
    TextView concentration_sol_c;
    @BindView(R.id.sol_d)
    TextView concentration_sol_d;
    @BindView(R.id.sol_show)
    LinearLayout concentration_sol_show;

    @BindView(R.id.concentration_1stbtn_title)
    TextView concentration_1stbtn_title;
    @BindView(R.id.concentration_1stbtn_weight)
    TextView concentration_1stbtn_weight;
    @BindView(R.id.concentration_1stbtn_water)
    TextView concentration_1stbtn_water;
    @BindView(R.id.concentration_1stbtn_density)
    TextView concentration_1stbtn_density;
    @BindView(R.id.concentration_1stbtn_amount)
    TextView concentration_1stbtn_amount;
    @BindView(R.id.concentration_1stbtn_sol_D)
    TextView concentration_1stbtn_sol_D;
    @BindView(R.id.concentration_1stbtn_sol_C)
    TextView concentration_1stbtn_sol_C;
    @BindView(R.id.concentration_1stbtn_sol)
    TextView concentration_1stbtn_sol;

    static String w_unit, v_unit, a_we_unit;
    static double a, b, c, d;
    SharedPreferences pref;

    public DnarnaFirstBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_concentration_1st, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            concentration_1stbtn_title.setText(getString(R.string.concentration_1stbtn_title_kor));
            concentration_1stbtn_weight.setText(getString(R.string.concentration_1stbtn_weight_kor));
            concentration_1stbtn_water.setText(getString(R.string.concentration_1stbtn_concen_kor));
            concentration_1stbtn_density.setText(getString(R.string.concentration_1stbtn_density_kor));
            concentration_1stbtn_amount.setText(getString(R.string.concentration_1stbtn_amount_kor));
            concentration_1stbtn_sol_D.setText(getString(R.string.concentration_1stbtn_sol_D_kor));
            concentration_1stbtn_sol_C.setText(getString(R.string.concentration_1stbtn_sol_C_kor));
            concentration_1stbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.concentration_1stbtn_alldel_kor));
            cal_btn.setText(getString(R.string.concentration_1stbtn_cal_kor));
        } else {
            //영어버전
        }

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (concentration_ed_a.getText().toString().equals("") || concentration_ed_b.getText().toString().equals("") || concentration_ed_c.getText().toString().equals("")) {
                    makeText(getContext(), "Input value!!", Toast.LENGTH_SHORT).show();
                } else {
                    a = Double.parseDouble(concentration_ed_a.getText().toString());
                    b = Double.parseDouble(concentration_ed_b.getText().toString());
                    c = Double.parseDouble(concentration_ed_c.getText().toString());

                    d = concentrationFirstCal(a, b, c, a_we_unit, w_unit, v_unit);

                    if (d < 0.001 && d >= 0.0000001) {
                        d = d * 1000;
                        concentration_sol_c.setText(d + "mM");
                    } else if (d < 0.0000001 && d != 0) {
                        d = d * 1000000;
                        concentration_sol_c.setText(d + "uM");
                    } else {
                        concentration_sol_c.setText(d + "M");
                    }

                    concentration_sol_d.setText(a + "%");
                    concentration_sol_show.setVisibility(View.VISIBLE);
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concentration_ed_a.setText("");
                concentration_ed_b.setText("");
                concentration_ed_c.setText("");
                //concentration_sol_d.setText("");
                //concentration_sol_c.setText("");
                concentration_sol_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double concentrationFirstCal(double a, double b, double c, String a_we_unit, String w_unit, String v_unit) {
        if (b == 0) return 0.0;

        return (a * c * 10 ) / b;
    }
}
