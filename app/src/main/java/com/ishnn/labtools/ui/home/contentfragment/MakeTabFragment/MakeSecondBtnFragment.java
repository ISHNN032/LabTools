package com.ishnn.labtools.ui.home.contentfragment.MakeTabFragment;


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

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeSecondBtnFragment extends Fragment {

    @BindView(R.id.make_2nd_b)
    EditText ed_b;
    @BindView(R.id.make_2nd_c)
    EditText ed_c;
    @BindView(R.id.make_2nd_d)
    TextView ed_d;
    /*
    @BindView(R.id.make_2nd_water_unit)
    Spinner water_unit;
    */

    @BindView(R.id.make_2nd_volume_unit)
    Spinner volume_unit;
    @BindView(R.id.make_2nd_cal_unit)
    TextView cal_unit;
    @BindView(R.id.make_2nd_delete)
    Button del_btn;
    @BindView(R.id.make_2nd_cal)
    Button cal_btn;
    @BindView(R.id.sol_b)
    TextView sol_b;
    @BindView(R.id.sol_c)
    TextView sol_c;
    @BindView(R.id.sol_d)
    TextView sol_d;
    @BindView(R.id.sol_show)
    LinearLayout sol_show;

    @BindView(R.id.make_2ndbtn_title)
    TextView make_2ndbtn_title;

    @BindView(R.id.make_2ndbtn_concen)
    TextView make_2ndbtn_concen;
    @BindView(R.id.make_2ndbtn_volume)
    TextView make_2ndbtn_volume;
    @BindView(R.id.make_2ndbtn_amount)
    TextView make_2ndbtn_amount;
    @BindView(R.id.make_2ndbtn_sol_D)
    TextView make_2ndbtn_sol_D;
    @BindView(R.id.make_2ndbtn_sol_C)
    TextView make_2ndbtn_sol_C;
    @BindView(R.id.make_2ndbtn_sol_B)
    TextView make_2ndbtn_sol_B;
    @BindView(R.id.make_2ndbtn_sol)
    TextView make_2ndbtn_sol;

    static String w_unit, v_unit, c_unit;
    double a,b,c,d;
    SharedPreferences pref;

    public MakeSecondBtnFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_make_2nd, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47,85,151)));

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            make_2ndbtn_title.setText(getString(R.string.make_2ndbtn_title_kor));
            make_2ndbtn_concen.setText(getString(R.string.make_2ndbtn_concen_kor));
            make_2ndbtn_volume.setText(getString(R.string.make_2ndbtn_volume_kor));
            make_2ndbtn_amount.setText(getString(R.string.make_2ndbtn_amount_kor));
            make_2ndbtn_sol_D.setText(getString(R.string.make_2ndbtn_sol_D_kor));
            make_2ndbtn_sol_C.setText(getString(R.string.make_2ndbtn_sol_C_kor));
            make_2ndbtn_sol_B.setText(getString(R.string.make_2ndbtn_sol_B_kor));
            make_2ndbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.make_2ndbtn_alldel_kor));
            cal_btn.setText(getString(R.string.make_2ndbtn_cal_kor));
        } else{
            //영어버전
        }
/*
        water_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                w_unit = parent.getItemAtPosition(position).toString();         }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
*/
        volume_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                v_unit = parent.getItemAtPosition(position).toString();         }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ed_b.getText().toString().equals("") || ed_c.getText().toString().equals("")){
                    Toast.makeText(getContext(),"값을 입력하세요", Toast.LENGTH_SHORT).show();
                } else{

                    b = Double.parseDouble(ed_b.getText().toString());
                    c = Double.parseDouble(ed_c.getText().toString());
                    d = makeSecondCal(b,c,w_unit,v_unit);

                    if(d < 0.001) {
                        d = d*1000;
                        ed_d.setText(d+"");
                        cal_unit.setText("mg");
                        c_unit = "mg";
                    } else {
                        ed_d.setText(d+"");
                        cal_unit.setText("g");
                        c_unit = "g";
                    }

                    sol_show.setVisibility(View.VISIBLE);

                    sol_b.setText(b+ "% (w/v)" +"");
                    sol_c.setText(c+v_unit+"");
                    sol_d.setText(d+c_unit+"");
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_b.setText("");
                ed_c.setText("");
                ed_d.setText("");
                cal_unit.setText("");
                sol_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double makeSecondCal(double b, double c, String w_unit, String v_unit){

        if(v_unit.equals("mL")){
            c = c*0.001;
        }
        else if(v_unit.equals("uL")){
            c = c*0.000001;
        }
        else if(v_unit.equals("L")){
            c = c*1;
        }
        return (b*c)/100;
    }

}