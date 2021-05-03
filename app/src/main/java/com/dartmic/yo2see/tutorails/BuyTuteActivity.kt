package com.dartmic.yo2see.tutorails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.MainActivity
import com.dartmic.yo2see.R
import kotlinx.android.synthetic.main.activity_buy_tute.*
import kotlinx.android.synthetic.main.activity_buy_tute.ivBackArrow

class BuyTuteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_tute)
        btnNext.setOnClickListener {
            let {
                startActivity(RentTuteActivity.getIntent(it))
            }
        }
        ivBackArrow.setOnClickListener {
            onBackPressed()
        }
/*
        tvSkip.setOnClickListener {
            let {
                startActivity(ExploreActivity.getIntent(it))
            }
        }
*/
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, BuyTuteActivity::class.java)
            return intent
        }
    }
}