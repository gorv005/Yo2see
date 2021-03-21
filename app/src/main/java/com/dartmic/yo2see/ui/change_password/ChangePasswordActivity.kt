package com.dartmic.yo2see.ui.change_password

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.ui.profile.ProfileActivity
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class) {
    override fun layout(): Int = R.layout.activity_change_password
    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnChangePassword.setOnClickListener {
            changePassword()
        }
        btnSignInChangePassword.setOnClickListener {
            startActivity(LoginActivity.getIntent(this))
        }
        subscribeLoading()
        subscribeUi()
        ivBackChangePassword.setOnClickListener {
            onBackPressed()
        }
    }

    fun changePassword() {
        val validateOldPasswordError = AndroidUtils.validateName(etOldPassword.text.toString())

        val validateMatchPasswordError = AndroidUtils.validateMatchPassword(
            etPasswordsignUp.text.toString(),
            etConfirmPasswordsignUp.text.toString()
        )
        this?.let { UiUtils.hideSoftKeyboard(it) }
        if (
            TextUtils.isEmpty(validateOldPasswordError) && TextUtils.isEmpty(
                validateMatchPasswordError
            )
        ) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.changePassword(
                    "Remove",
                    model?.getUserID()!!,
                    etOldPassword.text.toString(),
                    etConfirmPasswordsignUp.text.toString()
                )
            }

        } else {
            etOldPassword.error = validateOldPasswordError
            etConfirmPasswordsignUp.error = validateMatchPasswordError
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
                UiUtils.showInternetDialog(
                    this@ChangePasswordActivity,
                    R.string.something_went_wrong
                )
            }
        })
    }

    private fun subscribeUi() {
        model.changePasswordModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                llChangePasswordRequest.visibility = View.GONE
                llChangePassword.visibility = View.VISIBLE
                // showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
            } else {
                showSnackbar(it.message, false)

            }
        })

    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, ChangePasswordActivity::class.java)
            return intent
        }
    }

}