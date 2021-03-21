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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.arthurivanets.bottomsheets.BottomSheet
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseApplication
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.Category_sub_subTosub.SubToSubListItem
import com.dartmic.yo2see.ui.AdsItems.sheet.SimpleAdsBottomSheet
import com.dartmic.yo2see.ui.addProduct.AddProductFragment
import com.dartmic.yo2see.ui.buycategoriesList.CategoriesListFragment
import com.dartmic.yo2see.ui.favorites.FragmentFavorites
import com.dartmic.yo2see.ui.home.HomeFragment
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.ui.more.MoreActivity
import com.dartmic.yo2see.ui.postAdd.PostAnAddFragment
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_landing.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.backgroundDrawable


const val INDEX_HOME = FragNavController.TAB1
const val INDEX_FAV = FragNavController.TAB2
const val INDEX_ADD_POST = FragNavController.TAB3
const val INDEX_MY_ADS = FragNavController.TAB4
const val INDEX_ACCOUNT = FragNavController.TAB5

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
            INDEX_FAV -> return FragmentFavorites.getInstance(0)
            INDEX_ADD_POST -> return PostAnAddFragment.getInstance(0)
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
        //  bottomBar.inflateMenu(R.menu.bottom_nav_menu)
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
            fragmentHideStrategy = FragNavController.HIDE
            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    // bottomBar.getMenu().getItem(index).setChecked(true)
                    bottomBar.selectTabAtPosition(index)

                }
            })
        }
        fragNavController.initialize(INDEX_ADD_POST, savedInstanceState)
        val initial = savedInstanceState == null
        if (initial) {
            // bottomBar.getMenu().getItem(INDEX_HOME).setChecked(true)
            bottomBar.selectTabAtPosition(INDEX_ADD_POST)

        }
        bottomBar.setOnTabSelectListener({ tabId ->
            when (tabId) {

                R.id.navigation_home -> {

                    if (checkUserLogin()) {
                        startActivity(MoreActivity.getIntent(this))
                    } else {
                        startActivity(LoginActivity.getIntent(this))

                    }
                    // Respond to navigation item 2 click
                    true
                }
                R.id.action_add_post -> {

                    fragNavController.switchTab(INDEX_HOME)

                    // Respond to navigation item 2 click
                    true
                }
                R.id.action_fav -> {
                    fragNavController.switchTab(INDEX_FAV)
                    // Respond to navigation item 2 click
                    true
                }
                else -> {
                    fragNavController.switchTab(INDEX_HOME)
                    false
                }
            }
        }, initial)

/*
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_home -> {

                    if (checkUserLogin()) {
                        startActivity(MoreActivity.getIntent(this))
                    } else {
                        startActivity(LoginActivity.getIntent(this))

                    }
                    // Respond to navigation item 2 click
                    true
                }
                R.id.action_fav -> {
                    fragNavController.switchTab(INDEX_FAV)
                    // Respond to navigation item 2 click
                    true
                }
                else -> {
                    fragNavController.switchTab(INDEX_HOME)
                    false
                }
            }
        }
*/
/*
        bottomBar.setOnNavigationItemReselectedListener { item ->
            fragNavController.clearStack()
            if (checkUserLogin()) {
                startActivity(MoreActivity.getIntent(this))
            } else {
                startActivity(LoginActivity.getIntent(this))

            }

        }
*/
        fab.setOnClickListener {

            if (checkUserLogin()) {
                pushFragment(PostAnAddFragment.getInstance(1))
                //  fragNavController.switchTab(INDEX_ADD_POST)
            } else {
                startActivity(LoginActivity.getIntent(this))
            }

        }
    }

    fun checkUserLogin(): Boolean {
        var preferenceManager = PreferenceManager(this)

        return preferenceManager.isUserLoggedIn()
    }

    override fun onBackPressed() {
        if (fragNavController.popFragment().not()) {
            super.onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.run {
            var query = getStringExtra("query")
            var location = getStringExtra("location")
            var type = getStringExtra("type")
            var from = getStringExtra("from")
            if (from.equals("search")) {
                pushFragment(
                    ProductListFragment
                        .getInstance(
                            1,
                            AndroidUtils.getType(type),
                            location,
                            query,
                            SubToSubListItem()
                        )
                )
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        fragNavController.onSaveInstanceState(outState!!)

    }

    private var bottomSheet: BottomSheet? = null

    fun showAds() {
        dismissBottomSheet()

        val sortByBottomSheet = SimpleAdsBottomSheet(this).also(BottomSheet::show)


    }

    private fun dismissBottomSheet(animate: Boolean = true) {
        bottomSheet?.dismiss(animate)
        bottomSheet = null
    }

    public fun updateStatusBarColor(
        color: Int,
        bottomColor: Int
    ) { // Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(color)
        }
        bottomBar.setBackgroundColor(color)
        bottomBar.backgroundColor = color
        // bottomBar.setColorChange(color)
        fab.setColorFilter(color)
        //fab.visibility = visbility
        /* if(bottomColor==1){
             bottomBar.visibility=View.VISIBLE
             bottomBarBlue.visibility=View.GONE
         }else{
             bottomBar.visibility=View.GONE
             bottomBarBlue.visibility=View.VISIBLE
         }*/


    }

    public fun hideVisibleBottomBar(
        view: Int
    ) {
        bottomBar.visibility = view
        fab.visibility = view

    }

    public fun switchToHome(
    ) {
        fragNavController.switchTab(INDEX_HOME)


    }


    fun getVisibleFragment(): Fragment? {
        val fragmentManager: FragmentManager = this@LandingActivity.getSupportFragmentManager()
        val fragments: List<Fragment> = fragmentManager.getFragments()
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible) return fragment
            }
        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            var f = getVisibleFragment() as AddProductFragment
            f.onActivityResult(requestCode, resultCode, data)
        } catch (e: Exception) {

        }
    }
}
