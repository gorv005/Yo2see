package com.appiness.yo2see.ui.home


import android.app.ActivityOptions
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appiness.yo2see.R
import com.appiness.yo2see.base.BaseFragment
import com.appiness.yo2see.ui.AdsItems.AdsItemsFragment
import com.appiness.yo2see.view.ads.ItemListActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.fragment_home




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rlBuy.setOnClickListener {
            mFragmentNavigation.pushFragment(AdsItemsFragment.getInstance(mInt + 1))

        }
    }
    companion object {

        fun getInstance(instance: Int): HomeFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}
