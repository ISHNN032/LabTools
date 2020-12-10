package com.ishnn.labtools.ui.calculator.FunctionFragment.UnitConverterTabFragment;


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

public class CentSpeedBtnFragment extends Fragment {

    @BindView(R.id.centspeed_ed_a)
    EditText centspeed_ed_a;

    @BindView(R.id.centspeed_ed_b)
    EditText centspeed_ed_b;


    @BindView(R.id.centspeed_delete)
    Button del_btn;
    @BindView(R.id.centspeed_cal)
    Button cal_btn;
    @BindView(R.id.centspeed_mm_a)
    TextView centspeed_mm_a;
    @BindView(R.id.centspeed_b)
    TextView centspeed_b;

    @BindView(R.id.centspeed_show)
    LinearLayout centspeed_show;
    @BindView(R.id.centspeed_title)
    TextView centspeed_title;

    static String v_unit;
    static double a, b,c;
    SharedPreferences pref;

    public CentSpeedBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_centspeed_btn, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            centspeed_title.setText(getString(R.string.concentration_3rdbtn_title_kor));
            centspeed_mm_a.setText(getString(R.string.concentration_3rdbtn_density_kor));
            centspeed_b.setText(getString(R.string.concentration_3rdbtn_sol_B_kor));
            del_btn.setText(getString(R.string.concentration_3rdbtn_alldel_kor));
            cal_btn.setText(getString(R.string.concentration_3rdbtn_cal_kor));
        } else {
            //영어버전
        }

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (centspeed_ed_a.getText().toString().equals("")) {
                    makeText(getContext(), "Input value!!", Toast.LENGTH_SHORT).show();
                } else {
                    a = Double.parseDouble(centspeed_ed_a.getText().toString());
                    b = dilutionFirstCal(a);

                    centspeed_mm_a.setText(" "+ a + " % ");
                    centspeed_b.setText(" "+ b + " ppm ");

                    centspeed_show.setVisibility(View.VISIBLE);
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centspeed_mm_a.setText("");
                //dilution_ed_d.setText("");
                centspeed_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double dilutionFirstCal(double a) {
        return  a * 10000;
    }
}