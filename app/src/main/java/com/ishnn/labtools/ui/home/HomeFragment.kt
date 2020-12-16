package com.ishnn.labtools.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import com.ishnn.labtools.KakaoLogin
import com.ishnn.labtools.LoginActivity
import com.ishnn.labtools.MainActivity
import com.ishnn.labtools.R
import com.ishnn.labtools.ui.home.contentfragment.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    //    @BindView(R.id.main_qa_text)
    //    TextView main_qa_text;
    var pref: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        pref = activity?.getSharedPreferences("lan", Context.MODE_PRIVATE)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_make_text!!.text = getString(R.string.main_fragment_make_text_kor)
        main_dilution_text!!.text = getString(R.string.main_fragment_dil_text_kor)
        main_mw_text!!.text = getString(R.string.main_fragment_mw_text_kor)
        main_cell_text!!.text = getString(R.string.main_fragment_cell_text_kor)
        main_buffer_text!!.text = getString(R.string.main_fragment_buffer_text_kor)
        main_unit_converter_text!!.text = getString(R.string.main_fragment_unit_converter_text_kor)
        main_pcr_text!!.text = getString(R.string.main_fragment_protein_text_kor)
        main_sds_text!!.text = getString(R.string.main_fragment_dnarna_text_kor)
        //            main_qa_text.setText(getString(R.string.main_fragment_qa_text_kor));
        main_button_menu!!.setOnClickListener {
            main_drawer_layout!!.open()
        }
        val main_drawer_close = main_navigationView!!.getHeaderView(0).findViewById<ImageView>(R.id.btn_exit_main_drawer_activity)
        main_drawer_close.setOnClickListener {
            main_drawer_layout!!.close()
        }
        main_button_login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }


        val userdata = KakaoLogin.getUserData()
        if(userdata != null){
            main_text_info.text = "${userdata.nickName}님 환영합니다."
            main_button_login.visibility = View.GONE
            val main_user_name = main_navigationView!!.getHeaderView(0).findViewById<TextView>(R.id.home_header_username)
            main_user_name.text = userdata.nickName
            val main_user_id = main_navigationView!!.getHeaderView(0).findViewById<TextView>(R.id.home_header_userId)
            main_user_id.text = userdata.id.toString()
        }

        make!!.setOnClickListener { replaceFragment(MakeFragment()) }
        dilution!!.setOnClickListener { replaceFragment(DilutionFragment()) }
        mw_cal!!.setOnClickListener { replaceFragment(MW_CalFragment()) }
        cell_culture!!.setOnClickListener { replaceFragment(Cell_CultureFragment()) }
        buffer!!.setOnClickListener { replaceFragment(BufferFragment()) }
        unit_converter!!.setOnClickListener { replaceFragment(UnitConverterFragment()) }
        pcr_fold!!.setOnClickListener { replaceFragment(ProteinFragment()) }
        gel_cal!!.setOnClickListener { replaceFragment(DnarnaFragment()) }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.action_menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}