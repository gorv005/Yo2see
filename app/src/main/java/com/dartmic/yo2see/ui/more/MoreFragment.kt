package com.dartmic.yo2see.ui.more

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.change_password.ChangePasswordActivity
import com.dartmic.yo2see.ui.favorites.FragmentFavorites
import com.dartmic.yo2see.ui.home.HomeFragment
import com.dartmic.yo2see.ui.productDetails.FragmentProductDetails
import com.dartmic.yo2see.ui.profile.ProfileActivity
import com.dartmic.yo2see.utils.Config

import kotlinx.android.synthetic.main.fragment_more.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoreFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var preferenceManager = PreferenceManager(activity!!)

        val username = SpannableString(preferenceManager?.getLoggedInUserName())
        username.setSpan(UnderlineSpan(), 0, username.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvUserName.text = username.toString()
        ivClose.setOnClickListener {
            activity?.onBackPressed()
        }
        tvMyAccount.setOnClickListener {
            startActivity(ProfileActivity.getIntent(activity!!))
        }
        tvUserName.setOnClickListener {
            startActivity(ProfileActivity.getIntent(activity!!))
        }
        tvChangePassword.setOnClickListener {
            startActivity(ChangePasswordActivity.getIntent(activity!!))

        }
        tvFavorites.setOnClickListener {
            mFragmentNavigation.pushFragment(
                FragmentFavorites
                    .getInstance(mInt + 1)
            )
        }
        tvLogout.setOnClickListener {
            var preferenceManager = PreferenceManager(activity!!)
            preferenceManager.savePreference(
                Config.SharedPreferences.PROPERTY_LOGIN_PREF,
                false
            )
            startActivity(
                LandingActivity.getIntent(activity!!, 1)
            )
        }
    }

    companion object {

        fun getInstance(instance: Int): MoreFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = MoreFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_more

}