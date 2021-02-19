package com.dartmic.yo2see.ui.productDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.product.ListingItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.productDetails.adapter.ProductImagesAdapter
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.jetbrains.anko.backgroundColor
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentProductDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentProductDetails : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var type: Int? = 0
    lateinit var listingItem: ListingItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            (activity as LandingActivity).hideVisibleBottomBar(
                View.VISIBLE
            )
        }

    }

    fun getProductDetails() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            //  listingType = "Rent"

            model.getProductDetails(
                "Detail", "1", "" + listingItem.id
            )
        }
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.productDetailsViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status.equals("true")) {

                with(view_pager) {

                    adapter = ProductImagesAdapter(it?.galleryList!!)
                    //    setPageTransformer(true, ZoomOutPageTransformer())
                    worm_dots_indicator.setViewPager(this)
                    setBorderAnimation(true)
                    //  setInterval(2000)
                    setDirection(AutoScrollViewPager.Direction.RIGHT)
                    //   setCycle(true)
                    //  setBorderAnimation(true)
                    //    setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT)
                    // startAutoScroll()
                }
                if(it?.detail?.get(0)?.negotiationType.equals("yes")){
                    llNagotiation.visibility=View.VISIBLE
                }else{
                    llNagotiation.visibility=View.INVISIBLE
                }
             //   if (type?.equals(Config.Constants.SELL)!!) {
                    tvProductName.setText(it?.detail?.get(0)?.listingTitle)
                    tvAddress.setText(it?.detail?.get(0)?.listingAddress)
                    tvPrice.setText(it?.detail?.get(0)?.listingPrice)
                    tvModel.setText(it?.detail?.get(0)?.listingTitle)
                    tvDesc.setText(it?.detail?.get(0)?.listingDescription)
                    tvDetails.setText(it?.detail?.get(0)?.rentProductDetail)

             //   }
            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            type = arguments?.getInt(ProductListFragment.TYPE)
            listingItem = arguments?.getParcelable(DATA)!!

            init()
            subscribeUi()
            subscribeLoading()
            getProductDetails()


        } catch (e: Exception) {

        }
        ivBackDetails.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        const val TYPE = "type"
        const val DATA = "data"

        fun getInstance(instance: Int, type: Int?, data: ListingItem): FragmentProductDetails {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = FragmentProductDetails()
            bundle.putInt(TYPE, type!!)
            bundle.putParcelable(DATA, data!!)
            fragment.arguments = bundle
            return fragment
        }
    }

    fun init() {
        tvProductName.text = listingItem?.listingTitle
        tvAddress.text = listingItem?.listingState
        when (type) {
            Config.Constants.SELL -> {

                ivCurveProductDetails.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue1)
                )
                llNagotiation.backgroundColor =
                    ContextCompat.getColor(activity!!, R.color.blue1)


            }
            Config.Constants.RENT -> {


                ivCurveProductDetails.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.voilet)
                )
                llNagotiation.backgroundColor =
                    ContextCompat.getColor(activity!!, R.color.voilet)


            }
            Config.Constants.BARTER -> {

                ivCurveProductDetails.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.yellow1)
                )
                llNagotiation.backgroundColor =
                    ContextCompat.getColor(activity!!, R.color.yellow1)

            }
            Config.Constants.POST -> {

                ivCurveProductDetails.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue)
                )
                llNagotiation.backgroundColor =
                    ContextCompat.getColor(activity!!, R.color.blue)

            }

        }
    }

    override fun getLayoutId() = R.layout.fragment_product_details


}