package com.ishnn.labtools.ui.calculator.FunctionFragment.DnarnaTabFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DnarnaFifthBtnFragment extends Fragment {
    @BindView(R.id.dnarna_5th_table)
    ImageView dnarna_5th_table;

    SharedPreferences pref;
    private static Context mContext;

    // 스케일제스쳐 디텍터 인스턴스
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            // ScaleGestureDetector에서 factor를 받아 변수로 선언한 factor에 넣고
            mScaleFactor *= scaleGestureDetector.getScaleFactor();

            // 최대 10배, 최소 10배 줌 한계 설정
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));

            // 이미지뷰 스케일에 적용
            dnarna_5th_table.setScaleX(mScaleFactor);
            dnarna_5th_table.setScaleY(mScaleFactor);
            return true;
        }
    }

    public DnarnaFifthBtnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_dnarna_5th, container, false);
        ButterKnife.bind(this, view);

        mContext = getActivity();

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 85, 151)));

        pref = this.getActivity().getSharedPreferences("lan", Context.MODE_PRIVATE);
        if (pref.getString("lan", "").equals("kor")) {

        } else {
            //영어버전
        }

        // 스케일제스쳐 디텍터 인스턴스

        mScaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleListener());
        return view;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        //변수로 선언해 놓은 ScaleGestureDetector
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
