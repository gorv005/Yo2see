package com.dartmic.yo2see.ui.otp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.dartmic.yo2see.ui.signup.SignUpActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_otp_verify.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class OtpVerifyActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class) {


    private var phonenumber: String? = null
    private var firstName: String? = null
    private var email: String? = null
    private var password: String? = null
    private var device_id: String? = null
    private var device_type: String? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private var service: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.run {
            phonenumber = getStringExtra(Phonenumber)
            firstName = getStringExtra(FirstName)
            email = getStringExtra(Email)
            password = getStringExtra(Password)
            device_id = getStringExtra(Device_id)
            device_type = getStringExtra(Device_type)
            latitude = getStringExtra(Latitude)
            longitude = getStringExtra(Longitude)
            service = getStringExtra(Service)

        }
        ivBackVerify.setOnClickListener {
            onBackPressed()
        }
        btnSubmit.setOnClickListener {
            verifyOTP()
        }
        getOTP()
        subscribeLoading()
        subscribeUi()
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(this@OtpVerifyActivity, R.string.something_went_wrong)
            }
        })
    }

    fun getOTP() {

        this?.let { UiUtils.hideSoftKeyboard(it) }



        if (NetworkUtil.isInternetAvailable(this)) {
            model.getOTP(
                "OTP",
                etPhonenumber.text.toString()
            )
        }


    }

    fun verifyOTP() {

        this?.let { UiUtils.hideSoftKeyboard(it) }
        if (!pinview?.value.toString().equals("") && pinview?.value.toString().length == 4) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.verifyOtp(
                    "Validate OTP",
                    phonenumber!!, pinview?.value.toString()
                )
            }
        } else {
            showSnackbar("Please enter OTP", false)

        }

    }

    fun doRegister() {
        this?.let { UiUtils.hideSoftKeyboard(it) }

        if (NetworkUtil.isInternetAvailable(this)) {
            model.register(
                Service,
                Phonenumber,
                FirstName,
                Email,
                Password,
                Device_id, Device_type, Latitude, Longitude
            )
        }
    }

    private fun subscribeUi() {
        model.registerData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
                this.let {
                    startActivity(LoginActivity.getIntent(it))
                }

            } else {
                showSnackbar(it.message, true)

            }
        })

        model.otpData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
                onBackPressed()
            } else {
                showSnackbar(it.message, true)

            }
        })

        model.otpVerify.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                doRegister()
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
        const val Phonenumber = "Phonenumber"
        const val FirstName = "FirstName"
        const val Email = "Email"
        const val Password = "Password"
        const val Device_id = "Device_id"
        const val Device_type = "Device_type"
        const val Latitude = "Latitude"
        const val Longitude = "Longitude"
        const val Service = "Service"

        fun getIntent(
            context: Context,
            service: String,
            phonenumber: String,
            firstName: String,
            email: String,
            password: String,
            device_id: String,
            device_type: String,
            latitude: String,
            longitude: String
        ): Intent? {
            val intent = Intent(context, OtpVerifyActivity::class.java).putExtra(
                Phonenumber, phonenumber
            ).putExtra(
                FirstName, firstName
            ).putExtra(
                Email, email
            ).putExtra(
                Password, password
            ).putExtra(
                Device_id, device_id
            ).putExtra(
                Device_type, device_type
            ).putExtra(
                Latitude, latitude
            ).putExtra(
                Longitude, longitude
            ).putExtra(
                Service, service
            )
            return intent
        }
    }

    override fun layout(): Int = R.layout.activity_otp_verify


    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }
}