package com.ishnn.labtools.ui.community.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ishnn.labtools.R
import com.ishnn.labtools.ui.community.CommunityFragment
import com.ishnn.labtools.util.IOnBackPressed
import kotlinx.coroutines.*


class PostFragment : Fragment(), IOnBackPressed{
    private val adapter by lazy { PostItemAdapter(ArrayList(), this, context) }
    private var isLoading = false
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipe: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Tag", this.tag.toString())

        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_post, container, false)
        mRecyclerView = root.findViewById(R.id.recyclerView)
        mSwipe = root.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        mSwipe.setOnRefreshListener {
            Log.d("refresh", "refreshed")
            GlobalScope.launch {
                adapter.refreshData()
                mSwipe.isRefreshing = false
            }
        }
        initRecyclerView()
        adapter.refreshData()
        return root
    }

    override fun onBackPressed(): Boolean {
        Log.e("a","b")
        return false
    }

    private fun initRecyclerView() {
        mRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = linearLayoutManager
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                adapter.removeLoader()
                adapter.refreshData()
                isLoading = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==android.R.id.home){
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}