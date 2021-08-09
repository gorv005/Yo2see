package com.dartmic.yo2see.ui.product_list.poems

import android.app.Activity
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
import com.dartmic.yo2see.model.filter.FilterDefaultMultipleListModel
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.filter.FilterActivity
import com.dartmic.yo2see.ui.productDetails.EventDetailsFragment
import com.dartmic.yo2see.ui.productDetails.poem.PoemDetailFragment
import com.dartmic.yo2see.ui.product_list.EventListingFragment
import com.dartmic.yo2see.ui.product_list.adapter.AdapterEventProductList
import com.dartmic.yo2see.ui.product_list.adapter.poems.AdapterPoemsList
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_poems_listing.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PoemsListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PoemsListingFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    AdapterViewClickListener<ListingItemEvent> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterEventProductList: AdapterPoemsList? = null
    var type: Int? = 0
    var listingType: String? = ""
    lateinit var subToSubListItem: SubToSubListItem
    var query: String? = ""
    var location: String? = ""
    lateinit var listingItem: ListingItemEvent
    private var listingItemList = ArrayList<ListingItemEvent>()
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
        latitude = arguments?.getString(LATITUDE)!!
        longitude = arguments?.getString(LONGITUDE)!!
        if(latitude.equals("")){
            latitude=model?.getLatitude()!!
            longitude=model?.getLongitude()!!
        }
        init()
        rvProductPoemList.layoutManager = manager
        activity?.let {
            adapterEventProductList =
                AdapterPoemsList(this, it, R.drawable.round_circle_blue, type!!)

        }
        rlFilter.setOnClickListener {
            activity?.let {
                if (subToSubListItem?.categoryName.contains("Short Stories")) {
                    startActivityForResult(
                        FilterActivity.getIntent(
                            it,
                            "Story Type",
                            brandMultipleListModels,
                            minPrice!!,
                            maxPrice!!
                        ), REQUEST_CODE
                    )
                } else if (subToSubListItem?.categoryName.contains("Freelance")) {
                    startActivityForResult(
                        FilterActivity.getIntent(
                            it,
                            "Freelancing Type",
                            brandMultipleListModels,
                            minPrice!!,
                            maxPrice!!
                        ), REQUEST_CODE
                    )
                } else if (subToSubListItem?.categoryName.contains("Local Services")) {
                    startActivityForResult(
                        FilterActivity.getIntent(
                            it,
                            "Service Type",
                            brandMultipleListModels,
                            minPrice!!,
                            maxPrice!!
                        ), REQUEST_CODE
                    )
                } else if (subToSubListItem?.categoryName.contains("Volunteering")) {
                    startActivityForResult(
                        FilterActivity.getIntent(
                            it,
                            "Valunteering Type",
                            brandMultipleListModels,
                            minPrice!!,
                            maxPrice!!
                        ), REQUEST_CODE
                    )
                } else if (subToSubListItem?.categoryName.contains("Uncategorized")) {
                    startActivityForResult(
                        FilterActivity.getIntent(
                            it,
                            "Uncategorized Type",
                            brandMultipleListModels,
                            minPrice!!,
                            maxPrice!!
                        ), REQUEST_CODE
                    )
                } else {
                    startActivityForResult(
                        FilterActivity.getIntent(
                            it,
                            "Poem Type",
                            brandMultipleListModels,
                            minPrice!!,
                            maxPrice!!
                        ), REQUEST_CODE
                    )
                }
            }
        }
        rvProductPoemList.adapter = adapterEventProductList
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


                if (subToSubListItem?.categoryName.contains("Short Stories")) {
                    model.getEventProductList(
                        "List",
                        model?.getUserID()!!,
                        subToSubListItem.categoryId,
                        subToSubListItem.subCategoryId,
                        subToSubListItem.id,
                        "",
                        "Story",
                        "",
                        "",
                        "",
                        "" + latitude, "" + longitude,
                        query!!,
                        brands,
                        minPrice!!,
                        maxPrice!!,
                        sort_by
                    )

                } else if (subToSubListItem?.categoryName.contains("Freelance")) {
                    model.getEventProductList(
                        "List",
                        model?.getUserID()!!,
                        subToSubListItem.categoryId,
                        subToSubListItem.subCategoryId,
                        subToSubListItem.id,
                        "",
                        "Freelancing News",
                        "",
                        "",
                        "",
                        "" + latitude, "" + longitude,
                        query!!,
                        brands,
                        minPrice!!,
                        maxPrice!!,
                        sort_by
                    )

                } else if (subToSubListItem?.categoryName.contains("Local Services")) {
                    model.getEventProductList(
                        "List",
                        model?.getUserID()!!,
                        subToSubListItem.categoryId,
                        subToSubListItem.subCategoryId,
                        subToSubListItem.id,
                        "",
                        "Local Service",
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

                } else if (subToSubListItem?.categoryName.contains("Volunteering")) {
                    model.getEventProductList(
                        "List",
                        model?.getUserID()!!,
                        subToSubListItem.categoryId,
                        subToSubListItem.subCategoryId,
                        subToSubListItem.id,
                        "",
                        "Valunteering",
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

                } else if (subToSubListItem?.categoryName.contains("Uncategorized")) {
                    model.getEventProductList(
                        "List",
                        model?.getUserID()!!,
                        subToSubListItem.categoryId,
                        subToSubListItem.subCategoryId,
                        subToSubListItem.id,
                        "",
                        "Uncategorized",
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

                } else {
                    model.getEventProductList(
                        "List",
                        model?.getUserID()!!,
                        subToSubListItem.categoryId,
                        subToSubListItem.subCategoryId,
                        subToSubListItem.id,
                        "",
                        "Poem",
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
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"

        @JvmStatic
        fun getInstance(
            instance: Int,
            type: Int?, latitude: String, longitude: String, query: String,
            categoryListItemData: SubToSubListItem?
        ): PoemsListingFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = PoemsListingFragment()
            bundle.putInt(TYPE, type!!)
            bundle.putString(LATITUDE, latitude)
            bundle.putString(LONGITUDE, longitude)
            bundle.putString(QUERY, query)

            bundle.putParcelable(DATA, categoryListItemData)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_poems_listing


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
                        PoemDetailFragment
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
                subToSubListItem?.categoryName
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