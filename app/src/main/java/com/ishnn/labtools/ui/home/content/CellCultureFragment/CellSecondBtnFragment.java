package com.ishnn.labtools.ui.home.content.CellCultureFragment;


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

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CellSecondBtnFragment extends Fragment {

    @BindView(R.id.cell_2nd_a)
    EditText cell_ed_a;
    @BindView(R.id.cell_2nd_b)
    EditText cell_ed_b;
    @BindView(R.id.cell_2nd_c)
    EditText cell_ed_c;
    @BindView(R.id.cell_2nd_d)
    EditText cell_ed_d;

    @BindView(R.id.cell_2nd_delete)
    Button del_btn;
    @BindView(R.id.cell_2nd_cal)
    Button cal_btn;
    @BindView(R.id.sol_c)
    TextView cell_sol_c;
    @BindView(R.id.sol_d)
    TextView cell_sol_d;
    @BindView(R.id.sol_e)
    TextView cell_sol_e;
    @BindView(R.id.sol_f)
    TextView cell_sol_f;

    @BindView(R.id.sol_show)
    LinearLayout cell_sol_show;
    @BindView(R.id.cell_2ndbtn_title)
    TextView cell_2ndbtn_title;
    @BindView(R.id.cell_2nd_cells_ml)
    TextView cell_2nd_cells_ml;
    @BindView(R.id.cell_2nd_cells)
    TextView cell_2nd_cells;
    @BindView(R.id.cell_2nd_media)
    TextView cell_2nd_media;
    @BindView(R.id.cell_2nd_wells)
    TextView cell_2nd_wells;

    @BindView(R.id.cell_2ndbtn_amount)
    TextView cell_2ndbtn_amount;
    @BindView(R.id.cell_2ndbtn_sol_F)
    TextView cell_2ndbtn_sol_F;
    @BindView(R.id.cell_2ndbtn_sol_E)
    TextView cell_2ndbtn_sol_E;
    @BindView(R.id.cell_2ndbtn_sol_D)
    TextView cell_2ndbtn_sol_D;
    @BindView(R.id.cell_2ndbtn_sol_C)
    TextView cell_2ndbtn_sol_C;
    @BindView(R.id.cell_2ndbtn_sol)
    TextView cell_2ndbtn_sol;
    @BindView(R.id.cell_2nd_stock_unit)
    Spinner stock_unit;
    @BindView(R.id.cell_2nd_target_unit)
    Spinner target_unit;

    static String s_unit, t_unit;
    static double a, b, c, d, e, cal;
    SharedPreferences pref;

    public CellSecondBtnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_content_cell_2nd, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            cell_2ndbtn_title.setText(getString(R.string.cell_2ndbtn_title_kor));
            cell_2nd_cells_ml.setText(getString(R.string.cell_2ndbtn_weight_kor));
            cell_2nd_cells.setText(getString(R.string.cell_2ndbtn_concen_kor));
            cell_2nd_media.setText(getString(R.string.cell_2ndbtn_media_kor));
            cell_2nd_wells.setText(getString(R.string.cell_2ndbtn_wells_kor));
            cell_2ndbtn_amount.setText(getString(R.string.cell_2ndbtn_amount_kor));
            cell_2ndbtn_sol_F.setText(getString(R.string.cell_2ndbtn_sol_F_kor));
            cell_2ndbtn_sol_E.setText(getString(R.string.cell_2ndbtn_sol_E_kor));
            cell_2ndbtn_sol_D.setText(getString(R.string.cell_2ndbtn_sol_D_kor));
            cell_2ndbtn_sol_C.setText(getString(R.string.cell_2ndbtn_sol_C_kor));
            cell_2ndbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.cell_2ndbtn_alldel_kor));
            cal_btn.setText(getString(R.string.cell_2ndbtn_cal_kor));
        } else {
            //영어버전
        }

        stock_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_unit = parent.getItemAtPosition(position).toString();         }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        target_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t_unit = parent.getItemAtPosition(position).toString();         }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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

                    e = cellSecondCal(a, b, c, d);
/*
                    if (v_unit.equals("uM")) {
                        w_unit = "uL";
                    } else if (v_unit.equals("mM")) {
                        w_unit = "mL";
                    }
  */
                    cal = c*(d+1) - e;

                    cell_sol_f.setText(" " + e + t_unit);
                    cell_sol_e.setText(" " + cal + s_unit);
                    cell_sol_d.setText(" " + c + s_unit);


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

    public double cellSecondCal(double a, double b, double c, double d) {
        if (a == 0.0 || b == 0.0 || c == 0.0 || d == 0.0) return 0.0;

        if(s_unit.equals("mM")) {
            a = a*0.001;
        } else if(s_unit.equals("uM")) {
            a = a*0.000001;
        }

        if(t_unit.equals("mM")) {
            b = b*0.001;
        } else if(t_unit.equals("uM")) {
            b = b*0.000001;
        }
        return ((b/a) * c*(d+1));
    }
}
