package com.dartmic.yo2see.ui.ads

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.AdsItems
import com.dartmic.yo2see.ui.ads.adapter.AdapterAdsItems
import com.arthurivanets.bottomsheets.BottomSheet
import kotlinx.android.synthetic.main.activity_item_list.*

class ItemListActivity : AppCompatActivity(), AdapterViewClickListener<AdsItems> {
    override fun onClickAdapterView(objectAtPosition: AdsItems, viewType: Int, position: Int) {

    }

    private var adapterAdsItems: AdapterAdsItems? = null
    private var bottomSheet : BottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        val manager = GridLayoutManager(this, 5)
        rv_ads.layoutManager = manager
       let {
           adapterAdsItems = AdapterAdsItems(this, it)

        }
        rv_ads.adapter = adapterAdsItems
        adapterAdsItems?.submitList(getData())
        adapterAdsItems?.notifyDataSetChanged()
    }


    private fun getData(): MutableList<AdsItems> {


        val product: MutableList<AdsItems> = ArrayList()
        var i=0
        while (i<50) {
       //     product!!.add(AdsItems(1, "Pepsi Lemon Drink", "AED 5.25"))
            i++
        }

        return product

    }
    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ItemListActivity::class.java)

        }
    }

}
