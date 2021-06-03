package com.dartmic.yo2see.ui.home.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dartmic.yo2see.ui.home.HomeFragment


class TabFragmentAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentList: ArrayList<Fragment> = ArrayList()
    private val fragmentTitleList: ArrayList<String> = ArrayList()

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }

    fun addFragment(fragment: Fragment?, title: String?) {
        fragmentList.add(fragment!!)
        fragmentTitleList.add(title!!)
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return fragmentList.size
    }
}