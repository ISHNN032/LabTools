package com.ishnn.labtools.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ishnn.labtools.R
import com.ishnn.labtools.util.adapter.*
import com.rnnzzo.uxdesign.model.RvItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class NotificationFragment : Fragment(){
    private val adapter by lazy { NotificationItemAdapter(ArrayList(), this) }
    private var headerCount = 0
    private var isLoading = false

    private lateinit var mSwipe: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notification, container, false)
        mSwipe = root.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        mSwipe!!.setOnRefreshListener {
            Log.d("refresh", "refreshed")
            GlobalScope.launch {
                delay(1000)
                mSwipe!!.isRefreshing = false
            }
        }
        mRecyclerView = root.findViewById(R.id.recyclerView)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addData()
    }

    private fun initRecyclerView() {
        mRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = linearLayoutManager

        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(adapter)
        val mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(mRecyclerView)

        mRecyclerView.addOnScrollListener(rvScrollListener)
    }

    fun addData() {
        val data: MutableList<RvItem> = ArrayList()
        headerCount++
        data.add(RvItem("2020년 12월 ${headerCount}일", TYPE_HEADER))
        for (i in 1..15) {
            data.add(RvItem("${i}", TYPE_ITEM))
        }
        //ADD LOADER
        data.add(RvItem("", TYPE_LOADER))
        adapter.addData(data)
    }

    private val rvScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            if (!isLoading && linearLayoutManager!!.itemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                loadData()
                isLoading = true
            }
        }
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                adapter.removeLoader()
                addData()
                isLoading = false
            }
        }
    }
}