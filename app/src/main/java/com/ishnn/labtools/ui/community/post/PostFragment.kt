package com.ishnn.labtools.ui.community.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ishnn.labtools.R
import com.ishnn.labtools.ui.community.CommunityFragment
import com.ishnn.labtools.util.IOnBackPressed
import kotlinx.android.synthetic.main.fragment_post.*
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
                adapter.refreshData(null)
                mSwipe.isRefreshing = false
            }
        }
        val searchButton = root.findViewById<ImageButton>(R.id.post_search_button)
        val searchBar = root.findViewById<EditText>(R.id.post_search_edit)
        searchButton.setOnClickListener {
            adapter.refreshData(searchBar.text.toString())
        }
        initRecyclerView()
        adapter.refreshData(null)
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
                adapter.refreshData(null)
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