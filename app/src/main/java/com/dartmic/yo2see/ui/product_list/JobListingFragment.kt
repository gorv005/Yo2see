package com.dartmic.yo2see.ui.product_list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.BuildConfig
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.interfaces.SortImpl
import com.dartmic.yo2see.model.Category_sub_subTosub.SubToSubListItem
import com.dartmic.yo2see.model.ProductItems
import com.dartmic.yo2see.model.filter.FilterDefaultMultipleListModel
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.filter.FilterActivity
import com.dartmic.yo2see.ui.productDetails.FragmentProductDetails
import com.dartmic.yo2see.ui.productDetails.JobDetailsFragment
import com.dartmic.yo2see.ui.product_list.adapter.AdapterJobProductList
import com.dartmic.yo2see.ui.product_list.adapter.AdapterProductList
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_job_listing.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JobListingFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    AdapterViewClickListener<ListingItemJob> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterProductList: AdapterJobProductList? = null
    var type: Int? = 0
    var listingType: String? = ""
    lateinit var subToSubListItem: SubToSubListItem
    var query: String? = ""
    var location: String? = ""
    lateinit var listingItem: ListingItemJob
    private var listingItemList = ArrayList<ListingItemJob>()
    var pos: Int = -1
    val REQUEST_CODE = 11
    internal var isFilter: Boolean = false
    internal var minPrice: String? = "0"
    internal var maxPrice: String? = "100000000"
    private var brandMultipleListModels = java.util.ArrayList<FilterDefaultMultipleListModel>()
    var latitude = ""
    var longitude = ""
    private var brandSelected = java.util.ArrayList<String>()
    internal var sort_by: String = ""

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
        latitude = arguments?.getString(LATITUDE)!!
        longitude = arguments?.getString(LONGITUDE)!!
        if(latitude.equals("")){
            latitude=model?.getLatitude()!!
            longitude=model?.getLongitude()!!
        }
        init()
        rvProductJobList.layoutManager = manager
        activity?.let {
            adapterProductList =
                AdapterJobProductList(this, it, R.drawable.round_circle_blue, type!!)

        }
        rlFilter.setOnClickListener {
            activity?.let {
                startActivityForResult(
                    FilterActivity.getIntent(
                        it,
                        "Job Type",
                        brandMultipleListModels,
                        minPrice!!,
                        maxPrice!!
                    ), REQUEST_CODE
                )
            }
        }
        rvProductJobList.adapter = adapterProductList
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
            val brands = JSONArray(brandSelected)

            model.getJobProductList(
                "List",
                model?.getUserID()!!,
                subToSubListItem.categoryId,
                subToSubListItem.subCategoryId,
                subToSubListItem.id,
                "",
                "Job",
                "",
                "",
                "",
                latitude,
                longitude,
                query!!,
                brands,
                minPrice!!,
                maxPrice!!,
                sort_by
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
        model.productJobListViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                listingItemList = it?.listing!!
                tvResult.setText("Found " + it?.listing?.size + " results")
                adapterProductList?.submitList(it?.listing)
                adapterProductList?.notifyDataSetChanged()
                //runLayoutAnimation(rvProductJobList)

            } else {
                showSnackbar(it.message, false)
            }
        })
        model.favViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                listingItemList.set(pos, listingItem)
                adapterProductList?.notifyDataSetChanged()

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
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"

        @JvmStatic
        fun getInstance(
            instance: Int,
            type: Int?, latitude: String, longitude: String, query: String,

            categoryListItemData: SubToSubListItem?
        ): JobListingFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = JobListingFragment()
            bundle.putInt(TYPE, type!!)
            bundle.putString(LATITUDE, latitude)
            bundle.putString(LONGITUDE, longitude)
            bundle.putString(QUERY, query)

            bundle.putParcelable(DATA, categoryListItemData)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_job_listing


    override fun onClickAdapterView(
        objectAtPosition: ListingItemJob,
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
                        JobDetailsFragment
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK) {


                    brandSelected = data!!.getStringArrayListExtra("brandSelected")!!
                    minPrice = data!!.getStringExtra("minPrice")
                    maxPrice = data!!.getStringExtra("maxPrice")
                    brandMultipleListModels =
                        data!!.getParcelableArrayListExtra<FilterDefaultMultipleListModel>("brandModel")!!
                    isFilter = true
                    getProductData()
                }

        }
    }


}