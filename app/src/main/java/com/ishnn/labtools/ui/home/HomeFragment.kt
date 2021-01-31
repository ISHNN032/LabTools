package com.ishnn.labtools.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.ishnn.labtools.*
import com.ishnn.labtools.ui.home.content.*
import com.ishnn.labtools.util.animOptions
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), GlobalLogin.OnLoginInterface {

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

        //하위 메뉴에서 Navigation을 통해 이동했을 경우, BackStack 이 남아있는 현상 수정
        val fm: FragmentManager = parentFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }

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
        val main_drawer_close = main_navigationView!!.getHeaderView(0)
            .findViewById<ImageView>(R.id.btn_exit_main_drawer_activity)
        main_drawer_close.setOnClickListener {
            main_drawer_layout!!.close()
        }
        main_navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.drawer_item_stopwatch -> {
                    Log.e("drawer", "goto stopwatch")
                    NavHostFragment.findNavController(this).navigate(
                        R.id.action_nav_home_to_stopwatch,
                        null,
                        animOptions
                    )
                }
                R.id.drawer_item_timer -> {
                    Log.e("drawer", "goto timer")
                    NavHostFragment.findNavController(this).navigate(
                        R.id.action_nav_home_to_timer,
                        null,
                        animOptions
                    )
                }
                R.id.drawer_item_calculator -> {
                    Log.e("drawer", "goto calculator")
                    NavHostFragment.findNavController(this).navigate(
                        R.id.action_nav_home_to_calculator,
                        null,
                        animOptions
                    )
                }
                R.id.drawer_item_counter -> {
                    Log.e("drawer", "goto counter")
                    NavHostFragment.findNavController(this).navigate(
                        R.id.action_nav_home_to_counter,
                        null,
                        animOptions
                    )
                }
                R.id.drawer_item_memo -> {
                    Log.e("drawer", "goto memo")
                    NavHostFragment.findNavController(this).navigate(
                        R.id.action_nav_home_to_memo,
                        null,
                        animOptions
                    )
                }
                R.id.drawer_item_community -> {
                    Log.e("drawer", "goto community")
                    NavHostFragment.findNavController(this).navigate(
                        R.id.navigation_community,
                        null,
                        animOptions
                    )
                }
                R.id.drawer_item_license -> {
                    Log.e("drawer", "goto license")
                    startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                    OssLicensesMenuActivity.setActivityTitle("Open Source Licenses")
                }
                R.id.drawer_item_setting -> {
                    Log.e("drawer", "goto setting")
                }
            }
            true
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

    override fun onResume() {
        super.onResume()
        checkLoggedIn()
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.nav_host_fragment, fragment)?.addToBackStack(null)
            ?.commit()
    }

    private fun checkLoggedIn() {
        val userdata = GlobalLogin.getUserData()
        if (userdata != null) {
            main_text_info.text = "${userdata.nickName}님 환영합니다."
            val main_user_name = main_navigationView!!.getHeaderView(0)
                .findViewById<TextView>(R.id.home_header_username)
            main_user_name.text = userdata.nickName
            val main_user_id = main_navigationView!!.getHeaderView(0)
                .findViewById<TextView>(R.id.home_header_userId)
            main_user_id.text = userdata.id.toString()

//            val mainScope = CoroutineScope(Dispatchers.Main)
//            mainScope.launch {
//                try {
//                    val bmp = BitmapFactory.decodeStream(userdata.imageUrl.openConnection().getInputStream())
//                    val main_user_id = main_navigationView!!.getHeaderView(0).findViewById<ImageView>(R.id.home_header_userImage)
//                    main_user_id.setImageBitmap(bmp)
//                } catch (e: MalformedURLException) {
//                    e.printStackTrace()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    Log.e("e",e.toString())
//                }
//            }
            main_button_login.text = "로그아웃"
            main_button_login.setOnClickListener {
                GlobalLogin.logoutKakao(this)
                GlobalLogin.logoutNaver(requireContext(), this)
            }
        } else {
            main_button_login.text = "로그인"
            main_button_login.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                requireActivity().startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.action_menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home_favorite -> {

                return true
            }
            R.id.action_home_share -> {
                //createLink("test", "", null)
                val msg = Intent(Intent.ACTION_SEND)

                msg.addCategory(Intent.CATEGORY_DEFAULT)
                msg.putExtra(
                    Intent.EXTRA_TEXT,
                    "https://trello.com/b/0Xj4wtZY/labtools"
                )
                msg.putExtra(Intent.EXTRA_TITLE, "제목")
                msg.type = "text/plain"
                startActivity(Intent.createChooser(msg, "앱을 선택해 주세요"))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onLogin(platform: LoginPlatform) {
        TODO("Not yet implemented")
    }

    override fun onLogout() {
        requireActivity().finish()
        val intent = Intent(this.context, MainActivity::class.java)
        startActivity(intent)
    }
}