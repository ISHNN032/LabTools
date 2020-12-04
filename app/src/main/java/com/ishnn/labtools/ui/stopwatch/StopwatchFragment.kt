package com.ishnn.labtools.ui.stopwatch

import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ishnn.labtools.R

class StopwatchFragment : Fragment() {

    private lateinit var stopwatchViewModel: StopwatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        stopwatchViewModel =
                ViewModelProviders.of(this).get(StopwatchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        val textView: TextView = root.findViewById(R.id.text_stopwatch)
        stopwatchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val seekBarProgress: SeekBar
        val seekBarThickness: SeekBar
        seekBarProgress = root.findViewById<View>(R.id.seekBar_progress) as SeekBar
        seekBarThickness = root.findViewById<View>(R.id.seekBar_thickness) as SeekBar
        val circleProgressBar =
            root.findViewById<View>(R.id.custom_progressBar) as CircleProgressBar
        //Using ColorPickerLibrary to pick color for our CustomProgressbar
        seekBarProgress.progress = circleProgressBar.progress.toInt()
        seekBarProgress.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) circleProgressBar.setProgressWithAnimation(i.toFloat()) else circleProgressBar.progress =
                    i.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        seekBarThickness.progress = circleProgressBar.strokeWidth.toInt()
        seekBarThickness.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                circleProgressBar.strokeWidth = i.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        return root
    }
}