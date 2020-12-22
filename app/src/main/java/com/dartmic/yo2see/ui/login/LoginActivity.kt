package com.dartmic.yo2see.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.signup.SignUpActivity
import com.dartmic.yo2see.utils.GifUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity(), GifEndListener {
    private var gifUtil: GifUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tvSignUp.setOnClickListener {
            let {
                startActivity(SignUpActivity.getIntent(it))
            }
        }
        btnLogin.setOnClickListener {

                let {
                    startActivity(LandingActivity.getIntent(it, 1))
                }

        }
        let {
            gifUtil = GifUtil(this)
            gifUtil?.setImage(this, ivLoginCart, R.drawable.cart_icon)
        }
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

}