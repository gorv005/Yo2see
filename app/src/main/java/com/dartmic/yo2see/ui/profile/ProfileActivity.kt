package com.dartmic.yo2see.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.product.ListingItem
import com.dartmic.yo2see.model.profile.UserList
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.dartmic.yo2see.ui.signup.SignUpActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.item_ads.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class ProfileActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class) {

    lateinit var user: com.dartmic.yo2see.model.login.UserList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLoading()
        subscribeUi()

        ivBackProfile.setOnClickListener {
            onBackPressed()
        }
        btnEditProfile.setOnClickListener {
            startActivity(UpdateProfileActivity.getIntent(this, user))
        }
        btnDeleteProfile.setOnClickListener {
            removeAccount()
        }
    }

    override fun onResume() {
        super.onResume()
        getUser()
    }

    fun getUser() {

        this?.let { UiUtils.hideSoftKeyboard(it) }



        if (NetworkUtil.isInternetAvailable(this)) {
            model.getUser(
                "User List",
                model?.getUserID()!!
            )
        }


    }

    fun removeAccount() {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        if (NetworkUtil.isInternetAvailable(this)) {
            model.removeAccount(
                "Remove",
                model?.getUserID()!!
            )
        }


    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(this@ProfileActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.userInforesponseViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {


                user = it?.userList!!
                model?.saveUserDetail(user)
                tvUserName.text = it?.userList?.name
                tvEmail.text = it?.userList?.email
                tvLink.text = it?.userList?.phone
                val equals = it?.userList?.equals("")
                if (it?.userList?.picture != null&& !it?.userList?.picture.equals("")) {
                    Glide.with(this).load("https://yo2see.com/app/admin/"+it?.userList?.picture)
                        .placeholder(R.drawable.ic_clothing_white).circleCrop()
                        .into(ivProfileL)
                }
                showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
            } else {
                showSnackbar(it.message, false)

            }
        })

        model.removeAccountModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
                startActivity(LoginActivity.getIntent(this))
            } else {
                showSnackbar(it.message, false)
            }
        })

    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun layout(): Int = R.layout.activity_profile

    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, ProfileActivity::class.java)
            return intent
        }
    }
}