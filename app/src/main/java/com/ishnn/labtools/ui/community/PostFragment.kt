package com.ishnn.labtools.ui.community

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
import com.ishnn.labtools.util.IOnBackPressed
import com.ishnn.labtools.util.adapter.ItemTouchHelperCallback
import com.rnnzzo.uxdesign.model.RvItem
import kotlinx.coroutines.*


class PostFragment : Fragment(), IOnBackPressed{
    private val adapter by lazy { PostItemAdapter(ArrayList(), this) }
    private var isLoading = false
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_post, container, false)
        mRecyclerView = root.findViewById(R.id.recyclerView)
        return root
    }

    override fun onBackPressed(): Boolean {
        Log.e("a","b")
        return false
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
    }

    fun addData() {
        val data = (parentFragment as CommunityFragment).getPosts()
        adapter.addData(data)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==android.R.id.home){
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}