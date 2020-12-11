package com.dartmic.yo2see.ui.product_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.ProductItems
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.product_list.adapter.AdapterProductList
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import kotlinx.android.synthetic.main.fragment_product_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<ProductItems> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterProductList: AdapterProductList? = null

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
        rvProductList.layoutManager = manager
        activity?.let {
            adapterProductList = AdapterProductList(this, it, R.drawable.round_circle_blue)

        }
        rvProductList.adapter = adapterProductList
        adapterProductList?.submitList(getProducts())
        runLayoutAnimation(rvProductList)
       // runAnimationAgain()

    }

    fun getProducts(): ArrayList<ProductItems> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<ProductItems>()

        for (i in 1..15) {
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
        }catch (e: Exception){
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun getInstance(instance: Int): ProductListFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = ProductListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_product_list


    override fun onClickAdapterView(objectAtPosition: ProductItems, viewType: Int, position: Int) {
        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT -> {

                this?.let {

                    /* mFragmentNavigation.pushFragment(
                         SubCategoriesFragment
                             .getInstance(mInt + 1)
                     )*/

                }

            }
        }
    }
}