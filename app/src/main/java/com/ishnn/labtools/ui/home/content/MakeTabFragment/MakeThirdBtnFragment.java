package com.ishnn.labtools.ui.home.content.MakeTabFragment;


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

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeThirdBtnFragment extends Fragment {

    @BindView(R.id.make_3rd_a)
    EditText ed_a;
    @BindView(R.id.make_3rd_b)
    EditText ed_b;
    @BindView(R.id.make_3rd_c)
    EditText ed_c;
    @BindView(R.id.make_3rd_d)
    EditText ed_d;
    @BindView(R.id.make_3rd_e)
    EditText ed_e;

   // @BindView(R.id.make_3rd_cal_unit)
   // TextView cal_unit;
    @BindView(R.id.make_3rd_delete)
   Button del_btn;
    @BindView(R.id.make_3rd_cal)
    Button cal_btn;

    @BindView(R.id.sol_a)
    TextView sol_a;
    @BindView(R.id.sol_b)
    TextView sol_b;
    @BindView(R.id.sol_d)
    TextView sol_d;
    @BindView(R.id.sol_f)
    TextView sol_f;
    @BindView(R.id.sol_show)
    LinearLayout sol_show;

    @BindView(R.id.make_3rdbtn_title)
    TextView make_3rdbtn_title;
    @BindView(R.id.make_3rdbtn_weight)
    TextView make_3rdbtn_weight;
    @BindView(R.id.make_3rdbtn_concen)
    TextView make_3rdbtn_concen;
    @BindView(R.id.make_3rdbtn_volume)
    TextView make_3rdbtn_volume;
    @BindView(R.id.make_3rdbtn_solution_concen)
    TextView make_3rdbtn_solution_concen;
    @BindView(R.id.make_3rdbtn_solution_density)
    TextView make_3rdbtn_solution_density;
    @BindView(R.id.make_3rdbtn_amount)
    TextView make_3rdbtn_amount;
    @BindView(R.id.make_3rdbtn_sol_F)
    TextView make_3rdbtn_sol_F;
    @BindView(R.id.make_3rdbtn_sol_D)
    TextView make_3rdbtn_sol_D;
    @BindView(R.id.make_3rdbtn_sol_B)
    TextView make_3rdbtn_sol_B;
    @BindView(R.id.make_3rdbtn_sol_A)
    TextView make_3rdbtn_sol_A;
    @BindView(R.id.make_3rdbtn_sol)
    TextView make_3rdbtn_sol;

    static String d_unit, f_unit, b_unit;
    double a,b,c,d,e,f,f1;
    SharedPreferences pref;

    public MakeThirdBtnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_make_3rd, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47,85,151)));

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            make_3rdbtn_title.setText(getString(R.string.make_3rdbtn_title_kor));
            make_3rdbtn_weight.setText(getString(R.string.make_3rdbtn_weight_kor));
            make_3rdbtn_concen.setText(getString(R.string.make_3rdbtn_concen_kor));
            make_3rdbtn_volume.setText(getString(R.string.make_3rdbtn_volume_kor));
            make_3rdbtn_solution_concen.setText(getString(R.string.make_3rdbtn_solution_concen_kor));
            make_3rdbtn_solution_density.setText(getString(R.string.make_3rdbtn_solution_density_kor));

            make_3rdbtn_amount.setText(getString(R.string.make_3rdbtn_amount_kor));
            make_3rdbtn_sol_D.setText(getString(R.string.make_3rdbtn_sol_D_kor));
            make_3rdbtn_sol_F.setText(getString(R.string.make_3rdbtn_sol_F_kor));
            make_3rdbtn_sol_B.setText(getString(R.string.make_3rdbtn_sol_B_kor));
            make_3rdbtn_sol_A.setText(getString(R.string.make_3rdbtn_sol_A_kor));
            make_3rdbtn_sol.setText(getString(R.string.sol_kor));
            del_btn.setText(getString(R.string.make_3rdbtn_alldel_kor));
            cal_btn.setText(getString(R.string.make_3rdbtn_cal_kor));
        } else{
            //영어버전
        }

        cal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ed_a.getText().toString().equals("") || ed_b.getText().toString().equals("") || ed_c.getText().toString().equals("")||ed_d.getText().toString().equals("") ||ed_e.getText().toString().equals("") ){
                    Toast.makeText(getContext(),"값을 입력하세요", Toast.LENGTH_SHORT).show();
                } else{
                    a = Double.parseDouble(ed_a.getText().toString());
                    b = Double.parseDouble(ed_b.getText().toString());
                    c = Double.parseDouble(ed_c.getText().toString());
                    d = Double.parseDouble(ed_d.getText().toString());
                    e = Double.parseDouble(ed_e.getText().toString());

                    f = makeFirstCal(a,b,c,d,e);
                    f1 = makeSecondCal(a,b,c,d,e);

                    sol_show.setVisibility(View.VISIBLE);

                    sol_d.setText(d+" ");
                    sol_f.setText(f+" ");
                    sol_b.setText(f1+" ");
                    sol_a.setText(a+" ");
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
                ed_e.setText("");
                //cal_unit.setText("");
                sol_show.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public double makeFirstCal(double a, double b, double c, double d,double e){
        if(c==0 || d==0 || e==0) {
            return 0;
        }

        return (a*(b/c) * (100/d)/e);
    }
    public double makeSecondCal(double a, double b, double c, double d,double e){
        if(c==0 || d==0 || e==0) {
            return 0;
        }
        return (b*1000-f);
    }

}
