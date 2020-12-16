package com.ishnn.labtools.ui.tools.timer

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import com.rnnzzo.uxdesign.model.TimerItem
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.DecimalFormat

val TYPE_HEADER = 1
val TYPE_ITEM = 2
val TYPE_LOADER = 3

class TimerItemAdapter(
    var items: MutableList<TimerItem>,
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
                    holder.hour = item.hour
                    holder.min = item.min
                    holder.sec = item.sec
                    holder.setTime()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var hour = 0
        var min = 0
        var sec = 0

        var lTotal: Long = 0
        private val tvTime: TextView
        private val btStart: ImageButton
        private val btPause: ImageButton
        private val btStop: ImageButton
        private val btDelete: ImageButton
        private val originBackGround: Drawable

        init {
            with(item) {
                tvTime = findViewById(R.id.iv_timer_time)
                btStart = findViewById(R.id.timer_btn_start)
                btPause = findViewById(R.id.timer_btn_pause)
                btStop = findViewById(R.id.timer_btn_stop)
                btDelete = findViewById(R.id.timer_btn_delete)
                originBackGround = this.background

                btStart.setOnClickListener {
                    startStopwatch()
                }
                btPause.setOnClickListener {
                    pauseStopwatch()
                }
                btStop.setOnClickListener {
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

        fun setTime(){
            lTotal = (hour * 60 * 60 * 1000) + (min * 60 * 1000) + (sec * 1000).toLong()
            val df = DecimalFormat("00.00");
            val formatted = df.format(sec);
            tvTime.text = String.format("%02d:%02d:%s", hour, min, formatted)
            itemView.background = originBackGround
        }

        private var TimerScope: CoroutineScope? = null
        var lElapsed = 0L
        private fun startStopwatch() {
            val start = SystemClock.elapsedRealtime() - lElapsed
            TimerScope = CoroutineScope(Dispatchers.Main)
            TimerScope!!.launch {
                try {
                    while (isActive) {
                        val elapsed = SystemClock.elapsedRealtime() - start
                        lElapsed = elapsed

                        val left = lTotal - elapsed
                        if(left < 0){
                            stopWatchEnded()
                            break
                        }

                        val leftHour = left / (60 * 60 * 1000)
                        val leftMin = left  % (60 * 60 * 1000) / (60 * 1000)
                        val leftSec = left % (60 * 60 * 1000) % (60 * 1000) / 1000f

                        delay(1)
                        val df = DecimalFormat("00.00");
                        val formatted = df.format(leftSec);
                        tvTime.text = String.format("%02d:%02d:%s", leftHour, leftMin, formatted)
                    }
                } catch (e: Exception) {
                    Log.e("e", e.toString())
                }
            }
        }

        private fun pauseStopwatch() {
            TimerScope?.cancel()
        }

        private fun stopStopwatch() {
            TimerScope?.cancel()
            lElapsed = 0L
            setTime()
        }

        @SuppressLint("SetTextI18n")
        private fun stopWatchEnded(){
            pauseStopwatch()
            tvTime.text = "00:00:00.00"
            itemView.setBackgroundColor(Color.RED)
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

    fun addItem(extraItem: TimerItem, pos: Int) {
        items.add(pos, extraItem)
        notifyItemInserted(pos)
    }

    fun addData(extraItems: List<TimerItem>) {
        items.addAll(extraItems)
        notifyDataSetChanged()
    }

    fun removeLoader() {
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

}