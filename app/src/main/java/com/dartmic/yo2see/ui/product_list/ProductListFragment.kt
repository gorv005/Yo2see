package com.dartmic.yo2see.ui.product_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.ProductItems
import com.dartmic.yo2see.model.product.ListingItem
import com.dartmic.yo2see.ui.productDetails.FragmentProductDetails
import com.dartmic.yo2see.ui.product_list.adapter.AdapterProductList
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_categories_list.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sub_categories.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    AdapterViewClickListener<ListingItem> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterProductList: AdapterProductList? = null
    var type: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = GridLayoutManager(context, 2)
        type = arguments?.getInt(TYPE)
        init()
        rvProductList.layoutManager = manager
        activity?.let {
            adapterProductList = AdapterProductList(this, it, R.drawable.round_circle_blue)

        }
        rvProductList.adapter = adapterProductList
        //  adapterProductList?.submitList(getProducts())
        // runAnimationAgain()
        subscribeUi()
        subscribeLoading()
        getProductData()
    }

    fun getProductData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getProductList(
                "List", "0", "0",
                "0", "", "Rent", "10000",
                "India", "New Delhi", "New Ashok Nagar", ""
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


                adapterProductList?.submitList(it?.listing)
                adapterProductList?.notifyDataSetChanged()
                runLayoutAnimation(rvProductList)

            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    fun getProducts(): ArrayList<ProductItems> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<ProductItems>()

        for (i in 1..30) {
            events?.add(
                ProductItems(
                    1,
                    "$ 10000", "Samsung galaxy s8", "Greater Noida", "Oct 04th",
                    R.drawable.ic_devices_white
                )
            )
        }
        return events

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

    private fun runAnimationAgain() {
        val controller =
            AnimationUtils.loadLayoutAnimation(
                activity,
                R.anim.gridlayout_animation_from_bottom
            )
        rvProductList.setLayoutAnimation(controller)
        adapterProductList?.notifyDataSetChanged()
        rvProductList.scheduleLayoutAnimation()
    }

    companion object {
        const val TYPE = "type"

        @JvmStatic
        fun getInstance(instance: Int, type: Int?): ProductListFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = ProductListFragment()
            bundle.putInt(TYPE, type!!)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_product_list


    override fun onClickAdapterView(objectAtPosition: ListingItem, viewType: Int, position: Int) {
        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT -> {

                this?.let {

                    mFragmentNavigation.pushFragment(
                        FragmentProductDetails
                            .getInstance(mInt + 1,type,objectAtPosition)
                    )
                    /* mFragmentNavigation.pushFragment(
                         SubCategoriesFragment
                             .getInstance(mInt + 1)
                     )*/

                }

            }
        }
    }

    fun init() {

        when (type) {
            Config.Constants.SELL -> {

                ivCurveProduct.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue1)
                )

            }
            Config.Constants.RENT -> {


                ivCurveProduct.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.voilet)
                )


            }
            Config.Constants.BARTER -> {

                ivCurveProduct.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.yellow1)
                )

            }
            Config.Constants.POST -> {

                ivCurveProduct.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue)
                )

            }

        }
    }

}