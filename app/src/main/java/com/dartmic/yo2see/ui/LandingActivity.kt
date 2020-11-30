package com.dartmic.yo2see.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arthurivanets.bottomsheets.BottomSheet
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.ui.AdsItems.sheet.SimpleAdsBottomSheet
import com.dartmic.yo2see.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import kotlinx.android.synthetic.main.activity_landing.*


const val INDEX_HOME = FragNavController.TAB1
const val INDEX_PODCAST = FragNavController.TAB2
const val INDEX_MY_ADS = FragNavController.TAB3
const val INDEX_ACCOUNT = FragNavController.TAB4
class LandingActivity : AppCompatActivity(), BaseFragment.FragmentNavigation,
    FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    override val numberOfRootFragments: Int = 4
    private val fragNavController: FragNavController =
        FragNavController(supportFragmentManager, R.id.container)

    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        val options = FragNavTransactionOptions.newBuilder()
        options.reordering = true
        sharedElementList?.let {
            it.forEach { pair ->
                options.addSharedElement(pair)
            }
        }
        fragNavController.pushFragment(fragment, options.build())
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }

    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_HOME -> return HomeFragment.getInstance(0)
            INDEX_PODCAST -> return HomeFragment.getInstance(0)
         //   INDEX_RENT_BUY_SELL -> return HomeFragment.getInstance(0)
            INDEX_MY_ADS -> return HomeFragment.getInstance(0)
            INDEX_ACCOUNT -> return HomeFragment.getInstance(0)

        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context, f: Int): Intent? {
            val intent = Intent(context, LandingActivity::class.java)
            intent.putExtra(KEY_TAB, f)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        bottomBar.inflateMenu(R.menu.bottom_nav_menu)
        fragNavController.apply {
            transactionListener = this@LandingActivity
            rootFragmentListener = this@LandingActivity
            createEager = false
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Log.e("DEBUG", message, throwable)
                }
            }


            defaultTransactionOptions = FragNavTransactionOptions.newBuilder().customAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            ).build()
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottomBar.getMenu().getItem(index).setChecked(true)                }
            })
        }
        fragNavController.initialize(INDEX_HOME, savedInstanceState)
        val initial = savedInstanceState == null
        if (initial) {
            bottomBar.getMenu().getItem(INDEX_HOME).setChecked(true)
        }

        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    fragNavController.switchTab(INDEX_HOME)

                    // Respond to navigation item 1 click
                    true
                }
                R.id.action_podcast -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }
        bottomBar.setOnNavigationItemReselectedListener { item ->
            fragNavController.clearStack()
        }
    }

    override fun onBackPressed() {
        if (fragNavController.popFragment().not()) {
            super.onBackPressed()
        }
    }
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        fragNavController.onSaveInstanceState(outState!!)

    }
    private var bottomSheet : BottomSheet? = null

    fun showAds( ) {
        dismissBottomSheet()

        val sortByBottomSheet = SimpleAdsBottomSheet(this).also(BottomSheet::show)


    }

    private fun dismissBottomSheet(animate: Boolean = true) {
        bottomSheet?.dismiss(animate)
        bottomSheet = null
    }

    public fun updateStatusBarColor(
        color: Int,
        bottomColor:Int
    ) { // Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(color)
        }
        //    bottomBar.setColorChange(color)
        fab.setColorFilter(color)
        //fab.visibility = visbility
        if(bottomColor==1){
            bottomBar.visibility=View.VISIBLE
            bottomBarBlue.visibility=View.GONE
        }else{
            bottomBar.visibility=View.GONE
            bottomBarBlue.visibility=View.VISIBLE
        }


    }


}
