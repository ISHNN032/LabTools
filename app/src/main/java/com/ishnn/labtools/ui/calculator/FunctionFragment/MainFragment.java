package com.ishnn.labtools.ui.calculator.FunctionFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @BindView(R.id.make)
    LinearLayout make;
    @BindView(R.id.dilution)
    LinearLayout dilution;
    @BindView(R.id.mw_cal)
    LinearLayout mw_cal;
    @BindView(R.id.cell_culture)
    LinearLayout cell_culture;
    @BindView(R.id.buffer)
    LinearLayout buffer;
    @BindView(R.id.unit_converter)
    LinearLayout unit_converter;
    @BindView(R.id.pcr_fold)
    LinearLayout pcr_fold;
    @BindView(R.id.gel_cal)
    LinearLayout gel_cal;
    //@BindView(R.id.q_and_a)
    //LinearLayout q_and_a;

    @BindView(R.id.main_make_text)
    TextView main_make_text;
    @BindView(R.id.main_dilution_text)
    TextView main_dilution_text;
    @BindView(R.id.main_mw_text)
    TextView main_mw_text;
    @BindView(R.id.main_cell_text)
    TextView main_cell_text;
    @BindView(R.id.main_buffer_text)
    TextView main_buffer_text;
    @BindView(R.id.main_unit_converter_text)
    TextView main_unit_converter_text;
    @BindView(R.id.main_pcr_text)
    TextView main_pcr_text;
    @BindView(R.id.main_sds_text)
    TextView main_sds_text;
//    @BindView(R.id.main_qa_text)
//    TextView main_qa_text;

    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_main, container, false);
        ButterKnife.bind(this, view);
        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        main_make_text.setText(getString(R.string.main_fragment_make_text_kor));
        main_dilution_text.setText(getString(R.string.main_fragment_dil_text_kor));
        main_mw_text.setText(getString(R.string.main_fragment_mw_text_kor));
        main_cell_text.setText(getString(R.string.main_fragment_cell_text_kor));
        main_buffer_text.setText(getString(R.string.main_fragment_buffer_text_kor));
        main_unit_converter_text.setText(getString(R.string.main_fragment_unit_converter_text_kor));
        main_pcr_text.setText(getString(R.string.main_fragment_protein_text_kor));
        main_sds_text.setText(getString(R.string.main_fragment_dnarna_text_kor));
//            main_qa_text.setText(getString(R.string.main_fragment_qa_text_kor));

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MakeFragment());
            }
        });
        dilution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DilutionFragment());
            }
        });
        mw_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MW_CalFragment());
            }
        });
        cell_culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Cell_CultureFragment());
            }
        });
        buffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BufferFragment());
            }
        });
        unit_converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new UnitConverterFragment());
            }
        });
        pcr_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProteinFragment());
            }
        });
        gel_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DnarnaFragment());
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.calculator_content, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
