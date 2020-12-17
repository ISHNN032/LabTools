//package com.ishnn.labtools.ui.calculator.FunctionFragment;
//
//
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.fragment.app.Fragment;
//
//import com.ishnn.labtools.MainActivity;
//import com.ishnn.labtools.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class CalculatorFragment extends Fragment {
//
//    @BindView(R.id.et_show)
//    EditText et_show;
//    @BindView(R.id.et_result)
//    EditText et_result;
//
//    @BindView(R.id.btn_add)
//    Button add;
//    @BindView(R.id.btn_sub)
//    Button sub;
//    @BindView(R.id.btn_mul)
//    Button mul;
//    @BindView(R.id.btn_div)
//    Button div;
//    @BindView(R.id.btn_remainder)
//    Button remainder;
//    @BindView(R.id.btn_del)
//    Button del;
//    @BindView(R.id.btn_result)
//    Button result;
//    @BindView(R.id.btn_clear)
//    Button clear;
//    @BindView(R.id.btn_plus_minus)
//    Button plus_minus;
//    @BindView(R.id.btn0)
//    Button btn0;
//    @BindView(R.id.btn1)
//    Button btn1;
//    @BindView(R.id.btn2)
//    Button btn2;
//    @BindView(R.id.btn3)
//    Button btn3;
//    @BindView(R.id.btn4)
//    Button btn4;
//    @BindView(R.id.btn5)
//    Button btn5;
//    @BindView(R.id.btn6)
//    Button btn6;
//    @BindView(R.id.btn7)
//    Button btn7;
//    @BindView(R.id.btn8)
//    Button btn8;
//    @BindView(R.id.btn9)
//    Button btn9;
//
//    //이번 연산의 결과를 저장
//    String history = "";
//    //피연산자1
//    String number1 = "";
//    //피연산자2
//    String number2 = "";
//
//    //어떤 연산자가 선택되었는지 확인하기 위한 int형 type변수
//    int type;
//
//    int ADD = 0;
//    int SUB = 1;
//    int MUL = 2;
//    int DIV = 3;
//    int REMAINDER = 4;
//    double d1;
//    double d2;
//
//    public CalculatorFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        ButterKnife.bind(this, view);
//
//        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 85, 151)));
//
//        et_result.setText("");
//
//        add.setOnClickListener(mListener);
//        sub.setOnClickListener(mListener);
//        mul.setOnClickListener(mListener);
//        div.setOnClickListener(mListener);
//        remainder.setOnClickListener(mListener);
//        result.setOnClickListener(mListener);
//        del.setOnClickListener(mListener);
//
//        btn0.setOnClickListener(mListener);
//        btn1.setOnClickListener(mListener);
//        btn2.setOnClickListener(mListener);
//        btn3.setOnClickListener(mListener);
//        btn4.setOnClickListener(mListener);
//        btn5.setOnClickListener(mListener);
//        btn6.setOnClickListener(mListener);
//        btn7.setOnClickListener(mListener);
//        btn8.setOnClickListener(mListener);
//        btn9.setOnClickListener(mListener);
//
//        //초기화
//        //Button clear = findViewById(R.id.btn_clear);
//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                et_show.setText("");
//                et_result.setText("");
//                d1 = d2 = 0;
//                history = number1 = number2 = "";
//            }
//        });
//
//        //Button plus_minus = findViewById(R.id.btn_plus_minus);
//        plus_minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //실수인지 정수인지 판단해서 부호 바꾸기
//                if (((Double.parseDouble(et_result.getText().toString())) - ((int) Double.parseDouble(et_result.getText().toString()))) == 0.0) {
//                    et_result.setText("" + (Integer.parseInt(et_result.getText().toString()) * -1));
//                } else {
//                    et_result.setText("" + (Double.parseDouble(et_result.getText().toString()) * -1));
//                }
//
//            }
//        });
//
//        return view;
//    }
//
//    Button.OnClickListener mListener = new Button.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (et_result.getText().toString() == null) {
//                //Toast.makeText(this, "수를 입력하세요", Toast.LENGTH_SHORT).show();
//                //Toast.makeText(MainActivity.this,"수를 입력하세요",Toast.LENGTH_SHORT).show();
//            }
//            switch (v.getId()) {
//                case R.id.btn_add:
//                    number1 = et_result.getText().toString();
//                    history = et_result.getText().toString() + " + ";
//                    et_show.setText(history);
//                    et_result.setText("");
//
//                    type = ADD;
//                    break;
//
//                case R.id.btn_sub:
//                    number1 = et_result.getText().toString();
//                    history = et_result.getText().toString() + " - ";
//                    et_show.setText(history);
//                    et_result.setText("");
//
//                    type = SUB;
//                    break;
//
//                case R.id.btn_mul:
//                    number1 = et_result.getText().toString();
//                    history = et_result.getText().toString() + " * ";
//                    et_show.setText(history);
//                    et_result.setText("");
//
//                    type = MUL;
//                    break;
//
//                case R.id.btn_div:
//                    number1 = et_result.getText().toString();
//                    history = et_result.getText().toString() + " / ";
//                    et_show.setText(history);
//                    et_result.setText("");
//
//                    type = DIV;
//                    break;
//
//                case R.id.btn_remainder:
//                    number1 = et_result.getText().toString();
//                    history = et_result.getText().toString() + " % ";
//                    et_show.setText(history);
//                    et_result.setText("");
//
//                    type = REMAINDER;
//                    break;
//
//                case R.id.btn_del:
//
//                    String del_number = et_result.getText().toString();
//                    //Toast.makeText(MainActivity.this,del_number,Toast.LENGTH_SHORT).show();
//                    et_result.setText(del_number.substring(0, del_number.length() - 1));
//                    break;
//
//                case R.id.btn_result:
//                    double result = 0;
//                    //Toast.makeText(MainActivity.this, "결과", Toast.LENGTH_SHORT).show();
//                    number2 = et_result.getText().toString();
//                    history = history + et_result.getText().toString();
//                    et_show.setText(history);
//
//                    d1 = Double.parseDouble(number1);
//                    d2 = Double.parseDouble(number2);
//
//                    if (type == ADD) {
//                        result = d1 + d2;
//                        et_result.setText("" + result);
//                    } else if (type == SUB) {
//                        result = d1 - d2;
//                        et_result.setText("" + result);
//                    } else if (type == MUL) {
//                        result = d1 * d2;
//                        et_result.setText("" + result);
//                    } else if (type == DIV) {
//                        result = d1 / d2;
//                        et_result.setText("" + result);
//                    } else if (type == REMAINDER) {
//                        result = d1 % d2;
//                        et_result.setText("" + result);
//                    }
//
//                    number1 = et_result.getText().toString();
//                    break;
//                case R.id.btn0:
//                    et_result.setText(et_result.getText().toString() + 0);
//                    break;
//                case R.id.btn1:
//                    et_result.setText(et_result.getText().toString() + 1);
//                    break;
//                case R.id.btn2:
//                    et_result.setText(et_result.getText().toString() + 2);
//                    break;
//                case R.id.btn3:
//                    et_result.setText(et_result.getText().toString() + 3);
//                    break;
//                case R.id.btn4:
//                    et_result.setText(et_result.getText().toString() + 4);
//                    break;
//                case R.id.btn5:
//                    et_result.setText(et_result.getText().toString() + 5);
//                    break;
//                case R.id.btn6:
//                    et_result.setText(et_result.getText().toString() + 6);
//                    break;
//                case R.id.btn7:
//                    et_result.setText(et_result.getText().toString() + 7);
//                    break;
//                case R.id.btn8:
//                    et_result.setText(et_result.getText().toString() + 8);
//                    break;
//                case R.id.btn9:
//                    et_result.setText(et_result.getText().toString() + 9);
//                    break;
//                case R.id.btndot:
//                    et_result.setText(et_result.getText().toString() + ".");
//                    break;
//
//            }
//        }
//    };
///*
//    public void onClick (View v)
//    {
//        switch(v.getId()){
//            case R.id.btn0 : et_result.setText(et_result.getText().toString() + 0); break;
//            case R.id.btn1 : et_result.setText(et_result.getText().toString() + 1); break;
//            case R.id.btn2 : et_result.setText(et_result.getText().toString() + 2); break;
//            case R.id.btn3 : et_result.setText(et_result.getText().toString() + 3); break;
//            case R.id.btn4 : et_result.setText(et_result.getText().toString() + 4); break;
//            case R.id.btn5 : et_result.setText(et_result.getText().toString() + 5); break;
//            case R.id.btn6 : et_result.setText(et_result.getText().toString() + 6); break;
//            case R.id.btn7 : et_result.setText(et_result.getText().toString() + 7); break;
//            case R.id.btn8 : et_result.setText(et_result.getText().toString() + 8); break;
//            case R.id.btn9 : et_result.setText(et_result.getText().toString() + 9); break;
//            case R.id.btndot : et_result.setText(et_result.getText().toString() + "."); break;
//        }
//    }
//    */
//}
