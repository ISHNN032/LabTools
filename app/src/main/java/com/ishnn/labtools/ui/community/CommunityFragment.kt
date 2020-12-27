package com.ishnn.labtools.ui.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ishnn.labtools.Global
import com.ishnn.labtools.R
import com.ishnn.labtools.util.IOnBackPressed

/**
 * A placeholder fragment containing a simple view.
 */
class CommunityFragment : Fragment(), IOnBackPressed {
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mPosts: Posts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_community, container, false)
        mTabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        mViewPager = root.findViewById(R.id.viewPager)
        mPosts = Posts()
        return root
    }

    override fun onBackPressed(): Boolean {
        Log.e("a", "b")
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTabs()
    }

    private fun setTabs() {
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.addTab(mTabLayout.newTab())
        mTabLayout.addTab(mTabLayout.newTab())
        mTabLayout.addTab(mTabLayout.newTab())
        mViewPager.adapter = FabAdapter(childFragmentManager, mTabLayout.tabCount)
    }

    fun getPosts():Posts{
        return mPosts
    }

    inner class FabAdapter(fm: FragmentManager?, var tabCount: Int) :
        FragmentStatePagerAdapter(fm!!) {
        private val pageTitles = arrayOf("전체글", "즐겨찾기", "전체공지")

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    PostFragment()
                }
                1 -> {
                    PostFragment()
                }
                2 -> {
                    PostFragment()
                }
                else -> PostFragment()
            }
        }

        override fun getCount(): Int {
            return tabCount
        }

        override fun getPageTitle(position: Int): CharSequence = pageTitles[position]
    }
}