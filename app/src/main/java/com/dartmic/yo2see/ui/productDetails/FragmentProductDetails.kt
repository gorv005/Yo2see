package com.dartmic.yo2see.ui.productDetails

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.home.adapter.TabFragmentAdapter
import com.dartmic.yo2see.ui.home.products.ProductFragment
import com.dartmic.yo2see.ui.productDetails.adapter.ProductImagesAdapter
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.layout_set_user_info_product_detail.*
import org.jetbrains.anko.backgroundColor
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager
import java.lang.StringBuilder

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
                "Detail", model.getUserID()!!, listingItem.id
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
                if (it?.detail?.get(0)?.negotiationType.equals("yes")) {
                    llNagotiation.visibility = View.VISIBLE
                } else {
                    llNagotiation.visibility = View.INVISIBLE
                }
                tvAddress.setText(it?.detail?.get(0)?.listingCity + ", " + it?.detail?.get(0)?.listingState)

                if (type?.equals(Config.Constants.BARTER)!!) {
                    tvProductName.setText(it?.detail?.get(0)?.barterProductTitle)
                    tvPrice.setText(it?.detail?.get(0)?.barterText)
                    tvModel.setText("In exchange with " + it?.detail?.get(0)?.barterExchangeText)
                    tvDesc.setText(it?.detail?.get(0)?.barterProductDesc)
                    tvDetails.setText(it?.detail?.get(0)?.barterAdditionalText)
                    tvUserName.text = it?.loginUserDetails?.get(0)?.userName
                    tvRentTermAndCondition.visibility = View.GONE
                    tvRentTermAndConditionValue.visibility = View.GONE
                } else if (type?.equals(Config.Constants.RENT)!!) {
                    tvProductName.setText(it?.detail?.get(0)?.listingTitle)
                    var r: String = ""
                    for (i in it?.rentTypeList!!) {
                        r = r + i.payment + "/" + i.rentType + "\n"
                    }
                    val ind: Int = r.lastIndexOf("\n")
                    if (ind >= 0)
                        r = StringBuilder(r).replace(ind, ind + 1, "").toString()
                    tvPrice.setText(
                        r
                    )
                    tvModel.setText(it?.detail?.get(0)?.listingTitle)
                    tvDesc.setText(it?.detail?.get(0)?.listingDescription)
                    tvDetails.setText(it?.detail?.get(0)?.rentProductDetail)
                    tvUserName.text = it?.loginUserDetails?.get(0)?.userName
                    tvRentTermAndConditionValue.text = it?.detail?.get(0)?.rentTermsAndCondition
                } else {
                    tvProductName.setText(it?.detail?.get(0)?.listingTitle)
                    tvPrice.setText(it?.detail?.get(0)?.listingPrice)
                    tvModel.setText(it?.detail?.get(0)?.listingTitle)
                    tvDesc.setText(it?.detail?.get(0)?.listingDescription)
                    tvDetails.setText(it?.detail?.get(0)?.rentProductDetail)
                    tvUserName.text = it?.loginUserDetails?.get(0)?.userName
                    tvRentTermAndCondition.visibility = View.GONE
                    tvRentTermAndConditionValue.visibility = View.GONE
                }
            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
    private var indicatorWidth = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            type = arguments?.getInt(ProductListFragment.TYPE)
            listingItem = arguments?.getParcelable(DATA)!!

            init()
            subscribeUi()
            subscribeLoading()
            tvUserName.setText(listingItem.userName)
            getProductDetails()


        } catch (e: Exception) {

        }
        ivBackDetails.setOnClickListener {
            activity?.onBackPressed()
        }

        val adapter = TabFragmentAdapter(activity?.getSupportFragmentManager()!!)
     //   adapter.addFragment(ProductFragment.getInstance(), getString(R.string.buy_))
     //   adapter.addFragment(ProductFragment.getInstance(), getString(R.string.rent_))
        adapter.addFragment(ProductFragment.getInstance(), getString(R.string.barter_))

        viewPagerProductDetail.setAdapter(adapter)
        tabProductDetail.setupWithViewPager(viewPagerProductDetail)

        tabProductDetail.post(Runnable {
            indicatorWidth = tabProductDetail.getWidth() / tabProductDetail.getTabCount()

            //Assign new width
            val indicatorParams = indicatorProductDetails?.getLayoutParams() as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            indicatorProductDetails?.setLayoutParams(indicatorParams)
        })

        viewPagerProductDetail.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //To move the indicator as the user scroll, we will need the scroll offset values
            //positionOffset is a value from [0..1] which represents how far the page has been scrolled
            //see https://developer.android.com/reference/android/support/v4/view/ViewPager.OnPageChangeListener
            override fun onPageScrolled(i: Int, positionOffset: Float, positionOffsetPx: Int) {
                if(i==0){
                    tvBarter.visibility=View.GONE
                    tvRent.visibility=View.GONE
                    tvBuy.visibility=View.VISIBLE


                }
                else if(i==1){
                    tvBarter.visibility=View.GONE
                    tvRent.visibility=View.VISIBLE
                    tvBuy.visibility=View.GONE


                }else{
                    tvBarter.visibility=View.VISIBLE
                    tvRent.visibility=View.GONE
                    tvBuy.visibility=View.GONE

                }
                var params = indicatorProductDetails?.getLayoutParams() as FrameLayout.LayoutParams

                //Multiply positionOffset with indicatorWidth to get translation
                val translationOffset: Float = (positionOffset + i) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                indicatorProductDetails?.setLayoutParams(params)
            }

            override fun onPageSelected(i: Int) {}
            override fun onPageScrollStateChanged(i: Int) {}
        })

        rlRentP.visibility=View.GONE
        rlBuyP.visibility=View.GONE

        llTab.weightSum=1.0f
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

                rluserInfo.setBackgroundResource(R.drawable.edit_text_corner_blue_bg)
                ivCurveProductDetails.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue1)
                )
                llNagotiation.backgroundColor =
                    ContextCompat.getColor(activity!!, R.color.blue1)


            }
            Config.Constants.RENT -> {

                rluserInfo.setBackgroundResource(R.drawable.edit_text_corner_voilet_bg)

                ivCurveProductDetails.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.voilet)
                )
                llNagotiation.backgroundColor =
                    ContextCompat.getColor(activity!!, R.color.voilet)


            }
            Config.Constants.BARTER -> {
                rluserInfo.setBackgroundResource(R.drawable.edit_text_corner_yellow_bg)

                ivCurveProductDetails.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.yellow1)
                )
                llNagotiation.backgroundColor =
                    ContextCompat.getColor(activity!!, R.color.yellow1)

            }
            Config.Constants.POST -> {
                rluserInfo.setBackgroundResource(R.drawable.edit_text_corner_light_bg)

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