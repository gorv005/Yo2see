package com.dartmic.yo2see.ui.otp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.model.chat.User
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.dartmic.yo2see.ui.signup.SignUpActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    private var userType: String? = null

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
            userType = getStringExtra(UserType)
        }
        ivBackVerify.setOnClickListener {
            onBackPressed()
        }
        btnSubmit.setOnClickListener {
            verifyOTP()
        }
        this.let { UiUtils.hideSoftKeyboard(it) }

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
                phonenumber!!
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
                service!!,
                phonenumber!!,
                firstName!!,
                email!!,
                password!!,
                device_id!!, device_type!!, latitude!!, longitude!!, userType!!
            )
        }
    }

    private fun subscribeUi() {
        model.registerData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                performRegistration(email!!, password!!)

                showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
                this.let {
                    val handler = Handler()
                    handler.postDelayed({
                        startActivity(LoginActivity.getIntent(it))

                    }, 1000)
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
            } else {
                showSnackbar(it.message, true)

            }
        })

        model.otpVerify.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                doRegister()
                this.let { UiUtils.hideSoftKeyboard(it) }
            } else {
                showSnackbar(it.message, true)

            }
        })

    }

    private fun performRegistration(email: String, password: String) {


        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                Log.e(TAG, "Successfully created user with uid: ${it.result!!.user?.uid}")
                // uploadImageToFirebaseStorage()
                saveUserToFirebaseDatabase(firstName!!)
            }
            .addOnFailureListener {
                Log.e(TAG, "Failed to create user: ${it.message}")
                Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
            }
    }


    private fun saveUserToFirebaseDatabase(name: String) {
        val uid = FirebaseAuth.getInstance().uid ?: return
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

       /* val user = if (profileImageUrl == null) {
            User(uid, name, null)
        } else {
            User(uid, name_edittext_register.text.toString(), profileImageUrl)
        }*/
        val user=  User(uid, name, null)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Finally we saved the user to Firebase Database")

               /* val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                overridePendingTransition(R.anim.enter, R.anim.exit)*/
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to set value to database: ${it.message}")
             /*   loading_view.visibility = View.GONE
                already_have_account_text_view.visibility = View.VISIBLE*/
            }
    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {
        val TAG = OtpVerifyActivity::class.java.simpleName!!

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
        const val UserType = "UserType"

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
            longitude: String,
            userType: String
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
            ).putExtra(
                UserType, userType
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