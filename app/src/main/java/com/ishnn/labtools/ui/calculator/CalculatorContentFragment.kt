package com.ishnn.labtools.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.ishnn.labtools.R

/**
 * A placeholder fragment containing a simple view.
 */
class CalculatorContentFragment : Fragment() {
    private lateinit var mContent: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calculator_content, container, false)
        mContent = root.findViewById(R.id.calculator_content)
        parentFragmentManager.beginTransaction()
            .add(R.id.calculator_content, com.ishnn.labtools.ui.calculator.contentfragment.MainFragment()).addToBackStack(null).commit()
        return root
    }
}