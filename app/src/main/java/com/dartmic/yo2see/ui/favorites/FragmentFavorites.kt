package com.dartmic.yo2see.ui.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.product.ListingItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.home.HomeFragment
import com.dartmic.yo2see.ui.productDetails.FragmentProductDetails
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.ui.product_list.adapter.AdapterProductList
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentFavorites.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFavorites : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    AdapterViewClickListener<ListingItem> {
    private var adapterProductList: AdapterFavProductList? = null
    var pos: Int = -1
    private var listingItemList = ArrayList<ListingItem>()

    override fun getLayoutId() = R.layout.fragment_favorites


    override fun onResume() {
        super.onResume()
        activity?.let {
            (activity as LandingActivity).hideVisibleBottomBar(
                View.VISIBLE
            )
        }

    }

    var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = GridLayoutManager(context, 2)

        rvFavProductList.layoutManager = manager
        activity?.let {
            adapterProductList = AdapterFavProductList(this, it, R.drawable.round_circle_blue)

        }
        rvFavProductList.adapter = adapterProductList
        //  adapterProductList?.submitList(getProducts())
        // runAnimationAgain()
        subscribeUi()
        subscribeLoading()
        getFavProductData()
        ivBackFav.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    fun getFavProductData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            //  listingType = "Rent"


            model.getFavProductList(
                "FavoriteList", model?.getUserID()!!
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
        model.productListViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                listingItemList = it?.listing!!
                adapterProductList?.submitList(listingItemList)
                adapterProductList?.notifyDataSetChanged()
                runLayoutAnimation(rvFavProductList)

            } else {
                showSnackbar(it.message, false)
            }
        })
        model.favViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                listingItemList.removeAt(pos)
                adapterProductList?.notifyDataSetChanged()


            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

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

    companion object {

        fun getInstance(instance: Int): FragmentFavorites {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = FragmentFavorites()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClickAdapterView(objectAtPosition: ListingItem, viewType: Int, position: Int) {
        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT -> {

                this?.let {


                    mFragmentNavigation.pushFragment(
                        FragmentProductDetails
                            .getInstance(
                                mInt + 1,
                                AndroidUtils.getType(objectAtPosition?.listingType),
                                objectAtPosition
                            )
                    )
                    /* mFragmentNavigation.pushFragment(
                         SubCategoriesFragment
                             .getInstance(mInt + 1)
                     )*/

                }

            }
            Config.AdapterClickViewTypes.CLICK_VIEW_FAV -> {

                this?.let {
                    pos = position
                    model?.addAndRemoveToFavorites(
                        "AddToFavList",
                        model?.getUserID()!!,
                        objectAtPosition?.id,
                        0
                    )

                }

            }

        }
    }

}