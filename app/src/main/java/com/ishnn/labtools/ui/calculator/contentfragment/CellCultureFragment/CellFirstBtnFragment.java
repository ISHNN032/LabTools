package com.ishnn.labtools.ui.calculator.contentfragment.CellCultureFragment;


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
public class CellFirstBtnFragment extends Fragment {
    @BindView(R.id.cell_1st_a)
    EditText cell_ed_a;
    @BindView(R.id.cell_1st_b)
    EditText cell_ed_b;
    @BindView(R.id.cell_1st_c)
    EditText cell_ed_c;
    @BindView(R.id.cell_1st_d)
    EditText cell_ed_d;

    @BindView(R.id.cell_1st_delete)
    Button del_btn;
    @BindView(R.id.cell_1st_cal)
    Button cal_btn;
    @BindView(R.id.sol_c)
    TextView cell_sol_c;
    @BindView(R.id.sol_d)
    TextView cell_sol_d;
    @BindView(R.id.sol_b)
    TextView cell_sol_b;

    @BindView(R.id.sol_show)
    LinearLayout cell_sol_show;
    @BindView(R.id.cell_1stbtn_title)
    TextView cell_1stbtn_title;
    @BindView(R.id.cell_1st_cells_ml)
    TextView cell_1st_cells_ml;
    @BindView(R.id.cell_1st_cells)
    TextView cell_1st_cells;
    @BindView(R.id.cell_1st_media)
    TextView cell_1st_media;
    @BindView(R.id.cell_1st_wells)
    TextView cell_1st_wells;

    @BindView(R.id.cell_1stbtn_amount)
    TextView cell_1stbtn_amount;
    @BindView(R.id.cell_1stbtn_sol_F)
    TextView cell_1stbtn_sol_F;
    @BindView(R.id.cell_1stbtn_sol_E)
    TextView cell_1stbtn_sol_E;
    @BindView(R.id.cell_1stbtn_sol_D)
    TextView cell_1stbtn_sol_D;
    @BindView(R.id.cell_1stbtn_sol_C)
    TextView cell_1stbtn_sol_C;
    @BindView(R.id.cell_1stbtn_sol_B)
    TextView cell_1stbtn_sol_B;
    @BindView(R.id.cell_1stbtn_sol_A)
    TextView cell_1stbtn_sol_A;
    @BindView(R.id.cell_1stbtn_sol)
    TextView cell_1stbtn_sol;

    static String v_unit;
    static double a, b, c, d,e, cal;
    SharedPreferences pref;

    public CellFirstBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_cell_1st, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            cell_1stbtn_title.setText(getString(R.string.cell_1stbtn_title_kor));
            cell_1st_cells_ml.setText(getString(R.string.cell_1stbtn_weight_kor));
            cell_1st_cells.setText(getString(R.string.cell_1stbtn_concen_kor));
            cell_1st_media.setText(getString(R.string.cell_1stbtn_media_kor));
            cell_1st_wells.setText(getString(R.string.cell_1stbtn_wells_kor));
            cell_1stbtn_amount.setText(getString(R.string.cell_1stbtn_amount_kor));
            cell_1stbtn_sol_F.setText(getString(R.string.cell_1stbtn_sol_F_kor));
            cell_1stbtn_sol_E.setText(getString(R.string.cell_1stbtn_sol_E_kor));
            cell_1stbtn_sol_D.setText(getString(R.string.cell_1stbtn_sol_D_kor));
            cell_1stbtn_sol_C.setText(getString(R.string.cell_1stbtn_sol_C_kor));
            cell_1stbtn_sol_B.setText(getString(R.string.cell_1stbtn_sol_B_kor));
            cell_1stbtn_sol_A.setText(getString(R.string.cell_1stbtn_sol_A_kor));
            cell_1stbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.cell_1stbtn_alldel_kor));
            cal_btn.setText(getString(R.string.cell_1stbtn_cal_kor));
        } else {
            //영어버전
        }

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cell_ed_a.getText().toString().equals("") || cell_ed_b.getText().toString().equals("") || cell_ed_c.getText().toString().equals("") || cell_ed_d.getText().toString().equals("")) {
                    makeText(getContext(), "Input value!!", Toast.LENGTH_SHORT).show();
                } else {
                    a = Double.parseDouble(cell_ed_a.getText().toString());
                    b = Double.parseDouble(cell_ed_b.getText().toString());
                    c = Double.parseDouble(cell_ed_c.getText().toString());
                    d = Double.parseDouble(cell_ed_d.getText().toString());

                    e = cellFirstCal(a, b, c, d);
/*
                    if (v_unit.equals("uM")) {
                        w_unit = "uL";
                    } else if (v_unit.equals("mM")) {
                        w_unit = "mL";
                    }
  */
                    cal = c - d;
                    cell_sol_d.setText(" "+ a + " ");
                    cell_sol_c.setText(" "+ d + v_unit);
                    cell_sol_b.setText(" "+ cal + v_unit);

                    cell_sol_show.setVisibility(View.VISIBLE);
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cell_ed_a.setText("");
                cell_ed_b.setText("");
                cell_ed_c.setText("");
                cell_ed_d.setText("");
                cell_sol_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double cellFirstCal(double a, double b, double c, double d) {
        if (a == 0 || b == 0 || c == 0 || d == 0) return 0.0;
        return c*(d+1)-e;
    }
}
