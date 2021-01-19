package com.ishnn.labtools.ui.tools.timer

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.Global
import com.ishnn.labtools.R
import com.ishnn.labtools.util.IOnBackPressed
import com.rnnzzo.uxdesign.model.TimerItem
import org.json.JSONArray
import org.json.JSONException


class TimerFragment : Fragment(), IOnBackPressed{
    private val adapter by lazy { TimerItemAdapter(ArrayList(), this) }
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mButtonAdd: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_tools_timer, container, false)
        mRecyclerView = root.findViewById(R.id.timerRecyclerView)
        mButtonAdd = root.findViewById(R.id.add_timer_item)
        mButtonAdd.setOnClickListener {
            openAddTimerDialog()
        }

        val data: MutableList<TimerItem> = ArrayList()
        data.add(TimerItem(0, 3, 0))
        data.add(TimerItem(0, 5, 0))
        adapter.addData(data)

        val prefdata = Global.getStringArrayPref(context, "array")?.toList()
        if (prefdata != null) {
            adapter.addData(prefdata)
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==android.R.id.home){
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
        Log.e("a","b")
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = linearLayoutManager

//        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(adapter)
//        val mItemTouchHelper = ItemTouchHelper(callback)
//        mItemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    fun openAddTimerDialog(){
        val dialog = AlertDialog.Builder(requireContext()).create()
        val edialog : LayoutInflater = LayoutInflater.from(context)
        val mView : View = edialog.inflate(R.layout.dialog_addtimer,null)

        val hour : NumberPicker = mView.findViewById(R.id.hour_picker)
        val min : NumberPicker = mView.findViewById(R.id.min_picker)
        val sec : NumberPicker = mView.findViewById(R.id.sec_picker)
        val cancel : Button = mView.findViewById(R.id.cancel_button_addtimer)
        val save : Button = mView.findViewById(R.id.save_button_addtimer)

        //  editText 설정 해제
        hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        min.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        sec.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        //  최소값 설정
        hour.minValue = 0
        min.minValue = 0
        sec.minValue = 1

        //  최대값 설정
        hour.maxValue = 99
        min.maxValue = 59
        sec.maxValue = 59

        //  취소 버튼 클릭 시
        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        //  완료 버튼 클릭 시
        save.setOnClickListener {
            val data: MutableList<TimerItem> = ArrayList()
            data.add(TimerItem(hour.value, min.value, sec.value))
            adapter.addData(data)

            Global.setStringArrayPref(context, "times", adapter.items as ArrayList<TimerItem>)
            dialog.dismiss()
            dialog.cancel()
        }

        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }
}