package com.dartmic.yo2see.ui.forgot_password

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.ui.change_password.ChangePasswordActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class) {


    override fun layout(): Int = R.layout.activity_forgot_password


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
        btnSendEmail.setOnClickListener {
            forgotPassword()
        }
        btnSignInChangePassword.setOnClickListener {
            startActivity(LoginActivity.getIntent(this))
        }
        llResendEmail.setOnClickListener {
            forgotPassword()
        }
        subscribeLoading()
        subscribeUi()
        ivBackChangePassword.setOnClickListener {
            onBackPressed()
        }

    }


    fun forgotPassword() {
        val validateEmailError = AndroidUtils.validateEmail(etEmailForgot.text.toString())


        this?.let { UiUtils.hideSoftKeyboard(it) }
        if (
            TextUtils.isEmpty(validateEmailError)
        ) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.forgotPassword(
                    "Forget Password",
                    etEmailForgot.text.toString()
                )
            }

        } else {
            etEmailForgot.error = validateEmailError
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
                    this@ForgotPasswordActivity,
                    R.string.something_went_wrong
                )
            }
        })

    }

    private fun subscribeUi() {
        model.forgotPasswordModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                llForgotPasswordRequest.visibility = View.GONE
                llForgotPassword.visibility = View.VISIBLE
                 showSnackbar(it.message, true)
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
            val intent = Intent(context, ForgotPasswordActivity::class.java)
            return intent
        }
    }
}