package com.appiness.yo2see.ui.AdsItems


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.appiness.yo2see.base.BaseFragment
import com.appiness.yo2see.callbacks.AdapterViewClickListener
import com.appiness.yo2see.model.AdsItems
import com.appiness.yo2see.ui.LandingActivity
import com.appiness.yo2see.ui.ads.adapter.AdapterAdsItems
import kotlinx.android.synthetic.main.activity_item_list.*

/**
 * A simple [Fragment] subclass.
 */
class AdsItemsFragment : Fragment(), AdapterViewClickListener<AdsItems> {
    override fun onClickAdapterView(objectAtPosition: AdsItems, viewType: Int, position: Int) {
        when (viewType) {

            5 -> {
                (activity as LandingActivity).showAds()

            }
        }
    }

  //  override fun getLayoutId() = R.layout.fragment_ads_items

    private var adapterAdsItems: AdapterAdsItems? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = GridLayoutManager(context, 5)
        rv_ads.layoutManager = manager
        activity?.let {
            adapterAdsItems = AdapterAdsItems(this, it)

        }
        rv_ads.adapter = adapterAdsItems
        adapterAdsItems?.submitList(getData())
        adapterAdsItems?.notifyDataSetChanged()
    }

    private fun getData(): MutableList<AdsItems> {


        val product: MutableList<AdsItems> = ArrayList()
        var i = 0
        while (i < 50) {
            product!!.add(AdsItems(1, "Pepsi Lemon Drink", "AED 5.25"))
            i++
        }

        return product

    }



    companion object {


        fun getInstance(instance: Int): AdsItemsFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            //   bundle.putParcelable(KEY_OFFER_DATA, tradeOffersItem)

            val fragment = AdsItemsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }



}
