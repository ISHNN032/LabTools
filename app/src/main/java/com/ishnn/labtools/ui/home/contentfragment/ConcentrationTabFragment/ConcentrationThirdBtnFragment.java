package com.ishnn.labtools.ui.home.contentfragment.ConcentrationTabFragment;


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

/**
 * A simple {@link Fragment} subclass.
 */

public class ConcentrationThirdBtnFragment extends Fragment {

    @BindView(R.id.concentration_3rd_a)
    EditText dilution_ed_a;

    @BindView(R.id.concentration_3rd_delete)
    Button del_btn;
    @BindView(R.id.concentration_3rd_cal)
    Button cal_btn;
    @BindView(R.id.sol_a)
    TextView dilution_sol_a;
    @BindView(R.id.sol_b)
    TextView dilution_sol_b;

    @BindView(R.id.sol_show)
    LinearLayout dilution_sol_show;
    @BindView(R.id.concentration_3rdbtn_title)
    TextView concentration_3rdbtn_title;

    @BindView(R.id.concentration_3rd_density)
    TextView concentration_3rd_density;

    @BindView(R.id.concentration_3rdbtn_sol_B)
    TextView concentration_3rdbtn_sol_B;
    @BindView(R.id.concentration_3rdbtn_sol_A)
    TextView concentration_3rdbtn_sol_A;
    @BindView(R.id.concentration_3rdbtn_sol)
    TextView concentration_3rdbtn_sol;

    static String v_unit;
    static double a, b;
    SharedPreferences pref;

    public ConcentrationThirdBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_concentration_3rd, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            concentration_3rdbtn_title.setText(getString(R.string.concentration_3rdbtn_title_kor));
            concentration_3rd_density.setText(getString(R.string.concentration_3rdbtn_density_kor));
            concentration_3rdbtn_sol_B.setText(getString(R.string.concentration_3rdbtn_sol_B_kor));
            concentration_3rdbtn_sol_A.setText(getString(R.string.concentration_3rdbtn_sol_A_kor));
            concentration_3rdbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.concentration_3rdbtn_alldel_kor));
            cal_btn.setText(getString(R.string.concentration_3rdbtn_cal_kor));
        } else {
            //영어버전
        }

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dilution_ed_a.getText().toString().equals("")) {
                    makeText(getContext(), "Input value!!", Toast.LENGTH_SHORT).show();
                } else {
                    a = Double.parseDouble(dilution_ed_a.getText().toString());
                    b = dilutionFirstCal(a);

                    dilution_sol_a.setText(" "+ a + " % ");
                    dilution_sol_b.setText(" "+ b + " ppm ");

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

    public double dilutionFirstCal(double a) {
        return  a * 10000;
    }
}