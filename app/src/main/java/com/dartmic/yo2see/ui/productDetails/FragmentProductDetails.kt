package com.dartmic.yo2see.ui.productDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.ui.productDetails.adapter.ProductImagesAdapter
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import kotlinx.android.synthetic.main.fragment_product_details.*
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
class FragmentProductDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            with(view_pager) {

                adapter = ProductImagesAdapter()
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
        } catch (e: Exception) {

        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentProductDetails.
         */
        @JvmStatic
        fun getInstance(instance: Int): FragmentProductDetails {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = FragmentProductDetails()
            fragment.arguments = bundle
            return fragment
        }
    }
}