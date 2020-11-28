package com.appiness.yo2see.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.appiness.yo2see.R

import com.appiness.yo2see.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* rlBuy.setOnClickListener {
            mFragmentNavigation.pushFragment(AdsItemsFragment.getInstance(mInt + 1))

        }*/
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
