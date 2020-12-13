package com.ishnn.labtools.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ishnn.labtools.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class CalculatorFragment : Fragment() {
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calculator, container, false)
        mTabLayout = root.findViewById<TabLayout>(R.id.tabLayout)
        mViewPager = root.findViewById(R.id.viewPager)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTabs()
    }

    private fun setTabs(){
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.addTab(mTabLayout.newTab())
        mTabLayout.addTab(mTabLayout.newTab())
        mViewPager.adapter = FabAdapter(childFragmentManager, mTabLayout.tabCount)
    }

    inner class FabAdapter( fm: FragmentManager?, var tabCount: Int) : FragmentStatePagerAdapter(fm!!) {
        private val pageTitles = arrayOf("계산기","수식")

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    CalculatorMainFragment()
                }
                1 -> {
                    CalculatorContentFragment()
                }
                else -> CalculatorMainFragment()
            }
        }
        override fun getCount(): Int {
            return tabCount
        }

        override fun getPageTitle(position: Int): CharSequence = pageTitles[position]
    }
}