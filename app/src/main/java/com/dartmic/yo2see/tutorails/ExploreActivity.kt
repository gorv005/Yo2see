package com.dartmic.yo2see.tutorails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.R
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_explore.*

class ExploreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        btnExplore.setOnClickListener {
            let {
                startActivity(LoginActivity.getIntent(it))
            }
        }
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, ExploreActivity::class.java)
            return intent
        }
    }
}