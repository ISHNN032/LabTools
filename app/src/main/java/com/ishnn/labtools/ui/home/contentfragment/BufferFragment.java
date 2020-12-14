package com.ishnn.labtools.ui.home.contentfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ishnn.labtools.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BufferFragment extends Fragment {


    public BufferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_buffer, container, false);



        return view;
    }

}
