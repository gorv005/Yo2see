package com.appiness.yo2see

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appiness.yo2see.view.ads.ItemListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rlBuy.setOnClickListener {
            startActivity(
                ItemListActivity.getIntent(this),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }
}
