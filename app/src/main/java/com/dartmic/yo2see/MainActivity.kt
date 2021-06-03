package com.dartmic.yo2see

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.tutorails.BuyTuteActivity
import com.dartmic.yo2see.tutorails.ExploreActivity
import com.dartmic.yo2see.utils.GifUtil
import com.dartmic.yo2see.utils.TransitionHelper
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ivLoginCart


class MainActivity : AppCompatActivity(),GifEndListener {
    private var gifUtil: GifUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        setContentView(R.layout.activity_main)

        let {
            gifUtil = GifUtil(this)
            gifUtil?.setImage(this, ivLoginCart, R.drawable.cart)
        }

        btnNext.setOnClickListener {
            let {
                startActivity(BuyTuteActivity.getIntent(it))
            }
        }
        tvSkip.setOnClickListener {
            let {
                startActivity(ExploreActivity.getIntent(it))
            }
        }
    }

    override fun onResume() {
        super.onResume()
     //   parentView.visibility=View.VISIBLE

    }

    override fun onStop() {
        super.onStop()

    }
    override fun animationEnd() {

        let {
            gifUtil?.setImage(this, ivLoginCart, R.drawable.cart)

        }

    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

   /* fun transitionTo(i: Intent?) {
        var pairs: Array<Pair<View, String>> =
            TransitionHelper.createSafeTransitionParticipants(this, true)
        val transitionActivityOptions =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs)
        startActivity(i, transitionActivityOptions.toBundle())
    }*/
}
