package com.ishnn.labtools.ui.home.content.ProteinTabFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ProteinFirstBtnFragment extends Fragment {

    @BindView(R.id.edit_b)
    EditText edit_b;
    @BindView(R.id.text_1)
    TextView text_1;
    @BindView(R.id.text_2)
    TextView text_2;
    @BindView(R.id.text_3)
    TextView text_3;
    @BindView(R.id.text_4)
    TextView text_4;
    @BindView(R.id.text_5)
    TextView text_5;
    @BindView(R.id.text_6)
    TextView text_6;

    @BindView(R.id.gel_mainview)
    LinearLayout gel_mainview;

    @BindView(R.id.edit_A)
    EditText edit_A;
    @BindView(R.id.edit_P)
    EditText edit_P;

    @BindView(R.id.textview2_1)
    TextView textview2_1;
    @BindView(R.id.textview2_2)
    TextView textview2_2;
    @BindView(R.id.textview2_3)
    TextView textview2_3;
    @BindView(R.id.textview2_4)
    TextView textview2_4;
    @BindView(R.id.textview2_5)
    TextView textview2_5;
    @BindView(R.id.textview2_6)
    TextView textview2_6;

    @BindView(R.id.gel_fragment_stacking_title) AutofitTextView gel_fragment_stacking_title;
    @BindView(R.id.gel_fragment_stacking_concentration)
    TextView gel_fragment_stacking_concentration;
    @BindView(R.id.gel_fragment_stacking_makevolume)
    TextView gel_fragment_stacking_makevolume;
    @BindView(R.id.gel_fragment_stacking_fur)
    TextView gel_fragment_stacking_fur;
    @BindView(R.id.gel_fragment_stacking_volume)
    TextView gel_fragment_stacking_volume;
    @BindView(R.id.gel_fragment_resolving_title) AutofitTextView gel_fragment_resolving_title;
    @BindView(R.id.gel_fragment_resolving_concentration)
    TextView gel_fragment_resolving_concentration;
    @BindView(R.id.gel_fragment_resolving_makevolume)
    TextView gel_fragment_resolving_makevolume;
    @BindView(R.id.gel_fragment_resolving_fur)
    TextView gel_fragment_resolving_fur;
    @BindView(R.id.gel_fragment_resolving_volume)
    TextView gel_fragment_resolving_volume;

    public ProteinFirstBtnFragment() {   }

    double value1;
    double value2;
    double value3;
    double value4;
    double value5;
    double value6;

    double value2_1, value2_2, value2_3, value2_4, value2_5, value2_6;
    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator_content_gel_cal, container, false);
        ButterKnife.bind(this, view);



        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if(pref.getString("lan","").equals("kor")){
            gel_fragment_stacking_title.setText(getString(R.string.gel_fragment_stacking_title_kor));
            gel_fragment_stacking_concentration.setText(getString(R.string.gel_fragment_stacking_concentration_kor));
            gel_fragment_stacking_makevolume.setText(getString(R.string.gel_fragment_stacking_makevolume_kor));
            gel_fragment_stacking_fur.setText(getString(R.string.gel_fragment_stacking_fur_kor));
            gel_fragment_stacking_volume.setText(getString(R.string.gel_fragment_stacking_volume_kor));
            gel_fragment_resolving_title.setText(getString(R.string.gel_fragment_resolving_title_kor));
            gel_fragment_resolving_concentration.setText(getString(R.string.gel_fragment_resolving_concentration_kor));
            gel_fragment_resolving_makevolume.setText(getString(R.string.gel_fragment_resolving_makevolume_kor));
            gel_fragment_resolving_fur.setText(getString(R.string.gel_fragment_resolving_fur_kor));
            gel_fragment_resolving_volume.setText(getString(R.string.gel_fragment_resolving_volume_kor));
            edit_b.setHint(getString(R.string.input_kor)); edit_A.setHint(getString(R.string.input_kor)); edit_P.setHint(getString(R.string.input_kor));
        }else {
            //영어버전
        }

        edit_b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = s.toString();
                if (text.length() == 0) {
                    text_1.setText(null);
                    text_2.setText(null);
                    text_3.setText(null);
                    text_4.setText(null);
                    text_5.setText(null);
                    text_6.setText(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (edit_b.getText().toString().length() != 0) {
                    value1 = (Double.parseDouble(edit_b.getText().toString()) * 0.17);
                    value2 = (Double.parseDouble(edit_b.getText().toString()) * 0.125);
                    value3 = (Double.parseDouble(edit_b.getText().toString()) * 0.01);
                    value4 = (Double.parseDouble(edit_b.getText().toString()) * 0.68);
                    value5 = (Double.parseDouble(edit_b.getText().toString()) * 0.01);
                    value6 = (Double.parseDouble(edit_b.getText().toString()) * 0.001);

                    text_1.setText(String.valueOf(value1));
                    text_2.setText(String.valueOf(value2));
                    text_3.setText(String.valueOf(value3));
                    text_4.setText(String.valueOf(value4));
                    text_5.setText(String.valueOf(value5));
                    text_6.setText(String.valueOf(value6));
                }

                if (text.length() == 0) {
                    text_1.setText(null);
                    text_2.setText(null);
                    text_3.setText(null);
                    text_4.setText(null);
                    text_5.setText(null);
                    text_6.setText(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length() == 0) {
                    text_1.setText(null);
                    text_2.setText(null);
                    text_3.setText(null);
                    text_4.setText(null);
                    text_5.setText(null);
                    text_6.setText(null);
                }

            }
        });

        edit_A.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = s.toString();
                if (text.length() == 0) {
                    textview2_2.setText(null);
                    textview2_3.setText(null);
                    textview2_5.setText(null);
                    textview2_6.setText(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (edit_A.getText().toString().length() != 0) {
                    value2_2 = Double.parseDouble(edit_A.getText().toString()) * 0.25;
                    value2_3 = Double.parseDouble(edit_A.getText().toString()) * 0.01;
                    value2_5 = Double.parseDouble(edit_A.getText().toString()) * 0.01;
                    value2_6 = Double.parseDouble(edit_A.getText().toString()) * 0.004;

                    textview2_2.setText(String.valueOf(value2_2));
                    textview2_3.setText(String.valueOf(value2_3));
                    textview2_5.setText(String.valueOf(value2_5));
                    textview2_6.setText(String.valueOf(value2_6));

                    if (edit_P.getText().toString().length() != 0) {
                        value2_1 = (Double.parseDouble(edit_A.getText().toString()) * Double.parseDouble(edit_P.getText().toString())) / 30;
                        value2_4 = Double.parseDouble(edit_A.getText().toString()) * (0.73 - Double.parseDouble(edit_P.getText().toString()) / 30);

                        textview2_1.setText(String.valueOf(value2_1));
                        textview2_4.setText(String.valueOf(value2_4));
                    }

                }

                if (text.length() == 0) {
                    textview2_1.setText(null);
                    textview2_2.setText(null);
                    textview2_3.setText(null);
                    textview2_4.setText(null);
                    textview2_5.setText(null);
                    textview2_6.setText(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length() == 0) {
                    textview2_1.setText(null);
                    textview2_2.setText(null);
                    textview2_3.setText(null);
                    textview2_4.setText(null);
                    textview2_5.setText(null);
                    textview2_6.setText(null);
                }

            }
        });
        return view;
    }

    @OnClick(R.id.gel_mainview)
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(gel_mainview.getWindowToken(), 0);
    }

}
