package com.ishnn.labtools.ui.home.contentfragment.CellCultureFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ishnn.labtools.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CellFourthBtnFragment extends Fragment {

    @BindView(R.id.sol_c)
    TextView cell_sol_c;

    public CellFourthBtnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator_content_cell_4th, container, false);
    }

}
