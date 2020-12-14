package com.ishnn.labtools.ui.home.contentfragment.MakeTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class MakeFirstBtnFragment extends Fragment {

    @BindView(R.id.make_1st_a)
    EditText ed_a;
    @BindView(R.id.make_1st_b)
    EditText ed_b;
    @BindView(R.id.make_1st_c)
    EditText ed_c;
    @BindView(R.id.make_1st_d)
    TextView ed_d;
    @BindView(R.id.make_fisrt_water_unit)
    Spinner water_unit;
    @BindView(R.id.make_1st_volume_unit)
    Spinner volume_unit;
    @BindView(R.id.make_1st_cal_unit)
    TextView cal_unit;
    @BindView(R.id.make_1st_delete)
    Button del_btn;
    @BindView(R.id.make_1st_cal)
    Button cal_btn;
    /*
    @BindView(R.id.sol_b)
    TextView sol_b;
    @BindView(R.id.sol_c)
    TextView sol_c;
    */
    @BindView(R.id.sol_d)
    TextView sol_d;
    @BindView(R.id.sol_show)
    LinearLayout sol_show;

    @BindView(R.id.make_1stbtn_title)
    TextView make_1stbtn_title;
    @BindView(R.id.make_1stbtn_weight)
    TextView make_1stbtn_weight;
    @BindView(R.id.make_1stbtn_concen)
    TextView make_1stbtn_concen;
    @BindView(R.id.make_1stbtn_volume)
    TextView make_1stbtn_volume;
    @BindView(R.id.make_1stbtn_amount)
    TextView make_1stbtn_amount;
    /*
    @BindView(R.id.make_1stbtn_sol_D)
    TextView make_1stbtn_sol_D;
    @BindView(R.id.make_1stbtn_sol_C)
    TextView make_1stbtn_sol_C;
    @BindView(R.id.make_1stbtn_sol_B)
    TextView make_1stbtn_sol_B;
    */
    @BindView(R.id.make_1stbtn_sol)
    TextView make_1stbtn_sol;

    static String b_w_unit, v_unit, c_unit;
    double a,b,c,d;
    SharedPreferences pref;

    public MakeFirstBtnFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_make_1st, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47,85,151)));

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            make_1stbtn_title.setText(getString(R.string.make_1stbtn_title_kor));
            make_1stbtn_weight.setText(getString(R.string.make_1stbtn_weight_kor));
            make_1stbtn_concen.setText(getString(R.string.make_1stbtn_concen_kor));
            make_1stbtn_volume.setText(getString(R.string.make_1stbtn_volume_kor));
            make_1stbtn_amount.setText(getString(R.string.make_1stbtn_amount_kor));
            /*
            make_1stbtn_sol_D.setText(getString(R.string.make_1stbtn_sol_D_kor));
            make_1stbtn_sol_C.setText(getString(R.string.make_1stbtn_sol_C_kor));
            make_1stbtn_sol_B.setText(getString(R.string.make_1stbtn_sol_B_kor));
            */
            make_1stbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.make_1stbtn_alldel_kor));
            cal_btn.setText(getString(R.string.make_1stbtn_cal_kor));
        } else{
            //영어버전
        }

        water_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b_w_unit = parent.getItemAtPosition(position).toString();         }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
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

                if(ed_a.getText().toString().equals("") || ed_b.getText().toString().equals("") || ed_c.getText().toString().equals("")){
                    Toast.makeText(getContext(),"값을 입력하세요", Toast.LENGTH_SHORT).show();
                } else{
                    a = Double.parseDouble(ed_a.getText().toString());
                    b = Double.parseDouble(ed_b.getText().toString());
                    c = Double.parseDouble(ed_c.getText().toString());

                    d = makeFirstCal(a,b,c,b_w_unit,v_unit);
                    d /= 1000000.0f;
                    d *= 1000000.0f;
                    Log.d("LEO","makeFirstCal:d= " + d);

                    if(d < 0.001f) {
                        d = d*1000000f;
                        ed_d.setText(d+"");
                        cal_unit.setText("ug");
                        c_unit = "ug";
                    }else if(d < 0) {
                        d = d*1000f;
                        ed_d.setText(d+"");
                        cal_unit.setText("mg");
                        c_unit = "mg";
                    } else {
                        ed_d.setText(d+"");
                        cal_unit.setText("g");
                        c_unit = "g";
                    }

                    sol_show.setVisibility(View.VISIBLE);
                    /*
                    make_1stbtn_sol_D.setText(getString(R.string.make_1stbtn_sol_D_kor));
                    make_1stbtn_sol_C.setText(getString(R.string.make_1stbtn_sol_C_kor));
                    make_1stbtn_sol_B.setText(getString(R.string.make_1stbtn_sol_B_kor));

                    sol_b.setText(b+b_w_unit+"");
                    sol_c.setText(c+v_unit+"");
                    */
                    sol_d.setText(d+b_w_unit+getString(R.string.make_1stbtn_sol_D_kor)+c+v_unit+getString(R.string.make_1stbtn_sol_C_kor)+b+c_unit+getString(R.string.make_1stbtn_sol_B_kor));
                }
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_a.setText("");
                ed_b.setText("");
                ed_c.setText("");
                ed_d.setText("");
                cal_unit.setText("");
                sol_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double makeFirstCal(double a, double b, double c, String b_w_unit, String v_unit){

        a = a*1000000.0f;
        a = a/1000000.0f;
        if(b_w_unit.equals("mM")) {
            b = b*0.001f;
        } else if(b_w_unit.equals("uM")) {
            b = b*0.000001f;
        }

        if(v_unit.equals("mL")){
            c = c*0.001f;
        } else if(v_unit.equals("uL")){
            c = c*0.000001f;
        }
        Log.d("LEO","makeFirstCal:a= " +a+" b= "+ b + "c= " + c + "= " + (a*b*c));
        return a*b*c;
    }

}
