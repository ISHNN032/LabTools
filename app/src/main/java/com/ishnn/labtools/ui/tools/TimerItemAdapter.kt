package com.ishnn.labtools.ui.tools

import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.R
import com.ishnn.labtools.util.adapter.OnMoveAndSwipedListener
import com.rnnzzo.uxdesign.model.RvItem
import kotlinx.android.synthetic.main.fragment_tools_stopwatch.*
import kotlinx.android.synthetic.main.item_timer.view.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.DecimalFormat
import java.util.*

val TYPE_HEADER = 1
val TYPE_ITEM = 2
val TYPE_LOADER = 3

class TimerItemAdapter(
    var items: MutableList<RvItem>,
    val clickListener: TimerFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_ITEM -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_timer, parent, false)
                return ViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_loader, parent, false)
                return LoadingViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (item.type) {
            TYPE_ITEM -> {
                with(holder as ViewHolder) {
                    //Put your logic here
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val tvTime: TextView
        private var lTotal: Long
        private val btStart: ImageButton
        private val btPause: ImageButton
        private val btStop: ImageButton
        private val btDelete: ImageButton

        init {
            with(item) {
                tvTime = findViewById(R.id.iv_timer_time)
                btStart = findViewById(R.id.timer_btn_start)
                btPause = findViewById(R.id.timer_btn_pause)
                btStop = findViewById(R.id.timer_btn_stop)
                btDelete = findViewById(R.id.timer_btn_delete)
                lTotal = 3 * 60 * 1000

                val totalMin = lTotal / (60 * 1000)
                val totalSec = lTotal % (60 * 1000) / 1000f
                val df = DecimalFormat("00.00");
                val formatted = df.format(totalSec);
                if (tvTime != null) {
                    tvTime.text = String.format("%02d:%s", totalMin, formatted)
                }

                btStart.setOnClickListener {
                    Log.d("btStart", "Clicked")
                    startStopwatch()
                }
                btPause.setOnClickListener {
                    Log.d("btPause", "Clicked")
                    pauseStopwatch()
                }
                btStop.setOnClickListener {
                    Log.d("btStop", "Clicked")
                    stopStopwatch()
                }
                btDelete.setOnClickListener {
                    if(adapterPosition != -1){
                        stopStopwatch()
                        items.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                    }
                }
            }
        }

        private var StopwatchScope: CoroutineScope? = null
        var lElapsed = 0L
        private fun startStopwatch() {
            val start = SystemClock.elapsedRealtime() - lElapsed
            StopwatchScope = CoroutineScope(Dispatchers.Main)
            StopwatchScope!!.launch {
                try {
                    while (isActive) {
                        val elapsed = SystemClock.elapsedRealtime() - start
                        lElapsed = elapsed

                        val left = lTotal - elapsed

                        val leftMin = left / (60 * 1000)
                        val leftSec = left % (60 * 1000) / 1000f

                        delay(1)
                        val df = DecimalFormat("00.00");
                        val formatted = df.format(leftSec);

                        if (tvTime != null) {
                            tvTime.text = String.format("%02d:%s", leftMin, formatted)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("e", e.toString())
                }
            }
        }

        private fun pauseStopwatch() {
            StopwatchScope?.cancel()
        }

        private fun stopStopwatch() {
            StopwatchScope?.cancel()
            lElapsed = 0L
            tvTime.text = "00:00.00"
        }
    }

    class LoadingViewHolder : RecyclerView.ViewHolder {
        val rvLoader: ProgressBar

        constructor(item: View) : super(item) {
            with(item) {
                rvLoader = findViewById(R.id.rvLoader)
            }
        }
    }

    interface ClickListener {
        fun onClickListener(pos: Int, aView: View)
    }

    fun getItem(pos: Int) = items[pos]

    fun addItem(extraItem: RvItem, pos: Int) {
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun addData(extraItems: List<RvItem>) {
        items.addAll(extraItems)
        notifyDataSetChanged()
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

}