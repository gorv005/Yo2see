package com.dartmic.yo2see.ui.product_list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartmic.yo2see.BuildConfig
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.Category_sub_subTosub.SubToSubListItem
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.filter.FilterActivity
import com.dartmic.yo2see.ui.productDetails.EventDetailsFragment
import com.dartmic.yo2see.ui.productDetails.JobDetailsFragment
import com.dartmic.yo2see.ui.product_list.adapter.AdapterEventProductList
import com.dartmic.yo2see.ui.product_list.adapter.AdapterJobProductList
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_event_listing.*
import kotlinx.android.synthetic.main.fragment_job_listing.*
import kotlinx.android.synthetic.main.fragment_job_listing.ivBackProduct
import kotlinx.android.synthetic.main.fragment_job_listing.rlFilter
import kotlinx.android.synthetic.main.fragment_job_listing.rvProductJobList
import kotlinx.android.synthetic.main.fragment_job_listing.tvResult
import kotlinx.android.synthetic.main.fragment_job_listing.tvSubTitleProductValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventListingFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    AdapterViewClickListener<ListingItemEvent> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterEventProductList: AdapterEventProductList? = null
    var type: Int? = 0
    var listingType: String? = ""
    lateinit var subToSubListItem: SubToSubListItem
    var query: String? = ""
    var location: String? = ""
    lateinit var listingItem: ListingItemEvent
    private var listingItemList = ArrayList<ListingItemEvent>()
    var pos: Int = -1

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.VERTICAL, false
        )
        type = arguments?.getInt(TYPE)
        subToSubListItem = arguments?.getParcelable(DATA)!!
        query = arguments?.getString(QUERY)
        location = arguments?.getString(LOCATION)

        init()
        rvProductEventList.layoutManager = manager
        activity?.let {
            adapterEventProductList =
                AdapterEventProductList(this, it, R.drawable.round_circle_blue, type!!)

        }
        rlFilter.setOnClickListener {
            activity?.let {
                startActivity(FilterActivity.getIntent(it))
            }
        }
        rvProductEventList.adapter = adapterEventProductList
        //  adapterProductList?.submitList(getProducts())
        // runAnimationAgain()
        subscribeUi()
        subscribeLoading()
        getProductData()
        ivBackProduct.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    fun getProductData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            //  listingType = "Rent"

            if (query.equals("")) {
                model.getEventProductList(
                    "List",
                    model?.getUserID()!!,
                    subToSubListItem.categoryId,
                    subToSubListItem.subCategoryId,
                    subToSubListItem.id,
                    "",
                    "event",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            } else {
                model.getProductList(
                    "List", model?.getUserID()!!, "", "",
                    "", "", listingType!!, "",
                    "", "", location!!, query!!
                )

            }
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
        model.productEventListViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                listingItemList = it?.listing!!
                tvResult.setText("Found " + it?.listing?.size + " results")
                adapterEventProductList?.submitList(it?.listing)
                adapterEventProductList?.notifyDataSetChanged()
                //runLayoutAnimation(rvProductJobList)

            } else {
                showSnackbar(it.message, false)
            }
        })
        model.favViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                listingItemList.set(pos, listingItem)
                adapterEventProductList?.notifyDataSetChanged()

            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


/*
    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        try {
            val context: Context = recyclerView.context
            val controller: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, R.anim.gridlayout_animation_from_bottom)
            recyclerView.layoutAnimation = controller
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
*/


    companion object {
        const val TYPE = "type"
        const val DATA = "data"
        const val LOCATION = "location"
        const val QUERY = "query"

        @JvmStatic
        fun getInstance(
            instance: Int,
            type: Int?, location: String, query: String,
            categoryListItemData: SubToSubListItem?
        ): EventListingFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = EventListingFragment()
            bundle.putInt(TYPE, type!!)
            bundle.putString(LOCATION, location)
            bundle.putString(QUERY, query)

            bundle.putParcelable(DATA, categoryListItemData)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_event_listing


    override fun onClickAdapterView(
        objectAtPosition: ListingItemEvent,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_SHARE -> {
                this?.let {
                    share()
                }
            }

            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT -> {

                this?.let {
                    mFragmentNavigation.pushFragment(
                        EventDetailsFragment
                            .getInstance(mInt + 1, type, objectAtPosition, position)
                    )

                }

            }
            Config.AdapterClickViewTypes.CLICK_VIEW_FAV -> {

                this?.let {
                    pos = position
                    listingItem = objectAtPosition
                    if (objectAtPosition?.userFavorite == 1) {
                        model?.addAndRemoveToFavorites(
                            "AddToFavList",
                            model?.getUserID()!!,
                            objectAtPosition?.id!!,
                            0
                        )
                        listingItem.userFavorite = 0
                    } else {
                        model?.addAndRemoveToFavorites(
                            "AddToFavList",
                            model?.getUserID()!!,
                            objectAtPosition?.id!!,
                            1
                        )
                        listingItem.userFavorite = 1

                    }
                }

            }

        }
    }

    fun share() {
        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage =
            """
            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
            """.trimIndent()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, AndroidUtils.getString(R.string.app_name))
            putExtra(Intent.EXTRA_TEXT, shareMessage)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Choose one")
        startActivity(shareIntent)
    }

    fun init() {
        if (query.equals("")) {
            tvSubTitleProductValue.text =
                subToSubListItem?.categoryName + "/" + subToSubListItem?.subCategoryName + "/" + subToSubListItem?.subSubcategoryName
        } else {
            tvSubTitleProductValue.text = ""
        }

    }
}