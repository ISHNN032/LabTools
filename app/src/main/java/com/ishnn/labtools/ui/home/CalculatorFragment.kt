package com.ishnn.labtools.ui.home

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.ishnn.labtools.R

/**
 * A placeholder fragment containing a simple view.
 */
class CalculatorFragment : Fragment() {
    private lateinit var mContent: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_calculator, container, false)
        mContent = root.findViewById(R.id.calculator_content)
        parentFragmentManager.beginTransaction()
            .add(R.id.calculator_content, com.ishnn.labtools.ui.home.contentfragment.MainFragment()).addToBackStack(null).commit()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.action_menu_calculator_content, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Safe call operator ? added to the variable before invoking the property instructs the compiler to invoke the property only if the value isn't null.
        when (item?.itemId) {
            R.id.action_calculator_bookmark -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}