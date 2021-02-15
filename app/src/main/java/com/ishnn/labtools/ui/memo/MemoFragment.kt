package com.ishnn.labtools.ui.memo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ishnn.labtools.Global
import com.ishnn.labtools.GlobalLogin
import com.ishnn.labtools.R
import com.ishnn.labtools.ui.community.CommunityFragment
import com.ishnn.labtools.util.IOnBackPressed
import com.ishnn.labtools.util.animOptions
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_memo.*
import kotlinx.coroutines.*


class MemoFragment : Fragment(), IOnBackPressed {
    private val adapter by lazy { MemoItemAdapter(ArrayList(), this, context) }
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
        val root = inflater.inflate(R.layout.fragment_memo, container, false)
        mRecyclerView = root.findViewById(R.id.recyclerView)
        initRecyclerView()
        adapter.refreshData()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memo_button_post.setOnClickListener {
            StorageManager.addMemo("제목", "내용",null, context)
//            NavHostFragment.findNavController(this).navigate(
//                R.id.action_nav_commu_to_posting,
//                null,
//                animOptions
//            )
        }
    }

    override fun onBackPressed(): Boolean {
        Log.e("a", "b")
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
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}