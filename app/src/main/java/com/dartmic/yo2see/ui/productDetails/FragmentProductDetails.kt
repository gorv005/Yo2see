package com.dartmic.yo2see.ui.productDetails

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.chat.User
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.chat_list.NewMessageChatActivity
import com.dartmic.yo2see.ui.home.adapter.TabFragmentAdapter
import com.dartmic.yo2see.ui.home.products.ProductFragment
import com.dartmic.yo2see.ui.productDetails.adapter.ProductImagesAdapter
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.ui.profile.UserProfileActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.item_product.view.*
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
    var clickPos: Int? = 0

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
                tvProductName.setText(it?.detail?.get(0)?.listingTitle)
                tvModel.setText(it?.detail?.get(0)?.listingTitle)
                tvDesc.setText(it?.detail?.get(0)?.listingDescription)

                /*   if (type?.equals(Config.Constants.BARTER)!!) {
                       tvProductName.setText(it?.detail?.get(0)?.barterProductTitle)
                       tvPrice.setText(it?.detail?.get(0)?.barterText)
                       tvModel.setText("In exchange with " + it?.detail?.get(0)?.barterExchangeText)
                       tvDesc.setText(it?.detail?.get(0)?.barterProductDesc)
                       tvDetails.setText(it?.detail?.get(0)?.barterAdditionalText)
                       tvUserName.text = it?.loginUserDetails?.get(0)?.userName

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
                   } else {
                       tvProductName.setText(it?.detail?.get(0)?.listingTitle)
                       tvPrice.setText(it?.detail?.get(0)?.listingPrice)
                       tvModel.setText(it?.detail?.get(0)?.listingTitle)
                       tvDesc.setText(it?.detail?.get(0)?.listingDescription)
                       tvDetails.setText(it?.detail?.get(0)?.rentProductDetail)
                       tvUserName.text = it?.loginUserDetails?.get(0)?.userName
                   }*/
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
            clickPos = arguments?.getInt(CLICKPOSITION)

            listingItem = arguments?.getParcelable(DATA)!!
            var s = "This product is available for "

            if (listingItem?.isSell != null && listingItem?.isSell?.equals("yes")) {
                s = s + "Buy"
            }
            if (listingItem?.isRent != null && listingItem?.isRent?.equals("yes")) {
                s = s + ", Rent"
            }
            if (listingItem?.isBarter != null && listingItem?.isBarter?.equals("yes")) {
                s = s + ", Barter"
            }
            btnStartMessage.setOnClickListener {
                fetchCurrentUser()
            }

            tvViewProfile.setOnClickListener {
                activity?.let {
                    startActivity(UserProfileActivity?.getIntent(it,listingItem))
                }
            }
            tvProductAvailabilityDesc.setText(s)
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

        var w = 3.0f
        val adapter = TabFragmentAdapter(activity?.getSupportFragmentManager()!!)
        if (listingItem.isSell != null && listingItem.isSell.equals("yes")) {
            adapter.addFragment(ProductFragment.getInstance(), getString(R.string.buy_))
        } else {
            rlBuyP.visibility = View.GONE
            llTab.weightSum = w - 1

        }
        if (listingItem.isRent != null && listingItem.isRent.equals("yes")) {
            adapter.addFragment(ProductFragment.getInstance(), getString(R.string.rent_))
        } else {
            rlRentP.visibility = View.GONE
            llTab.weightSum = w - 1

        }
        if (listingItem.isBarter != null && listingItem.isBarter.equals("yes")) {
            adapter.addFragment(ProductFragment.getInstance(), getString(R.string.barter_))
        } else {
            rlbarterP.visibility = View.GONE
            llTab.weightSum = w - 1

        }

        viewPagerProductDetail.setAdapter(adapter)
        tabProductDetail.setupWithViewPager(viewPagerProductDetail)

        tabProductDetail.post(Runnable {
            indicatorWidth = tabProductDetail.getWidth() / tabProductDetail.getTabCount()

            //Assign new width
            val indicatorParams =
                indicatorProductDetails?.getLayoutParams() as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            indicatorProductDetails?.setLayoutParams(indicatorParams)
        })

        viewPagerProductDetail.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //To move the indicator as the user scroll, we will need the scroll offset values
            //positionOffset is a value from [0..1] which represents how far the page has been scrolled
            //see https://developer.android.com/reference/android/support/v4/view/ViewPager.OnPageChangeListener
            override fun onPageScrolled(i: Int, positionOffset: Float, positionOffsetPx: Int) {

/*
                if (i == 0) {
                    if (listingItem.isSell != null && listingItem.isSell.equals("yes")) {
                        tvBuy.visibility = View.VISIBLE
                        tvBarter.visibility = View.GONE
                        tvRent.visibility = View.GONE
                        llBuySellPrice.visibility = View.VISIBLE
                        tvPrice.setText("$" + listingItem?.listingPrice)
                        llRentPrice.visibility = View.GONE
                        llBarterPrice.visibility = View.GONE

                    } else {
                        if (listingItem.isRent != null && listingItem.isRent.equals("yes")) {
                            tvBuy.visibility = View.GONE
                            tvBarter.visibility = View.GONE
                            tvRent.visibility = View.VISIBLE

                            setPriceRent()
                        } else {
                            if (listingItem.isBarter != null && listingItem.isBarter.equals("yes")) {
                                tvBuy.visibility = View.GONE
                                tvBarter.visibility = View.VISIBLE
                                tvRent.visibility = View.GONE
                                llBarterPrice.visibility = View.VISIBLE
                                llRentPrice.visibility = View.GONE
                                llBuySellPrice.visibility = View.GONE
                                tvPrice5.setText("I would want to have a " + listingItem?.barterText + " in place of my product")
                            }
                        }
                    }
                } else if (i == 1) {
                    if (listingItem.isRent != null && listingItem.isRent.equals("yes")) {
                        tvBuy.visibility = View.GONE
                        tvBarter.visibility = View.GONE
                        tvRent.visibility = View.VISIBLE
                        setPriceRent()
                    } else {
                        if (listingItem.isBarter != null && listingItem.isBarter.equals("yes")) {
                            tvBuy.visibility = View.GONE
                            tvBarter.visibility = View.VISIBLE
                            tvRent.visibility = View.GONE
                            llBarterPrice.visibility = View.VISIBLE
                            llRentPrice.visibility = View.GONE
                            llBuySellPrice.visibility = View.GONE
                            tvPrice5.setText("I would want to have a " + listingItem?.barterText + " in place of my product")
                        }
                    }

                } else {
                    tvBarter.visibility = View.VISIBLE
                    tvRent.visibility = View.GONE
                    tvBuy.visibility = View.GONE
                    llBarterPrice.visibility = View.VISIBLE
                    llRentPrice.visibility = View.GONE
                    llBuySellPrice.visibility = View.GONE
                    tvPrice5.setText("I would want to have a " + listingItem?.barterText + " in place of my product")
                }

                var params = indicatorProductDetails?.getLayoutParams() as FrameLayout.LayoutParams

                //Multiply positionOffset with indicatorWidth to get translation
                val translationOffset: Float = (positionOffset + i) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                indicatorProductDetails?.setLayoutParams(params)
*/
            }

            override fun onPageSelected(i: Int) {

                if (i == 0) {
                    if (listingItem.isSell != null && listingItem.isSell.equals("yes")) {
                        tvBuy.visibility = View.VISIBLE
                        tvBarter.visibility = View.GONE
                        tvRent.visibility = View.GONE
                        llBuySellPrice.visibility = View.VISIBLE
                        tvPrice.setText("$" + listingItem?.listingPrice)
                        llRentPrice.visibility = View.GONE
                        llBarterPrice.visibility = View.GONE

                    } else {
                        if (listingItem.isRent != null && listingItem.isRent.equals("yes")) {
                            tvBuy.visibility = View.GONE
                            tvBarter.visibility = View.GONE
                            tvRent.visibility = View.VISIBLE

                            setPriceRent()
                        } else {
                            if (listingItem.isBarter != null && listingItem.isBarter.equals("yes")) {
                                tvBuy.visibility = View.GONE
                                tvBarter.visibility = View.VISIBLE
                                tvRent.visibility = View.GONE
                                llBarterPrice.visibility = View.VISIBLE
                                llRentPrice.visibility = View.GONE
                                llBuySellPrice.visibility = View.GONE
                                tvPrice5.setText("I would want to have a " + listingItem?.barterText + " in place of my product")
                            }
                        }
                    }
                } else if (i == 1) {
                    if (listingItem.isRent != null && listingItem.isRent.equals("yes")) {
                        tvBuy.visibility = View.GONE
                        tvBarter.visibility = View.GONE
                        tvRent.visibility = View.VISIBLE
                        setPriceRent()
                    } else {
                        if (listingItem.isBarter != null && listingItem.isBarter.equals("yes")) {
                            tvBuy.visibility = View.GONE
                            tvBarter.visibility = View.VISIBLE
                            tvRent.visibility = View.GONE
                            llBarterPrice.visibility = View.VISIBLE
                            llRentPrice.visibility = View.GONE
                            llBuySellPrice.visibility = View.GONE
                            tvPrice5.setText("I would want to have a " + listingItem?.barterText + " in place of my product")
                        }
                    }

                } else {
                    tvBarter.visibility = View.VISIBLE
                    tvRent.visibility = View.GONE
                    tvBuy.visibility = View.GONE
                    llBarterPrice.visibility = View.VISIBLE
                    llRentPrice.visibility = View.GONE
                    llBuySellPrice.visibility = View.GONE
                    tvPrice5.setText("I would want to have a " + listingItem?.barterText + " in place of my product")
                }
                var params = indicatorProductDetails?.getLayoutParams() as FrameLayout.LayoutParams

                //Multiply positionOffset with indicatorWidth to get translation
                val translationOffset: Float = (0.0f + i) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                indicatorProductDetails?.setLayoutParams(params)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })

        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerProductDetail.setCurrentItem(clickPos!!, true)
        }, 300)
    }

    fun setPriceRent() {
        llRentPrice.visibility = View.VISIBLE
        llBarterPrice.visibility = View.GONE
        llBuySellPrice.visibility = View.GONE
        var i = 0
        while (i < listingItem?.rentType?.size!!) {
            if (i == 0) {
                rlPrice1.visibility = View.VISIBLE
                tvPrice1.setText(
                    "$" + listingItem?.rentType?.get(i)?.payment + "/" + listingItem?.rentType?.get(
                        i
                    )?.rentType
                )
            } else if (i == 1) {
                rlPrice2.visibility = View.VISIBLE
                tvPrice2.setText(
                    "$" + listingItem?.rentType?.get(i)?.payment + "/" + listingItem?.rentType?.get(
                        i
                    )?.rentType
                )
            } else if (i == 2) {
                rlPrice3.visibility = View.VISIBLE
                tvPrice3.setText(
                    "$" + listingItem?.rentType?.get(i)?.payment + "/" + listingItem?.rentType?.get(
                        i
                    )?.rentType
                )
            } else if (i == 3) {
                rlPrice4.visibility = View.VISIBLE
                tvPrice4.setText(
                    "$" + listingItem?.rentType?.get(i)?.payment + "/" + listingItem?.rentType?.get(
                        i
                    )?.rentType
                )
            }
            i++
        }
    }

    companion object {
        const val TYPE = "type"
        const val DATA = "data"
        var currentUser: User? = null
        const val CLICKPOSITION = "CLICKPostion"

        fun getInstance(
            instance: Int,
            type: Int?,
            data: ListingItem,
            clickPos: Int
        ): FragmentProductDetails {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putInt(CLICKPOSITION, clickPos)

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
        /*when (type) {
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

        }*/
    }

    override fun getLayoutId() = R.layout.fragment_product_details

    private fun fetchCurrentUser() {
        //    val uid = "hUJc668PNFeZTtyYMZPS0lpdjv93"
        val uid = listingItem?.userUID
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = dataSnapshot.getValue(User::class.java)
                val intent = Intent(activity, NewMessageChatActivity::class.java)

                intent.putExtra(NewMessageChatActivity.USER_KEY, currentUser)
                //     intent.putExtra(NewMessageChatActivity.UID, uid)

                startActivity(intent)
            }

        })
    }

}