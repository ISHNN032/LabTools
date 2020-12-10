package com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class DnarnaThirdBtnFragment extends Fragment {

    @BindView(R.id.edit_A)
    EditText edit_A;
    @BindView(R.id.edit_B)
    EditText edit_B;
    @BindView(R.id.edit_C)
    EditText edit_C;
    @BindView(R.id.edit_D)
    EditText edit_D;
    @BindView(R.id.edit_E)
    EditText edit_E;
    @BindView(R.id.edit_F)
    EditText edit_F;
    @BindView(R.id.edit_G)
    EditText edit_G;
    @BindView(R.id.edit_H)
    EditText edit_H;
    @BindView(R.id.edit_I)
    EditText edit_I;
    @BindView(R.id.edit_J)
    EditText edit_J;
    @BindView(R.id.edit_K)
    EditText edit_K;
    @BindView(R.id.edit_L)
    EditText edit_L;

    @BindView(R.id.pcr_mainview)
    LinearLayout pcr_mainview;

    @BindView(R.id.result_S) AutofitTextView result_S;
    @BindView(R.id.result_T)
    TextView result_T;
    @BindView(R.id.delete_all_btn)
    Button delete_all_btn;
    @BindView(R.id.cal_btn)
    Button cal_btn;
    @BindView(R.id.result_explain) AutofitTextView result_explain;

    @BindView(R.id.pcr_title)
    TextView pcr_title;
    @BindView(R.id.pcr_1ct1_text)
    TextView pcr_1ct1_text;
    @BindView(R.id.pcr_1ct2_text)
    TextView pcr_1ct2_text;
    @BindView(R.id.pcr_1ct3_text)
    TextView pcr_1ct3_text;
    @BindView(R.id.pcr_1target_text) AutofitTextView pcr_1target_text;
    @BindView(R.id.pcr_1house_text) AutofitTextView pcr_1house_text;
    @BindView(R.id.pcr_2ct1_text)
    TextView pcr_2ct1_text;
    @BindView(R.id.pcr_2ct2_text)
    TextView pcr_2ct2_text;
    @BindView(R.id.pcr_2ct3_text)
    TextView pcr_2ct3_text;
    @BindView(R.id.pcr_2target_text) AutofitTextView pcr_2target_text;
    @BindView(R.id.pcr_2house_text) AutofitTextView pcr_2house_text;
    @BindView(R.id.pcr_ctresult) AutofitTextView pcr_ctresult;
    @BindView(R.id.pcr_foldchange)
    TextView pcr_foldchange;
    @BindView(R.id.pcr_sol)
    TextView pcr_sol;

    public DnarnaThirdBtnFragment() { }

    double getedit_a;
    double getedit_b;
    double getedit_c;
    double getedit_d;
    double getedit_e;
    double getedit_f;
    double getedit_g;
    double getedit_h;
    double getedit_i;
    double getedit_j;
    double getedit_k;
    double getedit_l;
    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator_content_pcr__fold, container, false);
        ButterKnife.bind(this,view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            pcr_title.setText(getString(R.string.pcr_title_kor));
            pcr_1ct1_text.setText(getString(R.string.pcr_ct1_text_kor));
            pcr_1ct2_text.setText(getString(R.string.pcr_ct2_text_kor));
            pcr_1ct3_text.setText(getString(R.string.pcr_ct3_text_kor));
            pcr_1target_text.setText(getString(R.string.pcr_target_text_kor));
            pcr_1house_text.setText(getString(R.string.pcr_house_text_kor));
            pcr_2ct1_text.setText(getString(R.string.pcr_ct1_text_kor));
            pcr_2ct2_text.setText(getString(R.string.pcr_ct2_text_kor));
            pcr_2ct3_text.setText(getString(R.string.pcr_ct3_text_kor));
            pcr_2target_text.setText(getString(R.string.pcr_target_text_kor));
            pcr_2house_text.setText(getString(R.string.pcr_house_text_kor));
            pcr_ctresult.setText(getString(R.string.pcr_ctresult_kor));
            pcr_foldchange.setText(getString(R.string.pcr_foldchange_kor));
            pcr_sol.setText(getString(R.string.sol_kor));
            delete_all_btn.setText(getString(R.string.pcr_alldel_kor));
            cal_btn.setText(getString(R.string.pcr_cal_kor));
            edit_A.setHint(getString(R.string.input_kor)); edit_B.setHint(getString(R.string.input_kor));
            edit_C.setHint(getString(R.string.input_kor)); edit_D.setHint(getString(R.string.input_kor));
            edit_E.setHint(getString(R.string.input_kor)); edit_F.setHint(getString(R.string.input_kor));
            edit_G.setHint(getString(R.string.input_kor)); edit_H.setHint(getString(R.string.input_kor));
            edit_I.setHint(getString(R.string.input_kor)); edit_J.setHint(getString(R.string.input_kor));
            edit_K.setHint(getString(R.string.input_kor)); edit_L.setHint(getString(R.string.input_kor));
        } else{
            //영어버전
        }

        moveFocus(edit_A,edit_B);
        moveFocus(edit_B,edit_C);
        moveFocus(edit_C,edit_G);
        moveFocus(edit_G,edit_H);
        moveFocus(edit_H,edit_I);
        moveFocus(edit_I,edit_D);
        moveFocus(edit_D,edit_E);
        moveFocus(edit_E,edit_F);
        moveFocus(edit_F,edit_J);
        moveFocus(edit_J,edit_K);
        moveFocus(edit_K,edit_L);
        endFocus(edit_L);

        return view;
    }



    @OnClick(R.id.cal_btn)
    public void getResult(){
        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(edit_A.getText().toString().length() != 0 && edit_B.getText().toString().length() != 0 && edit_C.getText().toString().length() != 0 && edit_D.getText().toString().length() != 0
                && edit_E.getText().toString().length() != 0 && edit_F.getText().toString().length() != 0 && edit_G.getText().toString().length() != 0 && edit_H.getText().toString().length() != 0
                && edit_I.getText().toString().length() != 0 && edit_J.getText().toString().length() != 0 && edit_K.getText().toString().length() != 0 && edit_L.getText().toString().length() != 0){

            getedit_a = Double.parseDouble(edit_A.getText().toString());
            getedit_b = Double.parseDouble(edit_B.getText().toString());
            getedit_c = Double.parseDouble(edit_C.getText().toString());
            getedit_d = Double.parseDouble(edit_D.getText().toString());
            getedit_e = Double.parseDouble(edit_E.getText().toString());
            getedit_f = Double.parseDouble(edit_F.getText().toString());
            getedit_g = Double.parseDouble(edit_G.getText().toString());
            getedit_h = Double.parseDouble(edit_H.getText().toString());
            getedit_i = Double.parseDouble(edit_I.getText().toString());
            getedit_j = Double.parseDouble(edit_J.getText().toString());
            getedit_k = Double.parseDouble(edit_K.getText().toString());
            getedit_l = Double.parseDouble(edit_L.getText().toString());

            double result_s;
            result_s = (((getedit_d+getedit_e+getedit_f)/3) - ((getedit_j+getedit_k+getedit_l) / 3)) -
                    (((getedit_a+getedit_b+getedit_c)/3) - ((getedit_g+getedit_h+getedit_i)/3));
            result_S.setText(String.valueOf(result_s));

            double result_t;
            result_t = 2 - ((getedit_d+getedit_e+getedit_f)/3) - ((getedit_a+getedit_b+getedit_c)/3) - ((getedit_g+getedit_h+getedit_i)/3);
            result_T.setText(String.valueOf(result_t));

            if(pref.getString("lan","").equals("kor")) {
                result_explain.setText(getString(R.string.pcr_sol_a_kor) + result_t + getString(R.string.pcr_sol_b_kor));
            } else{
                //영어버전
            }
        }else{
            if(pref.getString("lan","").equals("kor")) {
                Toast.makeText(getActivity(), "데이터를 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else{
                //영어버전
            }
        }
    }

    @OnClick(R.id.delete_all_btn)
    public void deleteAll(){
        edit_A.setText(null);
        edit_B.setText(null);
        edit_C.setText(null);
        edit_D.setText(null);
        edit_E.setText(null);
        edit_F.setText(null);
        edit_G.setText(null);
        edit_H.setText(null);
        edit_I.setText(null);
        edit_J.setText(null);
        edit_K.setText(null);
        edit_L.setText(null);
        result_T.setText(null);
        result_S.setText(null);
        result_explain.setText(null);
    }

    @OnClick(R.id.pcr_mainview)
    public void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pcr_mainview.getWindowToken(), 0 );
    }

    private void moveFocus(EditText before_et, final EditText after_et){
        before_et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    after_et.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private void endFocus(final EditText et){
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

}
