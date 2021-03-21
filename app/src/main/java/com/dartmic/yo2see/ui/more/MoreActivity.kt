package com.dartmic.yo2see.ui.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import com.dartmic.yo2see.R
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.change_password.ChangePasswordActivity
import com.dartmic.yo2see.ui.profile.ProfileActivity
import com.dartmic.yo2see.utils.Config
import kotlinx.android.synthetic.main.activity_more.*


class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        var preferenceManager = PreferenceManager(this)

        val username = SpannableString(preferenceManager?.getLoggedInUserName())
        username.setSpan(UnderlineSpan(), 0, username.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvUserName.text = username.toString()
        ivClose.setOnClickListener {
            finish()
        }
        tvMyAccount.setOnClickListener {
            startActivity(ProfileActivity.getIntent(this))
        }
        tvUserName.setOnClickListener {
            startActivity(ProfileActivity.getIntent(this))
        }
        tvChangePassword.setOnClickListener {
            startActivity(ChangePasswordActivity.getIntent(this))

        }
        tvLogout.setOnClickListener {
            var preferenceManager = PreferenceManager(this)
            preferenceManager.savePreference(
                Config.SharedPreferences.PROPERTY_LOGIN_PREF,
                false
            )
            startActivity(
                LandingActivity.getIntent(this, 1)
            )
        }
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, MoreActivity::class.java)
            return intent
        }
    }

}