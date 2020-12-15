package com.ishnn.labtools.ui.tools

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ishnn.labtools.R
import com.ishnn.labtools.util.IOnBackPressed
import com.ishnn.labtools.util.adapter.ItemTouchHelperCallback
import com.rnnzzo.uxdesign.model.RvItem
import kotlinx.coroutines.*


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
            val data: MutableList<RvItem> = ArrayList()
            data.add(RvItem("$", TYPE_ITEM))
            adapter.addData(data)
        }

        val data: MutableList<RvItem> = ArrayList()
        data.add(RvItem("$", TYPE_ITEM))
        data.add(RvItem("$", TYPE_ITEM))
        adapter.addData(data)

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
}