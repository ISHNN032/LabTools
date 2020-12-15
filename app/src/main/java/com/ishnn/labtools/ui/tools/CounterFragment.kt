package com.ishnn.labtools.ui.tools

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.ishnn.labtools.R
import kotlinx.android.synthetic.main.fragment_tools_stopwatch.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.DecimalFormat

class CounterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        val root = inflater.inflate(R.layout.fragment_tools_counter, container, false)


        return root
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}