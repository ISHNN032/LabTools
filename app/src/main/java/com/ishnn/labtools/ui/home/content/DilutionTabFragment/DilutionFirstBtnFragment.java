package com.ishnn.labtools.ui.home.content.DilutionTabFragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.makeText;

public class DilutionFirstBtnFragment extends Fragment {

    @BindView(R.id.dilution_1st_a)
    EditText dilution_ed_a;
    @BindView(R.id.dilution_1st_b)
    EditText dilution_ed_b;
    @BindView(R.id.dilution_1st_c)
    EditText dilution_ed_c;
    //@BindView(R.id.dilution_1st_d)
    //TextView dilution_ed_d;
    @BindView(R.id.dilution_1st_weight_unit)
    Spinner weight_unit_dilution;
    @BindView(R.id.dilution_1st_water_unit)
    Spinner water_unit_dilution;
    @BindView(R.id.dilution_1st_volume_unit)
    Spinner volume_unit_dilution;
    @BindView(R.id.dilution_1st_delete)
    Button del_btn;
    @BindView(R.id.dilution_1st_cal)
    Button cal_btn;
    @BindView(R.id.sol_b)
    TextView dilution_sol_b;
    @BindView(R.id.sol_c_d)
    TextView dilution_sol_c_d;
    @BindView(R.id.sol_d)
    TextView dilution_sol_d;
    @BindView(R.id.sol_c)
    TextView dilution_sol_c;
    @BindView(R.id.sol_show)
    LinearLayout dilution_sol_show;

    @BindView(R.id.dilution_1stbtn_title)
    TextView dilution_1stbtn_title;
    @BindView(R.id.dilution_1stbtn_weight)
    TextView dilution_1stbtn_weight;
    @BindView(R.id.dilution_1stbtn_water)
    TextView dilution_1stbtn_water;
    @BindView(R.id.dilution_1stbtn_volume)
    TextView dilution_1stbtn_volume;
    @BindView(R.id.dilution_1stbtn_amount)
    TextView dilution_1stbtn_amount;
    @BindView(R.id.dilution_1stbtn_sol_D)
    TextView dilution_1stbtn_sol_D;
    @BindView(R.id.dilution_1stbtn_sol_C)
    TextView dilution_1stbtn_sol_C;
    @BindView(R.id.dilution_1stbtn_sol_B)
    TextView dilution_1stbtn_sol_B;
    @BindView(R.id.dilution_1stbtn_sol_A1)
    TextView dilution_1stbtn_sol_A1;
    @BindView(R.id.dilution_1stbtn_sol_A2)
    TextView dilution_1stbtn_sol_A2;
    @BindView(R.id.dilution_1stbtn_sol)
    TextView dilution_1stbtn_sol;

    static String b_w_unit, c_v_unit, a_we_unit;
    static double a, b, c, d,cal;
    SharedPreferences pref;

    public DilutionFirstBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_dilution_1st, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {
            dilution_1stbtn_title.setText(getString(R.string.dilution_1stbtn_title_kor));
            dilution_1stbtn_weight.setText(getString(R.string.dilution_1stbtn_weight_kor));
            dilution_1stbtn_water.setText(getString(R.string.dilution_1stbtn_concen_kor));
            dilution_1stbtn_volume.setText(getString(R.string.dilution_1stbtn_volume_kor));
            dilution_1stbtn_amount.setText(getString(R.string.dilution_1stbtn_amount_kor));
            dilution_1stbtn_sol_D.setText(getString(R.string.dilution_1stbtn_sol_D_kor));
            dilution_1stbtn_sol_C.setText(getString(R.string.dilution_1stbtn_sol_C_kor));
            dilution_1stbtn_sol_B.setText(getString(R.string.dilution_1stbtn_sol_B_kor));
            dilution_1stbtn_sol_A1.setText(getString(R.string.dilution_1stbtn_sol_A1_kor));
            dilution_1stbtn_sol_A2.setText(getString(R.string.dilution_1stbtn_sol_A2_kor));
            dilution_1stbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.dilution_1stbtn_alldel_kor));
            cal_btn.setText(getString(R.string.dilution_1stbtn_cal_kor));
        } else {
            //영어버전
        }
        weight_unit_dilution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                a_we_unit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        water_unit_dilution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b_w_unit = parent.getItemAtPosition(position).toString();
                if (a_we_unit.equals("mM")) {
                    if(b_w_unit.equals("M")) {
                        //Toast toast = makeText(this, "낮은 단위를 선택하세요", Toast.LENGTH_SHORT);
                        //toast.show();
                    }
                }
                else if (a_we_unit.equals("uM")) {
                    if(b_w_unit.equals("M") || b_w_unit.equals("mM")) {
                        //Toast toast = makeText(this, "낮은 단위를 선택하세요", Toast.LENGTH_SHORT);
                        //toast.show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        volume_unit_dilution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c_v_unit = parent.getItemAtPosition(position).toString();
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

                    d = dilutionFirstCal(a, b, c, a_we_unit, b_w_unit, c_v_unit);

                    if (c_v_unit.equals("M")) {
                        d = d * 1000000;
                    } else if (c_v_unit.equals("mM")) {
                        d = d * 1000;
                    } else
                    {

                    }
                    /*
                        if (d < 0.001 && d >= 0.0000001) {
                        d = d * 1000;
                        //dilution_ed_d.setText(d + "");
                    } else if (d < 0.0000001 && d != 0) {
                        d = d * 1000000;
                        //dilution_ed_d.setText(d + "");
                    } else if (d == 0) {
                        //dilution_ed_d.setText(d + "");
                    } else {
                        //dilution_ed_d.setText(d + "");
                    }
                    */
                    cal = c-d;
                    Log.d("LEO", String.valueOf(cal));
                    dilution_sol_d.setText(" " +d + c_v_unit + " ");
                    dilution_sol_c_d.setText(" " +cal + a_we_unit + " ");
                    dilution_sol_b.setText(" " +b + b_w_unit + " ");
                    dilution_sol_c.setText(" " +c + c_v_unit + " ");
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

    public double dilutionFirstCal(double a, double b, double c, String a_we_unit, String b_w_unit, String c_v_unit) {
        if (a == 0) return 0;

        if (a_we_unit.equals("mM")) {
            a = a * 0.001;
        } else if (a_we_unit.equals("uM")) {
            a = a * 0.000001;
        }

        if (b_w_unit.equals("mM")) {
            b = b * 0.001;
        } else if (b_w_unit.equals("uM")) {
            b = b * 0.000001;
        }

        if (c_v_unit.equals("mL")) {
            c = c * 0.001;
        } else if (c_v_unit.equals("uL")) {
            c = c * 0.000001;
        }
        return b * c / a;
    }
}
