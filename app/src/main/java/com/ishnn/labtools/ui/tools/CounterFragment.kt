package com.ishnn.labtools.ui.tools

import android.content.Context
import android.os.*
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ishnn.labtools.R
import kotlinx.android.synthetic.main.fragment_tools_counter.*
import kotlinx.android.synthetic.main.fragment_tools_stopwatch.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.DecimalFormat

class CounterFragment : Fragment() {
    lateinit var vibrator:Vibrator

    var count = 0
    var area = 0
    var array = arrayOf(0,0,0,0,0)

    lateinit var arrayText:Array<TextView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        return inflater.inflate(R.layout.fragment_tools_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayText = arrayOf(counter_text_area0, counter_text_area1, counter_text_area2, counter_text_area3, counter_text_area4)

        counter_button_counter.setOnClickListener {
            if(counter_switcher.isChecked){
                counter_button_counter.text = (--count).toString()
            }else{
                counter_button_counter.text = (++count).toString()
            }
            if(Build.VERSION.SDK_INT >= 26){
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            }
            else{
                vibrator.vibrate(10)
            }
        }
        counter_button_input.setOnClickListener {
            if(area < array.size){
                array[area] = count
                arrayText[area].text = count.toString()

                area++
                counter_average.text = getAverage().toString()
                count = 0
            }
        }
        counter_button_reset.setOnClickListener {
            array = arrayOf(0,0,0,0,0)
            for(v in arrayText){
                v.text = 0.toString()
            }
            counter_button_counter.text = 0.toString()
            counter_average.text = 0.toString()
            area = 0
            count = 0
        }
    }

    private fun getAverage():Float{
        var sum = 0
        for(i in array){
            sum += i
        }
        return sum/(area.toFloat())
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}