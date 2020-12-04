package com.ishnn.labtools.ui.calculator

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishnn.labtools.R
import com.ishnn.labtools.util.animOptions
import com.rnnzzo.uxdesign.model.DesignItem


class CalculatorConcentrationFragment: Fragment(), CalculatorConcentrationAdapter.ClickListener {
    private val adapter by lazy { CalculatorConcentrationAdapter(getMenuItems(), this) }

    private lateinit var mRvDesign : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calculator_concentration, container, false)
        mRvDesign = root.findViewById<RecyclerView>(R.id.rvConcentration)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
    }

    fun getMenuItems() = listOf(
        DesignItem(
            1,
            R.drawable.ic_nav_calculator_black_24dp,
            "물(M)농도",
            R.id.action_nav_calculator_to_a
        ),
        DesignItem(
            2,
            R.drawable.ic_nav_calculator_black_24dp,
            "%농도",
            R.id.action_nav_calculator_to_a
        ),
        DesignItem(
            3,
            R.drawable.ic_nav_calculator_black_24dp,
            "%->M",
            R.id.action_nav_calculator_to_a
        ),
        DesignItem(
            4,
            R.drawable.ic_nav_calculator_black_24dp,
            "M->%",
            R.id.action_nav_calculator_to_a
        ),
        DesignItem(
            5,
            R.drawable.ic_nav_calculator_black_24dp,
            "물(M)희석",
            R.id.action_nav_calculator_to_a
        ),
        DesignItem(
            6,
            R.drawable.ic_nav_calculator_black_24dp,
            "%농도희석",
            R.id.action_nav_calculator_to_a
        ),
        DesignItem(
            7,
            R.drawable.ic_nav_calculator_black_24dp,
            "농도배수",
            R.id.action_nav_calculator_to_a
        )
    )

    fun initRecyclerView(){
        mRvDesign.setHasFixedSize(true)
        mRvDesign.layoutManager = GridLayoutManager(requireActivity(), 2)
        mRvDesign.itemAnimator = DefaultItemAnimator()
        mRvDesign.adapter = adapter
    }

    override fun onClickListener(pos: Int, aView: View) {
        val item = adapter.getItem(pos)
        findNavController().navigate(item.action, null, animOptions)
    }
}