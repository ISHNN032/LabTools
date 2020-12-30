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
import com.ishnn.labtools.R
import com.ishnn.labtools.ui.community.post.PostFragment
import com.ishnn.labtools.ui.community.post.PostManager
import com.ishnn.labtools.util.IOnBackPressed
import kotlinx.android.synthetic.main.fragment_community.*

/**
 * A placeholder fragment containing a simple view.
 */
class CommunityFragment : Fragment(), IOnBackPressed {
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_community, container, false)
        mTabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        mViewPager = root.findViewById(R.id.viewPager)
        return root
    }

    override fun onBackPressed(): Boolean {
        Log.e("a", "b")
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        community_button_post.setOnClickListener {
            PostManager.addPost()
        }
        setTabs()
    }

    private fun setTabs() {
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.addTab(mTabLayout.newTab().setTag("all"))
        mTabLayout.addTab(mTabLayout.newTab().setTag("favorite"))
        mTabLayout.addTab(mTabLayout.newTab().setTag("notice"))
        mTabLayout.addTab(mTabLayout.newTab().setTag("cafe"))
        mViewPager.adapter = FabAdapter(childFragmentManager, mTabLayout.tabCount)
    }

    fun getCurrentTab():String{
        return mTabLayout.getTabAt(mTabLayout.selectedTabPosition)!!.tag.toString()
    }

    inner class FabAdapter(fm: FragmentManager?, private var tabCount: Int) :
        FragmentStatePagerAdapter(fm!!) {
        private val pageTitles = arrayOf("전체글", "즐겨찾기", "전체공지", "카페")

        override fun getItem(position: Int): Fragment {
            val fragment:Fragment =
            when (position) {
                0 -> {
                    PostFragment()
                }
                1 -> {
                    PostFragment()
                }
                2 -> {
                    PostFragment()
                }
                3 -> {
                    WebViewFragment()
                }
                else -> PostFragment()
            }
            fragment.arguments = Bundle().apply { putString("Tag", pageTitles[position])}
            return fragment
        }

        override fun getCount(): Int {
            return tabCount
        }

        override fun getPageTitle(position: Int): CharSequence = pageTitles[position]
    }
}