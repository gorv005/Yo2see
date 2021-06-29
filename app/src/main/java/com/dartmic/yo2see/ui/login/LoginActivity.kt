package com.dartmic.yo2see.ui.login

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dartmic.yo2see.BuildConfig
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.chat_list.ChatListFragment
import com.dartmic.yo2see.ui.forgot_password.ForgotPasswordActivity
import com.dartmic.yo2see.ui.signup.SignUpActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.GifUtil
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.gsa.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class), GifEndListener {
    private var gifUtil: GifUtil? = null
    lateinit var callbackManager: CallbackManager

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    var id = ""
    var firstName = ""
    var middleName = ""
    var lastName = ""
    var name = ""
    var picture = ""
    var email = ""
    var accessToken = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        FirebaseApp.initializeApp(this)

        tvSignUp.setOnClickListener {
            let {
                startActivity(SignUpActivity.getIntent(it))
            }
        }
        btnSignInFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance()

        btnSignInGoogle.setOnClickListener { view: View? ->
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()
        }
        tvForgotpassword.setOnClickListener {
            startActivity(ForgotPasswordActivity.getIntent(this))
        }
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Log.d("TAG", "Success Login")
                    getUserProfile(loginResult?.accessToken, loginResult?.accessToken?.userId)
                }

                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_LONG).show()
                }
            })

        btnLogin.setOnClickListener {
            performLogin()
        }
        let {
            gifUtil = GifUtil(this)
            gifUtil?.setImage(this, ivLoginCart, R.drawable.cart_icon)
        }
        subscribeLoading()
        subscribeUiLogin()
    }

    private fun signInGoogle() {

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    fun doSignIn(uid:String?) {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateEmailError = AndroidUtils.mobilePassword(etUserName.text.toString())
        val validatePasswordError = AndroidUtils.validateName(etPasswordLogin.text.toString())

        if (
            TextUtils.isEmpty(validateEmailError) || TextUtils.isEmpty(validatePasswordError)

        ) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.login(
                    "User Login",
                    etUserName.text.toString(),
                    etPasswordLogin.text.toString(),
                    "12345",
                    "Android",
                    "12345",
                    "12345",
                    uid!!
                )
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
                model.savePassword(etPasswordLogin.text.toString())

                this.let { UiUtils.hideSoftKeyboard(it) }

                startActivity(
                    LandingActivity.getIntent(this, 1),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                )
            } else {
                showSnackbar(it.message, false)

            }
        })
        model.socialLoginData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                showSnackbar(it.message, true)

                model.saveUserDetail(it?.userList)


                this.let { UiUtils.hideSoftKeyboard(it) }

                startActivity(
                    LandingActivity.getIntent(this, 1),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                )
            } else {
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

    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {

        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, first_name, middle_name, last_name, name, picture, email"
        )
        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            GraphRequest.Callback { response ->
                val jsonObject = response.jsonObject

                // Facebook Access Token
                // You can see Access Token only in Debug mode.
                // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }
                accessToken = token.toString()

                // Facebook Id
                if (jsonObject.has("id")) {
                    val facebookId = jsonObject.getString("id")
                    Log.i("Facebook Id: ", facebookId.toString())
                    id = facebookId.toString()
                } else {
                    Log.i("Facebook Id: ", "Not exists")
                    id = "Not exists"
                }


                // Facebook First Name
                if (jsonObject.has("first_name")) {
                    val facebookFirstName = jsonObject.getString("first_name")
                    Log.i("Facebook First Name: ", facebookFirstName)
                    firstName = facebookFirstName
                } else {
                    Log.i("Facebook First Name: ", "Not exists")
                    firstName = "Not exists"
                }


                // Facebook Middle Name
                if (jsonObject.has("middle_name")) {
                    val facebookMiddleName = jsonObject.getString("middle_name")
                    Log.i("Facebook Middle Name: ", facebookMiddleName)
                    middleName = facebookMiddleName
                } else {
                    Log.i("Facebook Middle Name: ", "Not exists")
                    middleName = "Not exists"
                }


                // Facebook Last Name
                if (jsonObject.has("last_name")) {
                    val facebookLastName = jsonObject.getString("last_name")
                    Log.i("Facebook Last Name: ", facebookLastName)
                    lastName = facebookLastName
                } else {
                    Log.i("Facebook Last Name: ", "Not exists")
                    lastName = "Not exists"
                }


                // Facebook Name
                if (jsonObject.has("name")) {
                    val facebookName = jsonObject.getString("name")
                    Log.i("Facebook Name: ", facebookName)
                    name = facebookName
                } else {
                    Log.i("Facebook Name: ", "Not exists")
                    name = "Not exists"
                }


                // Facebook Profile Pic URL
                if (jsonObject.has("picture")) {
                    val facebookPictureObject = jsonObject.getJSONObject("picture")
                    if (facebookPictureObject.has("data")) {
                        val facebookDataObject = facebookPictureObject.getJSONObject("data")
                        if (facebookDataObject.has("url")) {
                            val facebookProfilePicURL = facebookDataObject.getString("url")
                            Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                            picture = facebookProfilePicURL
                        }
                    }
                } else {
                    Log.i("Facebook Profile Pic URL: ", "Not exists")
                    picture = "Not exists"
                }

                // Facebook Email
                if (jsonObject.has("email")) {
                    val facebookEmail = jsonObject.getString("email")
                    Log.i("Facebook Email: ", facebookEmail)
                    email = facebookEmail
                } else {
                    Log.i("Facebook Email: ", "Not exists")
                    email = "Not exists"
                }
                if (NetworkUtil.isInternetAvailable(this)) {
                    model.socialLogin(
                        "Login",
                        name,
                        email,
                        "",
                        "123456",
                        "Android",
                        "12345",
                        "12345",
                        firstName,
                        firstName,
                        picture,
                        "1",
                        "Facebook"
                    )
                }

            }).executeAsync()
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        return isLoggedIn
    }


    fun logOutUser() {
        LoginManager.getInstance().logOut()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
//            firebaseAuthWithGoogle(account!!)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                if (NetworkUtil.isInternetAvailable(this)) {
                    model.socialLogin(
                        "Login",
                        account.displayName.toString(),
                        account.email.toString(),
                        "",
                        "123456",
                        "Android",
                        "12345",
                        "12345",
                        account.familyName.toString(),
                        account.givenName.toString(),
                        account.photoUrl.toString(),
                        "1",
                        "Gmail"
                    )
                }
                //  SavedPreference.setEmail(this,account.email.toString())
                //    SavedPreference.setUsername(this,account.displayName.toString())
                /* val intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
                 finish()*/
            }
        }
    }
    private fun performLogin() {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(etUserName.text.toString(), etPasswordLogin.text.toString())
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.e(ChatListFragment.TAG, "Successfully logged in: ${it.result!!.user?.uid}")
                doSignIn(it.result!!.user?.uid)
                /*  val intent = Intent(this, LatestMessagesActivity::class.java)
                  intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                  startActivity(intent)
                  overridePendingTransition(R.anim.enter, R.anim.exit)*/
            }
            .addOnFailureListener {
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()

            }
    }

}