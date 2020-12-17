package com.dartmic.yo2see.ui.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.R
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignup.setOnClickListener {
            let {
                startActivity(LandingActivity.getIntent(it,1))
            }
        }
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, SignUpActivity::class.java)
            return intent
        }
    }

}