package com.dartmic.yo2see

import android.app.ActivityOptions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dartmic.yo2see.callbacks.GifEndListener
import com.dartmic.yo2see.ui.categories.CategoryActivity
import com.dartmic.yo2see.util.UiUtils
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
}
