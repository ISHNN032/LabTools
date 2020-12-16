package com.ishnn.labtools.ui.tools.timer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.fragment.app.FragmentManager


class NumberPickerDialog : androidx.fragment.app.DialogFragment(), OnValueChangeListener {
    var valueChangeListener: OnValueChangeListener? = null
    var title //dialog 제목
            : String? = null
    var subtitle //dialog 부제목
            : String? = null
    var minvalue //입력가능 최소값
            = 0
    var maxvalue //입력가능 최대값
            = 0
    var step //선택가능 값들의 간격
            = 0
    var defvalue //dialog 시작 숫자 (현재값)
            = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(activity)

        //Dialog 시작 시 bundle로 전달된 값을 받아온다
        title = requireArguments().getString("title")
        subtitle = requireArguments().getString("subtitle")
        minvalue = requireArguments().getInt("minvalue")
        maxvalue = requireArguments().getInt("maxvalue")
        step = requireArguments().getInt("step")
        defvalue = requireArguments().getInt("defvalue")

        //최소값과 최대값 사이의 값들 중에서 일정한 step사이즈에 맞는 값들을 배열로 만든다.
        val myValues = getArrayWithSteps(minvalue, maxvalue, step)

        //displayedvalues를 사용하지 않고 min/max 값을 설정해서 선택을 받을 때는 선택한 보여지는 값이 바로 리턴되는 값이다.
        //하지만 이런경우에는 보여지는 값은 최소값과 최대값사이에 값이 1씩 증가되는 값들이 모두 보여지게 된다. (별도의 step을 줄 수 없다)
        //일정한 간격의 숫자만을 선택할 수 있게 하려면, String 배열에 display가 되는 값들을 입력해서 setDisplayedValues함수로 입력해줘야 하며
        //이런경우 그 숫자가 리턴값이 아닌 배열의 인덱스가 리턴값이 된다. 따라서 minValue와 maxvalue는 이에 맞게 설정해 주어야 한다.
        numberPicker.minValue = 0
        numberPicker.maxValue = (maxvalue - minvalue) / step
        numberPicker.displayedValues = myValues

        //현재값 설정 (dialog를 실행했을 때 시작지점)
        numberPicker.value = (defvalue - minvalue) / step
        //키보드 입력을 방지
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        val builder = AlertDialog.Builder(activity)
        //제목 설정
        builder.setTitle(title)
        //부제목 설정
        builder.setMessage(subtitle)

        //Ok button을 눌렀을 때 동작 설정
        builder.setPositiveButton(
            "OK"
        ) { dialog, which -> //dialog를 종료하면서 값이 변했다는 함수는 onValuechange함수를 실행시킨다.
            //실제 구현에서는 이 클레스의 함수를 재 정의해서 동작을 수행한다.
            valueChangeListener!!.onValueChange(
                numberPicker,
                numberPicker.value, numberPicker.value
            )
        }

        //취소 버튼을 눌렀을 때 동작 설정
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, which -> }

        builder.setView(numberPicker)
        //number picker 실행
        return builder.create()
    }

    //최소값부터 최대값가지 일정 간격의 값을 String 배열로 출력
    fun getArrayWithSteps(min: Int, max: Int, step: Int): Array<String?> {
        val number_of_array = (max - min) / step + 1
        val result = arrayOfNulls<String>(number_of_array)
        for (i in 0 until number_of_array) {
            result[i] = (min + step * i).toString()
        }
        return result
    }

    fun showNumberPicker(
        fragmentManager: FragmentManager,
        title: String?,
        subtitle: String?,
        maxvalue: Int,
        minvalue: Int,
        step: Int,
        defvalue: Int
    ) {
        val newFragment = NumberPickerDialog()

        //Dialog에는 bundle을 이용해서 파라미터를 전달한다
        val bundle = Bundle(6) // 파라미터는 전달할 데이터 개수
        bundle.putString("title", title) // key , value
        bundle.putString("subtitle", subtitle) // key , value
        bundle.putInt("maxvalue", maxvalue) // key , value
        bundle.putInt("minvalue", minvalue) // key , value
        bundle.putInt("step", step) // key , value
        bundle.putInt("defvalue", defvalue) // key , value
        newFragment.arguments = bundle
        //class 자신을 Listener로 설정한다
        newFragment.valueChangeListener = this
        newFragment.show(fragmentManager, "number picker")
    }

    override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {

    }
}