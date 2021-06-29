package com.dartmic.yo2see.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.MainActivity
import com.dartmic.yo2see.utils.GifUtil
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), GifEndListener {

    private var gifUtil: GifUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        let {
            gifUtil = GifUtil(this)
            gifUtil?.setImage(this, gifImageView, R.drawable.animation_with_icon)
        }
    }

    override fun animationEnd() {
        let {
            var preferenceManager = PreferenceManager(this)

            if (!preferenceManager.isUserLoggedIn()) {

                startActivity(MainActivity.getIntent(it))
            } else {
                startActivity(LandingActivity.getIntent(it, 1))

            }
        }
    }


}