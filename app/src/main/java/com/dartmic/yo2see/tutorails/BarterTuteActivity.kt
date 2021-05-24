package com.dartmic.yo2see.tutorails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.R
import kotlinx.android.synthetic.main.activity_barter_tute.*
import kotlinx.android.synthetic.main.activity_barter_tute.ivBackArrow

class BarterTuteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barter_tute)
        btnNext.setOnClickListener {
            let {
                startActivity(PostTuteActivity.getIntent(it))
            }
        }
        ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        tvSkip.setOnClickListener {
            let {
                startActivity(ExploreActivity.getIntent(it))
            }
        }

    }
    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, BarterTuteActivity::class.java)
            return intent
        }
    }

}