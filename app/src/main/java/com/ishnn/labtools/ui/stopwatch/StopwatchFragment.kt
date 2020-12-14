package com.ishnn.labtools.ui.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ishnn.labtools.R
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import kotlinx.coroutines.*
import java.text.DecimalFormat

class StopwatchFragment : Fragment(), View.OnClickListener {
    private lateinit var StopwatchScope: CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        val root = inflater.inflate(R.layout.fragment_stopwatch, container, false)

        val startButton: ImageButton = root.findViewById(R.id.stopwatch_btn_start)
        startButton.setOnClickListener(this)
        val pauseButton: ImageButton = root.findViewById(R.id.stopwatch_btn_pause)
        pauseButton.setOnClickListener(this)
        val addButton: ImageButton = root.findViewById(R.id.stopwatch_btn_add)
        addButton.setOnClickListener(this)
        val resumeButton: ImageButton = root.findViewById(R.id.stopwatch_btn_resume)
        resumeButton.setOnClickListener(this)
        val stopButton: ImageButton = root.findViewById(R.id.stopwatch_btn_stop)
        stopButton.setOnClickListener(this)
        return root
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.action_menu_calculator, menu)
    }

    override fun onClick(view: View?) {
        when(view){
            stopwatch_btn_start -> {
                startStopwatch()
                stopwatch_btn_start.visibility = View.GONE
                stopwatch_btn_pause.visibility = View.VISIBLE
                stopwatch_btn_add.visibility = View.VISIBLE
            }
            stopwatch_btn_add -> {
                stopwatch_log.visibility = View.VISIBLE
            }
            stopwatch_btn_pause -> {
                pauseStopwatch()
                stopwatch_btn_pause.visibility = View.GONE
                stopwatch_btn_add.visibility = View.GONE
                stopwatch_btn_stop.visibility = View.VISIBLE
                stopwatch_btn_resume.visibility = View.VISIBLE
            }
            stopwatch_btn_stop -> {
                stopStopwatch()
                stopwatch_log.visibility = View.GONE
                stopwatch_btn_stop.visibility = View.GONE
                stopwatch_btn_resume.visibility = View.GONE
                stopwatch_btn_start.visibility = View.VISIBLE
            }
            stopwatch_btn_resume -> {
                startStopwatch()
                stopwatch_btn_resume.visibility = View.GONE
                stopwatch_btn_stop.visibility = View.GONE
                stopwatch_btn_pause.visibility = View.VISIBLE
                stopwatch_btn_add.visibility = View.VISIBLE
            }
        }
    }

    var lElapsed = 0L
    private fun startStopwatch(){
        val start = SystemClock.elapsedRealtime() - lElapsed
        StopwatchScope = CoroutineScope(Dispatchers.Main)
        StopwatchScope.launch {
            while (isActive){
                val elapsed = SystemClock.elapsedRealtime() - start
                lElapsed = elapsed

                val elapsedMin = elapsed/(60*1000)
                val elapsedSec = elapsed%(60*1000)/1000f

                delay(1)
                val df = DecimalFormat("00.00");
                val formatted = df.format(elapsedSec);

                stopwatch_time.text = String.format("%02d:%s", elapsedMin, formatted)
                stopwatch_progressBar.progress = elapsedSec
            }
        }
    }

    private fun pauseStopwatch(){
        StopwatchScope.cancel()
    }

    private fun stopStopwatch(){
        StopwatchScope.cancel()
        lElapsed = 0L
        stopwatch_progressBar.setProgressWithAnimation(0f)
        stopwatch_time.text = "00:00.00"
    }
}