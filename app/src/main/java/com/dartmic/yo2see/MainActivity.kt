package com.dartmic.yo2see

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.tutorails.BuyTuteActivity
import com.dartmic.yo2see.ui.categories.CategoryActivity
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.GifUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),GifEndListener {
    private var gifUtil: GifUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        setContentView(R.layout.activity_main)
       // parentView.visibility=View.VISIBLE
        /*ivsell.visibility=View.GONE
        ivbarter.visibility=View.GONE
        ivbuy.visibility=View.GONE
        ivpost.visibility=View.GONE
        ivrent.visibility=View.GONE*/
       /*let {
           gifUtil = GifUtil(this)
           gifUtil?.setImage(this,gifImageView)
        }*/
        ivbuy.setOnClickListener {
            let {
               // parentView.visibility=View.GONE
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    CategoryActivity.getIntent(
                        it
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }

        }

        //   Glide.with(this).load(R.drawable.tree_gif).into(imageView).loa
        /* rlBuy.setOnClickListener {
             startActivity(
                 ItemListActivity.getIntent(this),
                 ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
             )
         }*/
        btnNext.setOnClickListener {
            let {
                startActivity(BuyTuteActivity.getIntent(it))
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

       /* ivsell.visibility=View.VISIBLE
        ivbarter.visibility=View.VISIBLE
        ivbuy.visibility=View.VISIBLE
        ivpost.visibility=View.VISIBLE
        ivrent.visibility=View.VISIBLE
*/

    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }
}
