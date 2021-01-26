package com.dartmic.yo2see.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.signup.SignUpActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.GifUtil
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) ,  GifEndListener {
    private var gifUtil: GifUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvSignUp.setOnClickListener {
            let {
                startActivity(SignUpActivity.getIntent(it))
            }
        }
        btnLogin.setOnClickListener {
            doSignIn()
        }
        let {
            gifUtil = GifUtil(this)
            gifUtil?.setImage(this, ivLoginCart, R.drawable.cart_icon)
        }
        subscribeLoading()
        subscribeUiLogin()
    }


    fun doSignIn() {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateEmailError = AndroidUtils.mobilePassword(etUserName.text.toString())
        val validatePasswordError = AndroidUtils.validateName(etPasswordLogin.text.toString())

        if (
            TextUtils.isEmpty(validateEmailError) || TextUtils.isEmpty(validatePasswordError)

        ) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.login("User Login",etUserName.text.toString(), etPasswordLogin.text.toString(), "12345", "Android","12345","12345")
            }

        } else {
            etUserName.error = validateEmailError
            etPasswordLogin.error = validatePasswordError
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
                UiUtils.showInternetDialog(this@LoginActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUiLogin() {
        model.loginData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                showSnackbar(it.message, true)

                model.saveUserDetail(it?.userList)


                this.let { UiUtils.hideSoftKeyboard(it) }

                startActivity(
                        LandingActivity.getIntent(this,1),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                    )
            }
            else{
                showSnackbar(it.message, false)

            }
        })

    }
    override fun layout(): Int = R.layout.activity_login


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
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    override fun animationEnd() {
        let {
            gifUtil?.setImage(this, ivLoginCart, R.drawable.cart_icon)

        }
    }
    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

}