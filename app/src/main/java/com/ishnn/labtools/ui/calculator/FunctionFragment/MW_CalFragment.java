package com.ishnn.labtools.ui.calculator.FunctionFragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.ishnn.labtools.MainActivity;
import com.ishnn.labtools.R;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MW_CalFragment extends Fragment implements Button.OnClickListener {

    @BindView(R.id.text_element)
    TextView text_element;
    @BindView(R.id.text_molecular)
    TextView text_molecular;

    @BindView(R.id.mw_del_btn)
    Button mw_del_btn;
    @BindView(R.id.mw_delall_btn)
    Button mw_delall_btn;
    @BindView(R.id.mw_calculator)
    Button mw_calculator;
    @BindView(R.id.mw_copy)
    Button mw_copy;

    @BindView(R.id.mw_0)
    Button mw_0;
    @BindView(R.id.mw_1)
    Button mw_1;
    @BindView(R.id.mw_2)
    Button mw_2;
    @BindView(R.id.mw_3)
    Button mw_3;
    @BindView(R.id.mw_4)
    Button mw_4;
    @BindView(R.id.mw_5)
    Button mw_5;
    @BindView(R.id.mw_6)
    Button mw_6;
    @BindView(R.id.mw_7)
    Button mw_7;
    @BindView(R.id.mw_8)
    Button mw_8;
    @BindView(R.id.mw_9)
    Button mw_9;
    @BindView(R.id.mw_open)
    Button mw_open;
    @BindView(R.id.mw_close)
    Button mw_close;

    @BindView(R.id.mw_H)
    Button mw_H;
    @BindView(R.id.mw_C)
    Button mw_C;
    @BindView(R.id.mw_O)
    Button mw_O;
    @BindView(R.id.mw_N)
    Button mw_N;
    @BindView(R.id.mw_P)
    Button mw_P;

    @BindView(R.id.mw_Li)
    Button mw_Li;
    @BindView(R.id.mw_Na)
    Button mw_Na;
    @BindView(R.id.mw_K)
    Button mw_K;
    @BindView(R.id.mw_Cs)
    Button mw_Cs;
    @BindView(R.id.mw_Ag)
    Button mw_Ag;

    @BindView(R.id.mw_Ca)
    Button mw_Ca;
    @BindView(R.id.mw_Mg)
    Button mw_Mg;
    @BindView(R.id.mw_Ba)
    Button mw_Ba;
    @BindView(R.id.mw_B)
    Button mw_B;
    @BindView(R.id.mw_Al)
    Button mw_Al;

    @BindView(R.id.mw_Cr)
    Button mw_Cr;
    @BindView(R.id.mw_Mn)
    Button mw_Mn;
    @BindView(R.id.mw_Fe)
    Button mw_Fe;
    @BindView(R.id.mw_Co)
    Button mw_Co;
    @BindView(R.id.mw_Ni)
    Button mw_Ni;

    @BindView(R.id.mw_Cl)
    Button mw_Cl;
    @BindView(R.id.mw_F)
    Button mw_F;
    @BindView(R.id.mw_Br)
    Button mw_Br;
    @BindView(R.id.mw_I)
    Button mw_I;
    @BindView(R.id.mw_S)
    Button mw_S;

    @BindView(R.id.mw_H2O)
    Button mw_H2O;
    @BindView(R.id.mw_CO3)
    Button mw_CO3;
    @BindView(R.id.mw_PO4)
    Button mw_PO4;
    @BindView(R.id.mw_SO4)
    Button mw_SO4;
    @BindView(R.id.mw_NH4)
    Button mw_NH4;

    @BindView(R.id.mw_HPO3)
    Button mw_HPO3;
    @BindView(R.id.mw_SO3)
    Button mw_SO3;
    @BindView(R.id.mw_NO3)
    Button mw_NO3;
    @BindView(R.id.mw_OH)
    Button mw_OH;
    @BindView(R.id.mw_HPO4)
    Button mw_HPO4;

    Stack<String> sik = new Stack<>();
    Deque<String> sik_cal = new ArrayDeque<>();

    public MW_CalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator_content_mw__cal, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 85, 151)));

        mw_del_btn.setOnClickListener(this);
        mw_delall_btn.setOnClickListener(this);
        mw_calculator.setOnClickListener(this);
        mw_copy.setOnClickListener(this);

        mw_0.setOnClickListener(this);
        mw_1.setOnClickListener(this);
        mw_2.setOnClickListener(this);
        mw_3.setOnClickListener(this);
        mw_4.setOnClickListener(this);
        mw_5.setOnClickListener(this);
        mw_6.setOnClickListener(this);
        mw_7.setOnClickListener(this);
        mw_8.setOnClickListener(this);
        mw_9.setOnClickListener(this);
        mw_open.setOnClickListener(this);
        mw_close.setOnClickListener(this);

        mw_H.setOnClickListener(this);
        mw_C.setOnClickListener(this);
        mw_O.setOnClickListener(this);
        mw_N.setOnClickListener(this);
        mw_P.setOnClickListener(this);

        mw_Li.setOnClickListener(this);
        mw_Na.setOnClickListener(this);
        mw_K.setOnClickListener(this);
        mw_Cs.setOnClickListener(this);
        mw_Ag.setOnClickListener(this);

        mw_Ca.setOnClickListener(this);
        mw_Mg.setOnClickListener(this);
        mw_Ba.setOnClickListener(this);
        mw_B.setOnClickListener(this);
        mw_Al.setOnClickListener(this);

        mw_Cr.setOnClickListener(this);
        mw_Mn.setOnClickListener(this);
        mw_Fe.setOnClickListener(this);
        mw_Co.setOnClickListener(this);
        mw_Ni.setOnClickListener(this);

        mw_Cl.setOnClickListener(this);
        mw_F.setOnClickListener(this);
        mw_Br.setOnClickListener(this);
        mw_I.setOnClickListener(this);
        mw_S.setOnClickListener(this);

        mw_H2O.setOnClickListener(this);
        mw_CO3.setOnClickListener(this);
        mw_PO4.setOnClickListener(this);
        mw_SO4.setOnClickListener(this);
        mw_NH4.setOnClickListener(this);

        mw_HPO3.setOnClickListener(this);
        mw_SO3.setOnClickListener(this);
        mw_NO3.setOnClickListener(this);
        mw_OH.setOnClickListener(this);
        mw_HPO4.setOnClickListener(this);

        return view;
    }

    public String showSik(Stack sik) {
        String show = "";
        Iterator<String> itr = sik.iterator();
        while (itr.hasNext()) {
            show += itr.next();
        }
        return show;
    }

    public String deleteSik(Stack<String> sik, Deque<String> sik_cal) {
        String show = "";
        sik.pop();
        sik_cal.pollLast();
        Iterator<String> itr = sik.iterator();
        while (itr.hasNext()) {
            show += itr.next();
        }
        return show;
    }

    public double calSik(Deque<String> sik_cal) {
        Stack<String> stack = new Stack<>();
        Deque<String> de = new ArrayDeque<>();
        double result = 0.0, result1=0.0;
        Iterator<String> itr = sik_cal.iterator();
        while (itr.hasNext()) de.offer(itr.next());

        while (!de.isEmpty()) {
            if (de.peek().equals("(")) {
                de.poll();
                while (!de.peek().equals(")")) {
                    if (de.peek().equals("1.0") || de.peek().equals("2.0") || de.peek().equals("3.0") || de.peek().equals("4.0") || de.peek().equals("5.0") ||
                            de.peek().equals("6.0") || de.peek().equals("7.0") || de.peek().equals("8.0") || de.peek().equals("9.0") || de.peek().equals("0.0")) {
                        stack.push(String.valueOf(Double.parseDouble(stack.pop()) * Double.parseDouble(de.poll())));
                    } else {
                        stack.push(de.poll());
                    }
                }
                de.poll();
            } else {
                if (de.peek().equals("1.0") || de.peek().equals("2.0") || de.peek().equals("3.0") || de.peek().equals("4.0") || de.peek().equals("5.0") ||
                        de.peek().equals("6.0") || de.peek().equals("7.0") || de.peek().equals("8.0") || de.peek().equals("9.0") || de.peek().equals("0.0")) {
                    while (!stack.isEmpty()) result1 += Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(result1 * Double.parseDouble(de.poll())));
                    result1 = 0;
                } else {
                    result += Double.parseDouble(de.poll());
                }
            }
        }

        while(!stack.isEmpty()) result += Double.parseDouble(stack.pop());

        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mw_del_btn:
                if (text_element.getText().equals("")) {
                    Toast.makeText(getContext(), "값이 없습니다", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    text_element.setText(deleteSik(sik, sik_cal));
                    break;
                }
            case R.id.mw_delall_btn:
                sik.clear();
                sik_cal.clear();
                text_element.setText(showSik(sik));
                text_molecular.setText("0.0000");
                break;
            case R.id.mw_calculator:
                text_molecular.setText(String.format("%.4f", calSik(sik_cal)));
                break;
            case R.id.mw_copy:
                Toast.makeText(getContext(), "값 복사", Toast.LENGTH_SHORT).show();
                break;

            case R.id.mw_0:
                sik_cal.add("0.0");
                sik.push("0");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_1:
                sik_cal.add("1.0");
                sik.push("1");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_2:
                sik_cal.offer("2.0");
                sik.push("2");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_3:
                sik_cal.offer("3.0");
                sik.push("3");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_4:
                sik_cal.offer("4.0");
                sik.push("4");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_5:
                sik_cal.offer("5.0");
                sik.push("5");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_6:
                sik_cal.offer("6.0");
                sik.push("6");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_7:
                sik_cal.offer("7.0");
                sik.push("7");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_8:
                sik_cal.offer("8.0");
                sik.push("8");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_9:
                sik_cal.offer("9.0");
                sik.push("9");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_open:
                sik_cal.offer("(");
                sik.push("(");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_close:
                sik_cal.offer(")");
                sik.push(")");
                text_element.setText(showSik(sik));
                break;

            case R.id.mw_H:
                sik_cal.offer("1.0079");
                sik.push("H");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_C:
                sik_cal.offer("12.0107");
                sik.push("C");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_O:
                sik_cal.offer("15.994");
                sik.push("O");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_N:
                sik_cal.offer("14.0067");
                sik.push("N");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_P:
                sik_cal.offer("30.9738");
                sik.push("P");
                text_element.setText(showSik(sik));
                break;

            case R.id.mw_Li:
                sik_cal.offer("6.9410");
                sik.push("Li");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Na:
                sik_cal.offer("22.9898");
                sik.push("Na");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_K:
                sik_cal.offer("30.0983");
                sik.push("K");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Cs:
                sik_cal.offer("132.9055");
                sik.push("Cs");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Ag:
                sik_cal.offer("107.8682");
                sik.push("Ag");
                text_element.setText(showSik(sik));
                break;

            case R.id.mw_Ca:
                sik_cal.offer("40.078");
                sik.push("Ca");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Mg:
                sik_cal.offer("24.305");
                sik.push("Mg");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Ba:
                sik_cal.offer("137.327");
                sik.push("Ba");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_B:
                sik_cal.offer("10.811");
                sik.push("B");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Al:
                sik_cal.offer("26.9815");
                sik.push("Al");
                text_element.setText(showSik(sik));
                break;

            case R.id.mw_Cr:
                sik_cal.offer("51.9961");
                sik.push("Cr");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Mn:
                sik_cal.offer("54.938");
                sik.push("Mn");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Fe:
                sik_cal.offer("55.845");
                sik.push("Fe");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Co:
                sik_cal.offer("58.933");
                sik.push("Co");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Ni:
                sik_cal.offer("58.6934");
                sik.push("Ni");
                text_element.setText(showSik(sik));
                break;

            case R.id.mw_Cl:
                sik_cal.offer("35.453");
                sik.push("Cl");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_F:
                sik_cal.offer("18.9984");
                sik.push("F");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_Br:
                sik_cal.offer("79.904");
                sik.push("Br");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_I:
                sik_cal.offer("126.9045");
                sik.push("I");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_S:
                sik_cal.offer("32.065");
                sik.push("S");
                text_element.setText(showSik(sik));
                break;

            case R.id.mw_H2O:
                sik_cal.offer("18.0152");
                sik.push("H20");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_CO3:
                sik_cal.offer("60.0089");
                sik.push("CO3");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_PO4:
                sik_cal.offer("94.9714");
                sik.push("PO4");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_SO4:
                sik_cal.offer("96.0626");
                sik.push("SO4");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_NH4:
                sik_cal.offer("18.0383");
                sik.push("NH4");
                text_element.setText(showSik(sik));
                break;

            case R.id.mw_HPO3:
                sik_cal.offer("79.9799");
                sik.push("HPO3");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_SO3:
                sik_cal.offer("80.0632");
                sik.push("SO3");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_NO3:
                sik_cal.offer("62.0049");
                sik.push("NO3");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_OH:
                sik_cal.offer("17.0073");
                sik.push("OH");
                text_element.setText(showSik(sik));
                break;
            case R.id.mw_HPO4:
                sik_cal.offer("95.9793");
                sik.push("HPO4");
                text_element.setText(showSik(sik));
                break;
            default:
                break;
        }
    }
}
