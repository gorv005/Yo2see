package com.dartmic.yo2see.tutorails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.utils.GifUtil
import kotlinx.android.synthetic.main.activity_explore.*
import kotlinx.android.synthetic.main.activity_main.*

class ExploreActivity : AppCompatActivity() , GifEndListener {
    private var gifUtil: GifUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        btnExplore.setOnClickListener {
            let {
                startActivity(LandingActivity.getIntent(it,1))
            }
        }
        let {
            gifUtil = GifUtil(this)
            gifUtil?.setImage(this, ivExploreTree, R.drawable.tree_colored_updated)
        }
        ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, ExploreActivity::class.java)
            return intent
        }
    }

    override fun animationEnd() {
        let {
        //    gifUtil?.setImage(this, ivExploreTree, R.drawable.old_tree)

        }
    }
}