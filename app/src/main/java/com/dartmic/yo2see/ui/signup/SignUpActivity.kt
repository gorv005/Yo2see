package com.dartmic.yo2see.ui.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.ui.otp.OtpVerifyActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class) {
    var userType: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnSignup.setOnClickListener {
            doRegister()

        }
        rlPrivate.setOnClickListener {
            userType = "Private"
            rlPrivate.setBackgroundColor(ContextCompat.getColor(this, R.color.red_a))
            rlBusiness.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red))
            tvPrivate.setTextColor(ContextCompat.getColor(this, R.color.white))
            tvBusiness.setTextColor(ContextCompat.getColor(this, R.color.black))

        }
        rlBusiness.setOnClickListener {
            userType = "Business"
            rlBusiness.setBackgroundColor(ContextCompat.getColor(this, R.color.red_a))
            rlPrivate.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red))
            tvBusiness.setTextColor(ContextCompat.getColor(this, R.color.white))
            tvPrivate.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
        subscribeLoading()
        subscribeUi()

    }

    override fun layout(): Int = R.layout.activity_sign_up

    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }

    fun doRegister() {


        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateMobileError =
            AndroidUtils.mobilePasswordValidation(etPhonenumber.text.toString())
        val validatePasswordError = AndroidUtils.validateName(etPassword.text.toString())
        val validateNameError = AndroidUtils.validateName(etFirstName.text.toString())
        val validateEmailError = AndroidUtils.validateEmail(etEmail.text.toString())
        val validateMatchPasswordError = AndroidUtils.validateMatchPassword(
            etPassword.text.toString(),
            etConfirmPassword.text.toString()
        )


        if (
            TextUtils.isEmpty(validateMobileError) && TextUtils.isEmpty(validatePasswordError)
            && TextUtils.isEmpty(validateNameError) && TextUtils.isEmpty(validateEmailError)
            && TextUtils.isEmpty(validateMatchPasswordError)

        ) {
            let {
                startActivity(
                    OtpVerifyActivity.getIntent(
                        it,
                        "User Signup",
                        ccp.selectedCountryCodeWithPlus + "" + etPhonenumber.text.toString(),
                        etFirstName.text.toString(),
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        "12345",
                        "12345",
                        "12345",
                        "12345",
                        userType
                    )
                )
            }
            /* if (NetworkUtil.isInternetAvailable(this)) {
                 model.register(
                     "User Signup",
                     etPhonenumber.text.toString(),
                     etFirstName.text.toString(),
                     etEmail.text.toString(),
                     etPassword.text.toString(),
                     "12345","12345","12345","12345"
                 )
             }*/

        } else {
            etPhonenumber.error = validateMobileError
            etPassword.error = validatePasswordError
            etFirstName.error = validateNameError
            etEmail.error = validateEmailError
            etConfirmPassword.error = validateMatchPasswordError
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
                UiUtils.showInternetDialog(this@SignUpActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.registerData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
                onBackPressed()
            } else {
                showSnackbar(it.message, true)

            }
        })

    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, SignUpActivity::class.java)
            return intent
        }
    }

}