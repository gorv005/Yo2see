package com.dartmic.yo2see.tutorails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.R
import kotlinx.android.synthetic.main.activity_rent_tute.*
import kotlinx.android.synthetic.main.activity_rent_tute.ivBackArrow

class RentTuteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_tute)
        btnNext.setOnClickListener {
            let {
                startActivity(BarterTuteActivity.getIntent(it))
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
            val intent = Intent(context, RentTuteActivity::class.java)
            return intent
        }
    }
}